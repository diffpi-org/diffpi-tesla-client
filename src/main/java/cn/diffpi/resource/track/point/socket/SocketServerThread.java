package cn.diffpi.resource.track.point.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerThread implements Runnable {
	Thread t;
	private int port = 0;
	private Class clobj;

	public SocketServerThread(int port, Class clobj) {
		t = new Thread(this);
		this.clobj = clobj;
		this.port = port;
		t.start();
	}

	@SuppressWarnings("resource")
	public void run() {
		ServerSocket ssock;
		try {
			ssock = new ServerSocket(port);
			System.out.println("Listening...");
			while (true) {
				Socket esock = null;
				try {
					esock = ssock.accept();

					System.out.println("connected...");
					// create new MTServerBase or subclass
					MTServerBase t = (MTServerBase) clobj.newInstance();
					t.socket = esock;
					t.start();
				} catch (Exception e) {
					try {
						esock.close();
					} catch (Exception ec) {
					}
				}
			}
		} catch (IOException e) {
		}
		// if we return something is wrong!
	}

	public void stopThread() {
		try {
			this.t.join(100);
		} catch (InterruptedException ex) {
		} finally {
			t = null;
		}
	}
}
