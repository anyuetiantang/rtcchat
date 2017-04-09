package com.rtcchat.tools;

import java.lang.reflect.Field;

import net.sf.cglib.beans.BeanCopier;


public class DataHelper {
	
	//ʹ�÷�����Ƹ���
	public static <T> T copy(Class<T> clazz,Object source) throws InstantiationException, IllegalAccessException{
		Field[] targetFs = clazz.getDeclaredFields();
		Field[] sourceFs = source.getClass().getDeclaredFields();
		T t = clazz.newInstance();
		for(int i=0;i<targetFs.length;i++){
			targetFs[i].setAccessible(true);
			for(int j=0;j<sourceFs.length;j++){
				sourceFs[j].setAccessible(true);
				if(targetFs[i].getName().equals(sourceFs[j].getName())
						&&targetFs[i].getType().equals(sourceFs[j].getType())){
					targetFs[i].set(t,sourceFs[j].get(source));
					System.out.println(targetFs[i].getName());
					System.out.println(targetFs[i].getType());
//					System.out.println(sourceFs[j].get(source).toString());
					j = sourceFs.length;
				}
			}
		}
		
		return t;
	}
	
	//ʹ�ù���ʵ�ָ���(�õ���jar���ĺ���˼��Ҳ�Ƿ���)
	public static <T> T copyByTool(Class<T> clazz,Object source) throws InstantiationException, IllegalAccessException{
		T t = clazz.newInstance();
		BeanCopier copier = BeanCopier.create(source.getClass(), t.getClass(), false);
		copier.copy(source, t, null);
		return t;
	}
}
