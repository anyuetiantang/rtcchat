package com.rtcchat.tools;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import com.rtcchat.controller.WebSocketController;

/**
 * 
 * @author anyuetiantang
 *
 * ����һ������ֿ⣬���ڴ洢һЩ�������������
 */

public class Storage {
	//�洢ÿһ���û���websocket
	public static Map<Integer,Session> socketMap = new HashMap<Integer,Session>();
}
