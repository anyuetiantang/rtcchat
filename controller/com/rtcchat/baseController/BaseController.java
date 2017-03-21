package com.rtcchat.baseController;

import javax.annotation.Resource;

import com.rtcchat.baseService.BaseServiceImpl;

public class BaseController {
	private BaseServiceImpl baseService;
	
	@Resource(name="baseService")
	public void setBaseService(BaseServiceImpl baseService) {
		this.baseService = baseService;
	}
}
