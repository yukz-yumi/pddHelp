package com.yukz.daodaoping.system.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PartETag;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/** 
 * 说明：腾讯COS服务API工具类
 * 创建人：yukz
 * 修改时间：2021年8月14日
 * @version
 */
public class CosApiUtil {
	private static final Logger logger = LoggerFactory.getLogger(CosApiUtil.class);

	private static COSClient cosClient;

	/**
	 * 简单上传文件
	 * @param localFile 本地文件
	 * @param key 指定要上传到 COS 上对象键
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static void putObject(File localFile, String key) throws InterruptedException, IOException, NoSuchAlgorithmException {
		initClient();
		// 指定要上传到的存储桶
		PutObjectRequest putObjectRequest = new PutObjectRequest(Const.QCLOUD_COS_BUCKETNAME, key, localFile);
		PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
		logger.info("上传文件结果："+putObjectResult.toString());
	}

	/**
	 * 简单上传对象-本地文件
	 */
	public static void putObject(InputStream fileInputStream, String key) throws InterruptedException, IOException, NoSuchAlgorithmException {
		initClient();
		ObjectMetadata objectMetadata = new ObjectMetadata();
		// 设置输入流长度为500
		objectMetadata.setContentLength(500);
		// 设置 Content type, 默认是 application/octet-stream
		//objectMetadata.setContentType("application/pdf");
		PutObjectResult putObjectResult = cosClient.putObject(Const.QCLOUD_COS_BUCKETNAME, key, fileInputStream, objectMetadata);
		logger.info("上传文件结果："+putObjectResult.toString());
	}

	private static void initClient() {
		String secretId = Const.QCLOUD_COS_SECRETID;
		String secretKey = Const.QCLOUD_COS_SECRETKEY;
		COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
		// 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		Region region = new Region("ap-nanjing");
		ClientConfig clientConfig = new ClientConfig(region);
		// 3 生成 cos 客户端。
		cosClient = new COSClient(cred, clientConfig);
	}


}
