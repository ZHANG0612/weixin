package com.imooc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.baidu.translate.demo.TransApi;
import com.imooc.pojo.Image;
import com.imooc.pojo.ImageMessage;
import com.imooc.pojo.Music;
import com.imooc.pojo.MusicMessage;
import com.imooc.pojo.News;
import com.imooc.pojo.NewsMessage;
import com.imooc.pojo.TextMessage;
import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	private static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	/**
	 * xml转map
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map=new HashMap<String,String>();
		SAXReader reader=new SAXReader();
		
		InputStream inputStream=request.getInputStream();
		Document document=reader.read(inputStream);
		
		Element root=document.getRootElement();
		
		List<Element> list=root.elements();
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		return map;
		
	}
	
	/**
	 * 将文本对象转为xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	/**
	 * 主菜单
	 */
	public static String menuText(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		stringBuffer.append("1.课程介绍\n");
		stringBuffer.append("2.慕课网介绍\n");
		stringBuffer.append("回复？调出此菜单。");
		return stringBuffer.toString();
	}
	
	/**
	 * 
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage textMessage=new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setMsgType(MESSAGE_TEXT);
		textMessage.setCreateTime(new Date().toString());
		textMessage.setContent(content);
		return textMessageToXml(textMessage);
	}
	
	/**
	 * firstMenu
	 */
	public static String firstMenu(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("本套课程介绍微信公众号开发，主要涉及公众号介绍、编辑模式介绍、开发模式介绍等。");
		return stringBuffer.toString();
	}
	
	/**
	 * secondMenu
	 */
	public static String secondMenu(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("慕课网介绍......");
		return stringBuffer.toString();
	}
	
	/**
	 * 将图文对象转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMessage);
	}
	
	/**
	 * 将图片消息转换成xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", imageMessage.getClass());
		return xStream.toXML(imageMessage);
	}
	
	/**
	 * 将音乐消息转换成xml
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", musicMessage.getClass());
		return xStream.toXML(musicMessage);
	}
	
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message=null;
		List<News> newsList=new ArrayList<News>();
		NewsMessage newsMessage=new NewsMessage();
		
		News news=new News();
		news.setTitle("慕课网介绍");
		news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发... ");
		news.setPicUrl("http://zhangqj.free.ngrok.cc/Weixin/image/imooc.jpg");
		news.setUrl("www.imooc.com");
		newsList.add(news);
		
		//如果创建多图文消息的话，在下边添加news对象放到newsMessage
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().toString());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		message=newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message=null;
		Image image=new Image();
		image.setMediaId("2pWHapE7WRioHV5iVy7KH6tn7-cVt_QPYToBwoC-nIaaC7hHHz_WinVp7ybcxHq5");
		ImageMessage imageMessage=new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().toString());
		imageMessage.setImage(image);
		message=imageMessageToXml(imageMessage);
		return message;
	}
	
	/**
	 * 组装音乐消息
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message=null;
		Music music=new Music();		
		music.setThumbMediaId("c-3Sy0l0cZYQdf-2rtB65yvuP6weRdcMEFZpJJlpjQED1K4LitG3XQu9HzSoJrY8");
		music.setDescription("速7片尾曲");
		music.setTitle("see you again");
		music.setMusicUrl("http://zhangqj.free.ngrok.cc/Weixin/resources/See You Again.mp3");
		music.setHQMusicUrl("http://zhangqj.free.ngrok.cc/Weixin/resources/See You Again.mp3");
		MusicMessage musicMessage=new MusicMessage();
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setCreateTime(new Date().toString());
		musicMessage.setMusic(music);
		message=musicMessageToXml(musicMessage);
		return message;
	}
	
}
