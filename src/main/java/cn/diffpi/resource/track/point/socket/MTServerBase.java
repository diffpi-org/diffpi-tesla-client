package cn.diffpi.resource.track.point.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;

import cn.diffpi.resource.track.point.model.Point;
import cn.dreampie.common.util.json.Jsoner;
//通用多线程服务器
public class MTServerBase extends Thread {

	// client
	protected Socket socket;

	private static String respJsonStr(InputStream inputStream) {
		try {
			StringBuffer buffer = new StringBuffer();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;

			return buffer.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Here is the thread that does the work
	// Presumably you'll override this in the subclass
	public void run() {
		try {
			JSONObject obj = Jsoner.toObject(respJsonStr(socket.getInputStream()));
			
			Point.dao.save(obj);
			
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}