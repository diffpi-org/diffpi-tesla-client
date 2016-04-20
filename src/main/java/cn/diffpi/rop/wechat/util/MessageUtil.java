package cn.diffpi.rop.wechat.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.diffpi.rop.wechat.course.message.resp.BaseMessage;
import cn.diffpi.rop.wechat.course.message.resp.article.Article;
import cn.diffpi.rop.wechat.course.message.resp.article.NewsMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "TEXT";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "NEWS";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "TEXT";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "EVENT";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "SUBSCRIBE";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "UNSUBSCRIBE";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	/***
	 * 事件类型：CLICK(上报地理位置事件)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/***
	 * 事件类型：VIEW(点击菜单跳转链接的事件推送)
	 */
	public static final String EVENT_TYPE_VIEW = "VIEW";
	
	/***
	 *  事件类型：SCANCODE_PUSH(扫码推事件的事件推送)
	 */
	public static final String EVENT_TYPE_SCANCODE_PUSH = "SCANCODE_PUSH";
	
	/***
	 * 事件类型：SCANCODE_WAITMSG(扫码推事件且弹出“消息接收中”提示框的事件推送)
	 */
	public static final String EVENT_TYPE_SCANCODE_WAITMSG = "SCANCODE_WAITMSG";
	
	/***
	 * 事件类型：PIC_SYSPHOTO(弹出系统拍照发图的事件推送)
	 */
	public static final String EVENT_TYPE_PIC_SYSPHOTO = "PIC_SYSPHOTO";
	
	/***
	 * 事件类型：PIC_PHOTO_OR_ALBUM(弹出拍照或者相册发图的事件推送)
	 */
	public static final String  EVENT_TYPE_PIC_PHOTO_OR_ALBUM = "PIC_PHOTO_OR_ALBUM";
	
	/***
	 * 事件类型：PIC_WEIXIN(弹出微信相册发图器的事件推送)
	 */
	public static final String  EVENT_TYPE_PIC_WEIXIN = "PIC_WEIXIN";
	
	/***
	 * 事件类型：LOCATION_SELECT(弹出地理位置选择器的事件推送)
	 */
	public static final String EVENT_TYPE_LOCATION_SELECT = "LOCATION_SELECT";
	
	/***
	 * 事件类型：ENTER_AGENT(成员进入应用的事件推送)
	 * 本事件在成员进入企业号的应用时触发，如果企业需要接收此事件，请打开应用回调模式中的相应开关。
	 */
	public static final String EVENT_TYPE_ENTER_AGENT = "ENTER_AGENT";
	
	/***
	 *事件类型：BATCH_JOB_RESULT( 异步任务完成事件推送)
	 */
	public static final String EVENT_TYPE_BATCH_JOB_RESULT = "BATCH_JOB_RESULT";
	
	/***
	 * 得到post的数据
	 * @param request
	 * @return
	 */
	public static String getPostData(HttpServletRequest request) {
		String str = null;
		try {
			InputStream in = request.getInputStream();
			BufferedInputStream br = new BufferedInputStream(in);

			StringBuffer sb = new StringBuffer();
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = br.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, iRead, "UTF-8"));
			}

			str = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}
	
	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		try {
			InputStream inputStream = request.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 释放资源
			inputStream.close();
			inputStream = null;
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return map;
	}
	
	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String xmlStr)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		try {
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(new ByteArrayInputStream(xmlStr.getBytes("GBK")));
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

		} catch (Exception e) {
			//e.printStackTrace();
		}

		return map;
	}

	/***
	 * 媒体或者文本消息转换为xml
	 * @param t
	 * @return
	 */
	public static <T> String custMessageToXml(T t) {
		xstream.alias("xml", t.getClass());
		return xstream.toXML(t);
	}
	
	public static String baseMessageToXml(BaseMessage bm) {
		if(bm instanceof NewsMessage){
			return newsMessageToXml((NewsMessage)bm);
		}
		xstream.alias("xml", bm.getClass());
		return xstream.toXML(bm);
	}
	
	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						try {
							Long.valueOf(text);
							
							writer.write(text);
						} catch (NumberFormatException e) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
							//e.printStackTrace();
						}
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

}
