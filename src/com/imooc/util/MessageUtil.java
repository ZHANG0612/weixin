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
	 * xmlתmap
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
	 * ���ı�����תΪxml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	/**
	 * ���˵�
	 */
	public static String menuText(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n");
		stringBuffer.append("1.�γ̽���\n");
		stringBuffer.append("2.Ľ��������\n");
		stringBuffer.append("�ظ��������˲˵���");
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
		stringBuffer.append("���׿γ̽���΢�Ź��ںſ�������Ҫ�漰���ںŽ��ܡ��༭ģʽ���ܡ�����ģʽ���ܵȡ�");
		return stringBuffer.toString();
	}
	
	/**
	 * secondMenu
	 */
	public static String secondMenu(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("Ľ��������......");
		return stringBuffer.toString();
	}
	
	/**
	 * ��ͼ�Ķ���תΪxml
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
	 * ��ͼƬ��Ϣת����xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", imageMessage.getClass());
		return xStream.toXML(imageMessage);
	}
	
	/**
	 * ��������Ϣת����xml
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
		news.setTitle("Ľ��������");
		news.setDescription("Ľ�����Ǵ�ֱ�Ļ�����IT�������ѧϰ��վ���Զ�����Ƶ�̡̳����߱�̹��ߡ�ѧϰ�ƻ����ʴ�����Ϊ������ɫ�������������ҵ���õĻ���������ţ�ˣ�Ҳ����ͨ����ѵ����߹�����Ƶ�γ�ѧϰ�������ȵĻ�����IT������Ľ�����γ̺���ǰ�˿���... ");
		news.setPicUrl("http://zhangqj.free.ngrok.cc/Weixin/image/imooc.jpg");
		news.setUrl("www.imooc.com");
		newsList.add(news);
		
		//���������ͼ����Ϣ�Ļ������±����news����ŵ�newsMessage
		
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
	 * ��װͼƬ��Ϣ
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
	 * ��װ������Ϣ
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message=null;
		Music music=new Music();		
		music.setThumbMediaId("c-3Sy0l0cZYQdf-2rtB65yvuP6weRdcMEFZpJJlpjQED1K4LitG3XQu9HzSoJrY8");
		music.setDescription("��7Ƭβ��");
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
