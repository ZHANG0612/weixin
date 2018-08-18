package com.imooc.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.baidu.translate.demo.TransApi;
import com.imooc.menu.Button;
import com.imooc.menu.ClickButton;
import com.imooc.menu.Menu;
import com.imooc.menu.ViewButton;
import com.imooc.pojo.AccessToken;
import com.imooc.translate.Result;
import com.imooc.translate.TransResult;

import net.sf.json.JSONObject;

public class WeixinUtil {
	private static final String APPID="wx5353f36c08558bf9";
	private static final String APPSECRET="7ccfb7587fb1396332e0ade869f22d83";
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private static final String CREATEMENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QUERY_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	private static final String BD_APP_ID = "20180303000130609";
    private static final String BD_SECURITY_KEY = "IlSpHO1Vh_yMDL75vyhp";
	
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(url);
		JSONObject jsonObject=null;
		try {
			HttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity=response.getEntity();
			if(entity!=null){
				String result=EntityUtils.toString(entity,"UTF-8");
				jsonObject=JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}
	
	public static JSONObject doPostStr(String url,String outStr){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(url);
		JSONObject jsonObject=null;
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response=httpClient.execute(httpPost);
			String result=EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject=JSONObject.fromObject(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	/**
	 * 获取accesstoken
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token=new AccessToken();
		String url=ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject=doGetStr(url);
		if (jsonObject!=null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		
		return token;
	}
	
	/**
	 * 文件上传
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu=new Menu();
		ClickButton button11=new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");

		ViewButton button21=new ViewButton();
		button21.setName("view菜单");
		button21.setType("view");
		button21.setUrl("http://www.imooc.com");
		ClickButton button31=new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		ClickButton button32=new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button=new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}
	
	public static int createMenu(String token,String menu){
		int result=0;
		String url=CREATEMENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doPostStr(url, menu);
		if(jsonObject!=null){
			result=jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static JSONObject queryMenu(String token){
		String url=QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		return jsonObject;
	}
	
	public static int deleteMenu(String token){
		String url=DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		int result=0;
		if(jsonObject!=null){
			 result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static String translate(String query) throws UnsupportedEncodingException {
		TransApi api = new TransApi(BD_APP_ID, BD_SECURITY_KEY);
		JSONObject jsonObject = JSONObject.fromObject(api.getTransResult(query, "auto", "auto"));
		String result = "";
		if (jsonObject != null) {
			if (jsonObject.has("error_code")) {
				result = "错误代码：" + jsonObject.getString("error_code") + "错误提示：" + jsonObject.getString("error_msg");
			} else {
				Result res = (Result) JSONObject.toBean(jsonObject, Result.class);
				result = "目标语言类型：" + res.getFrom() + "\n" + "翻译结果类型：" + res.getTo() + "\n";
				for (TransResult transResult : res.getTrans_result()) {
					result += ("目标语言：" + transResult.getSrc() + "\n" + "翻译结果：" + transResult.getDst() + "\n");
				}
			}
		}

		return result;
	}
	
}
