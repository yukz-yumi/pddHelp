package com.yukz.daodaoping.app.wx;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yukz.daodaoping.common.utils.R;

@RequestMapping("/appInt/wx")

@RestController
public class WxCtrl {

	private static final String CODE2SESSION_URI = "https://api.weixin.qq.com/sns/jscode2session";

	@Value("${wx.appId}")
	private String appId;

	@Value("${wx.secert}")
	private String secert;
	
	@Autowired
	private WXService wxService;

	@GetMapping("/getOpenId")
	public R getOpenId(HttpServletRequest request) throws Exception {
		String js_code = request.getParameter("js_code");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("appid", appId));
		nvps.add(new BasicNameValuePair("secret", secert));
		nvps.add(new BasicNameValuePair("js_code", js_code));
		nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
		URI uri = new URIBuilder(CODE2SESSION_URI).addParameters(nvps).build();
		CloseableHttpResponse response = null;
		HttpGet httpGet = new HttpGet(uri);
		JSONObject json = new JSONObject();
		try {
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				// 解析响应数据
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				json = JSONObject.parseObject(content);
			}
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
		if(json.containsKey("errcode")) {
			return R.error().put("data", json);
			
		}
		return R.ok().put("data", json);
	}
	

}
