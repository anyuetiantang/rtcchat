package com.rtcchat.baseController;

import javax.annotation.Resource;

import com.rtcchat.service.UserService;
import com.rtcchat.serviceImpl.BaseServiceImpl;

public class BaseController {
	protected BaseServiceImpl baseService = null;
	
	@Resource(name="baseService")
	public void setBaseService(BaseServiceImpl baseService) {
		this.baseService = baseService;
	}
}
