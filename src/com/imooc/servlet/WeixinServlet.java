package com.imooc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;
import com.imooc.pojo.TextMessage;
import com.imooc.util.CheckUtil;
import com.imooc.util.MessageUtil;
import com.imooc.util.WeixinUtil;



public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeixinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		PrintWriter out=response.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		try {
			Map<String, String> map=MessageUtil.xmlToMap(request);
			String fromUserName=map.get("FromUserName");
			String toUserName=map.get("ToUserName");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			
			String message=null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message=MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("?".equals(content)||"？".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if("3".equals(content)){
					message=MessageUtil.initImageMessage(toUserName, fromUserName);
					System.out.println(message);
				}else if("4".equals(content)){
					message=MessageUtil.initMusicMessage(toUserName, fromUserName);
					System.out.println(message);
				}else{
					message=MessageUtil.initText(toUserName, fromUserName, WeixinUtil.translate(content));
				}
//				TextMessage textMessage=new TextMessage();
//				textMessage.setFromUserName(toUserName);
//				textMessage.setToUserName(fromUserName);
//				textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
//				textMessage.setCreateTime(new Date().toString());
//				textMessage.setContent("您发送的消息是："+content);
//				message=MessageUtil.textMessageToXml(textMessage);
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
					String url=map.get("EventKey");
					message=MessageUtil.initText(toUserName, fromUserName, url);
				}else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){
					String key=map.get("EventKey");
					message=MessageUtil.initText(toUserName, fromUserName, key);
				}
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label=map.get("Label");
				message=MessageUtil.initText(toUserName, fromUserName, label);
			}
			out.print(message);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

}
