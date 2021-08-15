package com.yukz.daodaoping.common.controller;

import com.yukz.daodaoping.common.service.PddHelpCommonService;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.common.utils.ShiroUtils;
import com.yukz.daodaoping.system.domain.UserDO;
import com.yukz.daodaoping.system.utils.*;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	/**
	 * 上传附件
	 */
	@ResponseBody
	@PostMapping("/upAttachFile/{fileCategory}")
	public Object upAttachFile(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file
			, @PathVariable(required = false) String fileCategory) throws Exception {
		request.setCharacterEncoding("UTF-8");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String yearMonth = sdf.format(now);
		Map<String, Object> fileMap = new HashMap<>();
		//for(int i=0;i<file.length;i++){
		MultipartFile multipartFile = file;
		if (null != multipartFile && !multipartFile.isEmpty()) {
			String upFileName = multipartFile.getOriginalFilename();
			String fileExt = upFileName.substring(upFileName.lastIndexOf("."));
			//处理上传的附件
			File localFile = null;
			String key = null;
			try {
				localFile = File.createTempFile("temp", null);
				file.transferTo(localFile);
				// 指定要上传到 COS 上的路径
				key = "/" + fileCategory + "/" + yearMonth + "/" + get32UUID() + fileExt;
				logger.info("文件上传key：" + key);
				CosApiUtil.putObject(localFile, key);
			} catch(IOException e) {

			}

			fileMap.put("fileUrl", key);
			fileMap.put("fileName", upFileName);
		}
		//}

		return fileMap;
	}
}