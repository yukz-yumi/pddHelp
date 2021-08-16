package com.yukz.daodaoping.app.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.yukz.daodaoping.common.config.ConfigKey;
import com.yukz.daodaoping.common.controller.BaseController;
import com.yukz.daodaoping.common.controller.CommonController;
import com.yukz.daodaoping.system.enums.FileCategoryEnum;
import com.yukz.daodaoping.system.enums.ImgTypeEnum;
import com.yukz.daodaoping.system.utils.CosApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.app.task.enums.TaskIsPaidEnum;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.task.enums.TaskVerifyStatusEnum;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.enums.PlatformEnum;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("appInt/pageConfig/")
public class PageConfig extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PageConfig.class);
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@GetMapping("supplyPlatform")
	public R getTaskTypeInfo() {
		return R.ok().put("data", PlatformEnum.toMap());
	}
	
	/**
	 * 查询不同平台所支持的业务
	 * @param agentId
	 * @param platform
	 * @param pageNum
	 * @param pageSize
	 * @param userAgent
	 * @return
	 */
	@GetMapping("platform/{platform}/list/{agentId}")
	public R getAllTaskType(@PathVariable("platform") String platform,@PathVariable("agentId") Long agentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentId", agentId);
		map.put("platform", platform);
		map.put("allowed", IsAllowEnum.YES.getStatus());
		map.put("sort", "id");
		map.put("order", "asc");
		List<TaskTypeInfoDO> taskTypeInfoList = taskTypeInfoService.list(map);
		return R.ok().put("data", taskTypeInfoList);
	}
	
	@GetMapping("assisantType")
	public R getAssistantTypeList() {
		return R.ok().put("data", AssisantTypeEnum.toList());
	}
	
	@GetMapping("taskStatus")
	public R getTaskStatusList() {
		return R.ok().put("data", TaskStatusEnum.toList());
	}
	
	@GetMapping("timeScope")
	public R getTimeScopeList() {
		return R.ok().put("data", TimeScopeEnum.toList());
	}
	
	@GetMapping("taskType/{agentId}")
	public R getTaskTypeList(@PathVariable("agentId") Long agentId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("agentId", agentId);
		map.put("allowed", "yes");
		List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
		List<TaskTypeInfoDO> list  = taskTypeInfoService.list(map);
		if(list.isEmpty()) {
			return  R.ok().put("data", list);
		}
		for (TaskTypeInfoDO item : list) {
			Map<String,String> typeMap = new HashMap<String, String>();
			typeMap.put("code", item.getTaskType());
			typeMap.put("desc", item.getTaskTypeDesc());
			resultMapList.add(typeMap);
		}
		return R.ok().put("data", resultMapList);
	}
	
	
	@GetMapping("/taskAcceptionStatus")
	public R getTaskApplyList() {
		return R.ok().put("data",  TaskAcceptionStatusEunm.toList());
	}
	
	@GetMapping("/taskVerifyStatus")
	public R getTaskVerifyStatus() {
		return R.ok().put("data",  TaskVerifyStatusEnum.toList());
	}
	
	@GetMapping("/taskIsPaid")
	public R getTaskPaidStatus() {
		return R.ok().put("data",  TaskIsPaidEnum.toList());

	/**
	 * 上传附件
	 */
	@ResponseBody
	@PostMapping("/upAttachFile/{fileCategory}")
	public R upAttachFile(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file
			, @PathVariable(required = false) String fileCategory) throws Exception {
		Map<String, Object> fileMap = new HashMap<>();
		//校验目录是否符合规范
		FileCategoryEnum categoryEnum = FileCategoryEnum.getEnumByCode(fileCategory);
		if (null == categoryEnum) {
			return R.error().put("msg", "目录格式错误");
		}
		request.setCharacterEncoding("UTF-8");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String yearMonth = sdf.format(now);
		MultipartFile multipartFile = file;
		if (null != multipartFile && !multipartFile.isEmpty()) {
			String upFileName = multipartFile.getOriginalFilename();
			String fileExt = upFileName.substring(upFileName.lastIndexOf("."));
			//校验文件类型
			ImgTypeEnum imgTypeEnum = ImgTypeEnum.getEnumByCode(fileExt);
			if (null == imgTypeEnum) {
				return R.error().put("msg", "不支持的文件类型");
			}
			//处理上传的附件
			File localFile = null;
			String key = null;
			try {
				localFile = File.createTempFile("temp", null);
				file.transferTo(localFile);
				// 指定要上传到 COS 上的路径(由于腾讯只认"/"，不认"\"，故这里不适用File.separator)
				key = "/" + fileCategory + "/" + yearMonth + "/" + get32UUID() + fileExt;
				logger.info("文件上传key：" + key);
				CosApiUtil.putObject(localFile, key);
			} catch(IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			fileMap.put("fileUrl", ConfigKey.imgUrl+key);
			fileMap.put("fileName", upFileName);
		}

		return R.ok().put("data", fileMap);
	}
}
