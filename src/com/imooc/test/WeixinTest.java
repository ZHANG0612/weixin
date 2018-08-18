package com.imooc.test;

import com.imooc.pojo.AccessToken;
import com.imooc.util.WeixinUtil;

import jdk.nashorn.api.scripting.JSObject;
import net.sf.json.JSONObject;

public class WeixinTest {

	public static void main(String[] args) {
		AccessToken accessToken=WeixinUtil.getAccessToken();
		System.out.println("Ʊ�ݣ�"+accessToken.getToken());
		System.out.println("��Чʱ�䣺"+accessToken.getExpires_in());
		
		String path="E:/imooc.jpg";
		try {
			String mediaId=WeixinUtil.upload(path, accessToken.getToken(), "image");
			System.out.println(mediaId);
//			String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
//			int result=WeixinUtil.createMenu(accessToken.getToken(), menu);
//			if(result==0){
//				System.out.println("�����˵��ɹ�");
//			}else{
//				System.out.println("������"+result);
//			}
//			JSONObject jsonObject=WeixinUtil.queryMenu(accessToken.getToken());
//			System.out.println(jsonObject);
			int result=WeixinUtil.deleteMenu(accessToken.getToken());
			if(result==0){
				System.out.println("�˵�ɾ���ɹ�");
			}else{
				System.out.println("������룺"+result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
