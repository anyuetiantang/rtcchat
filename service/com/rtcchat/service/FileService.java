package com.rtcchat.service;

import java.util.List;

import com.rtcchat.baseService.BaseService;
import com.rtcchat.entity.File;
import com.rtcchat.tools.ErrorType;

public interface FileService extends BaseService {

	//���ϴ����ļ���Ϣ�������ݿ�
	public int fileUpload(int sourceId,int targetId,String type,String originName,String path);
	
	//��ȡָ�����ļ���Ϣ
	public List<File> getFiles(int sourceId,int targetId,String type);
	
}
