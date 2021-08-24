package com.yukz.daodaoping.app.wx;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.http.HttpMethodName;
import com.yukz.daodaoping.app.wx.request.MessageRequest;
import com.yukz.daodaoping.system.config.RedisHandler;

@Service
public class WXService {
	
	private static final Logger logger = LoggerFactory.getLogger(WXService.class);

	@Value("${wx.appId}")
	private String appId;

	@Value("${wx.secert}")
	private String secert;

	private static final String CLIENT_CREDENTIAL = "client_credential";

	private static final String TOKEN_KEY = "access_token:agentId:";

	private static final String TOKEN_URI = "https://api.weixin.qq.com/cgi-bin/token";
	
	private static final String UNIFORM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send";
	
	private static final String PURCHASE_TEMPLATE_ID = "jNQxovLS_wGdymokeEp4fHTMzYIFw6l4givU9zL_1hA";
	
	private static final String COLOR = "#173177";
	
	private static final String TASK_CONFIRM_REDIRECT_URL = "www.baidu.com";
	
	@Autowired
	private RedisHandler redisHandler;
	

	private String getAccessToken(Long agentId) {
		Object obj = redisHandler.get(TOKEN_KEY+agentId);
		if (obj != null) {
			return String.valueOf(obj);
		}
		JSONObject json = new JSONObject();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("grant_type", CLIENT_CREDENTIAL));
			nvps.add(new BasicNameValuePair("appid", appId));
			nvps.add(new BasicNameValuePair("secret", secert));
			URI uri = new URIBuilder(TOKEN_URI).addParameters(nvps).build();
			HttpGet httpGet = new HttpGet(uri);
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				// 解析响应数据
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				json = JSONObject.parseObject(content);
				String token = json.getString("access_token");
				Long expiredTime = Long.valueOf(json.getString("expires_in"));
				redisHandler.set(TOKEN_KEY+agentId, token, expiredTime, TimeUnit.SECONDS);
				return token;
			}
		} catch (Exception ex) {
			logger.error("获取access_token失败...原因:{}",ex.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
					httpclient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}
	
	/**
	 * 任务发布成功的通知
	 * @param request
	 */
	@Async
	public void taskPublishMsgSend(MessageRequest request) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("access_token",getAccessToken(request.getAppId()));
		param.put("touser", request.getOpenId());
		Map<String,String> templateMap = new HashMap<String, String>();
		templateMap.put("template_id", PURCHASE_TEMPLATE_ID);
		templateMap.put("url", TASK_CONFIRM_REDIRECT_URL);
		JSONObject miniprogram = new JSONObject();
		miniprogram.put("appid", appId);
		miniprogram.put("pagepath", "index?foo=bar");
		templateMap.put("miniprogram", JSON.toJSONString(miniprogram));
		JSONObject data = new JSONObject();
		JSONObject dataDetail = null;
		dataDetail = new JSONObject();
		data.put("first", dataDetail.fluentPut("value", "助力任务发布成功").put("color", COLOR));
		dataDetail = new JSONObject();
		data.put("thing1", dataDetail.fluentPut("value", request.getTitle()).put("color",COLOR));
		dataDetail = new JSONObject();
		data.put("amount5", dataDetail.fluentPut("value", request.getAmount()).put("color",COLOR));
		dataDetail = new JSONObject();
		data.put("thing7", dataDetail.fluentPut("value", request.getDetail()).put("color",COLOR));
		dataDetail = new JSONObject();
		data.put("thing8", dataDetail.fluentPut("value", request.getMemo()).put("color",COLOR));
		dataDetail = new JSONObject();
		data.put("remark", dataDetail.fluentPut("value", "查看详情").put("color",COLOR));
		templateMap.put("data", JSON.toJSONString(data));
		param.put("mp_template_msg",JSON.toJSONString(templateMap));
		JSONObject result = remote2wx(HttpMethodName.POST, UNIFORM_SEND_URL, param);
	}
	
	private JSONObject remote2wx(HttpMethodName method,String url,Map<String,String> param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		JSONObject result = new JSONObject();
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Entry<String, String> item : param.entrySet()) {
				nvps.add(new BasicNameValuePair(item.getKey(),item.getValue()));
			}
//			nvps.add(new BasicNameValuePair("grant_type", CLIENT_CREDENTIAL));
//			nvps.add(new BasicNameValuePair("appid", appId));
//			nvps.add(new BasicNameValuePair("secret", secert));
			URI uri = new URIBuilder(url).addParameters(nvps).build();
			if(method == HttpMethodName.GET ) {
				HttpGet httpGet = new HttpGet(uri);
				// 执行请求
				response = httpclient.execute(httpGet);				
			}else if(method == HttpMethodName.POST ){
				HttpPost httpPost = new HttpPost(uri);
				response = httpclient.execute(httpPost);
			}
			
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				// 解析响应数据
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				result = JSONObject.parseObject(content);
			}
		} catch (Exception ex) {
			logger.error("远程调用失败",ex.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
					httpclient.close();
				} catch (IOException e) {
					logger.error("httpClient关闭失败...");
				}
			}
		}
		return result;
	}

}
