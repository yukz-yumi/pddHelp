package com.yukz.daodaoping.app.wx;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
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

	@Autowired
	private RedisHandler redisHandler;
	

	public String getAccessToken(Long agentId) {
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

}
