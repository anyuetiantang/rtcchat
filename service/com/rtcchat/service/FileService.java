package com.rtcchat.service;

import java.util.List;

import com.rtcchat.baseService.BaseService;
import com.rtcchat.entity.File;
import com.rtcchat.tools.ErrorType;

public interface FileService extends BaseService {

	//将上传的文件信息存入数据库
	public int fileUpload(int sourceId,int targetId,String type,String originName,String path);
	
	//获取指定的文件信息
	public List<File> getFiles(int sourceId,int targetId,String type);
	
}
