package com.rtcchat.tools;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import com.rtcchat.controller.WebSocketController;

/**
 * 
 * @author anyuetiantang
 *
 * 这是一个缓存仓库，用于存储一些自主缓存的数据
 */

public class Storage {
	//存储每一个用户的websocket
	public static Map<Integer,Session> socketMap = new HashMap<Integer,Session>();
}
