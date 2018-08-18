package com.imooc.test;

import com.imooc.pojo.AccessToken;
import com.imooc.util.WeixinUtil;

import jdk.nashorn.api.scripting.JSObject;
import net.sf.json.JSONObject;

public class WeixinTest {

	public static void main(String[] args) {
		AccessToken accessToken=WeixinUtil.getAccessToken();
		System.out.println("票据："+accessToken.getToken());
		System.out.println("有效时间："+accessToken.getExpires_in());
		
		String path="E:/imooc.jpg";
		try {
			String mediaId=WeixinUtil.upload(path, accessToken.getToken(), "image");
			System.out.println(mediaId);
//			String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
//			int result=WeixinUtil.createMenu(accessToken.getToken(), menu);
//			if(result==0){
//				System.out.println("创建菜单成功");
//			}else{
//				System.out.println("错误码"+result);
//			}
//			JSONObject jsonObject=WeixinUtil.queryMenu(accessToken.getToken());
//			System.out.println(jsonObject);
			int result=WeixinUtil.deleteMenu(accessToken.getToken());
			if(result==0){
				System.out.println("菜单删除成功");
			}else{
				System.out.println("错误代码："+result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
