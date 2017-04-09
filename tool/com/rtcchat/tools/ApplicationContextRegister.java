package com.rtcchat.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

@Component 
@Lazy(false) 
public class ApplicationContextRegister implements ApplicationContextAware { 
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextRegister.class);   
	private static ApplicationContext APPLICATION_CONTEXT;   
	
	/**  * 设置spring上下文  *  * @param applicationContext spring上下文  * @throws BeansException  */  
	@Override  
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { 
		LOGGER.debug("ApplicationContext registed-->{}", applicationContext);  
		APPLICATION_CONTEXT = applicationContext;  
	} 
	
	public static ApplicationContext getApplicationContext() { 
		return APPLICATION_CONTEXT;  
	}
}
