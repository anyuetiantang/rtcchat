package com.rtcchat.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rtcchat.baseController.BaseController;
import com.rtcchat.service.FileService;
import com.rtcchat.tools.ErrorType;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	private FileService fileService = null;

	@ResponseBody
	@RequestMapping(value="/fileUpload",method=RequestMethod.POST)
	public String fileUpload(
			@RequestParam(value="file",required=true) CommonsMultipartFile file,
			@RequestParam(value="type",required=true) String type,
			@RequestParam(value="sourceId",required=true) int sourceId,
			@RequestParam(value="targetId",required=true) int targetId,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			if(!file.isEmpty()){
				HttpSession session = request.getSession();
				String username = (String)session.getAttribute("username");
				String newName = username+"_" +"to_"+type+targetId+"_"+file.getOriginalFilename();
				String logicPath = "/fileRepertory/"+newName;
			    String path = request.getServletContext().getRealPath("/")+"fileRepertory\\"+newName;
			    System.out.println(path);
			    File newFile = new File(path);
			    //通过CommonsMultipartFile本身的transferTo方法来实现
			    file.transferTo(newFile);

			    int fileId = fileService.fileUpload(sourceId, targetId, type, file.getOriginalFilename(),logicPath);
			    
			    map.put("fileId", fileId);
				map.put("code", ErrorType.ERROR_SUCCESS.getCode());
				map.put("msg", ErrorType.ERROR_SUCCESS.getMsg());
			}else{
				map.put("code", ErrorType.ERROR_PARAM.getCode());
				map.put("msg", ErrorType.ERROR_PARAM.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", ErrorType.ERROR_UNKNOWN.getCode());
			map.put("msg", ErrorType.ERROR_UNKNOWN.getMsg());
		}
		
		JSONObject jsonObj = JSONObject.fromObject(map);
		
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/getFiles",method=RequestMethod.POST)
	public String getFiles(
			@RequestParam(value="sourceId",required=true) int sourceId,
			@RequestParam(value="targetId",required=true) int targetId,
			@RequestParam(value="type",required=true) String type,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<com.rtcchat.entity.File> fileList = fileService.getFiles(sourceId, targetId, type);
			
			map.put("fileList", fileList);
    		map.put("code", ErrorType.ERROR_SUCCESS.getCode());
    		map.put("msg", ErrorType.ERROR_SUCCESS.getMsg());
		} catch (Exception e) {
    		map.put("code", ErrorType.ERROR_PARAM.getCode());
    		map.put("msg", ErrorType.ERROR_PARAM.getMsg());
		}
		
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}
	
	@Resource(name="fileService")
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
}



