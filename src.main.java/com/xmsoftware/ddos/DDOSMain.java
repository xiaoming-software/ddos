package com.xmsoftware.ddos;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class DDOSMain {
	/* 扫描 */
	private TextField ipText = new TextField("10.211.55.4", 15);
	private TextField startPortText = new TextField("8080", 15);
	private TextField endPortText = new TextField("8081", 10);
	
	private JButton scanButton = new JButton("扫描");
	
	private JTextArea scanText = new JTextArea();
	
	/* 攻击 */
	private TextField attackIPText = new TextField("10.211.55.4", 15);
	private TextField portText = new TextField("8080", 10);
	private JButton attackButton = new JButton("攻击");
	private JTextArea attackText = new JTextArea();

	public static void main(String[] args) {
		DDOSMain main = new DDOSMain();
		main.ddosMain();
	}
	/**
	 * 端口选项卡
	 * @return
	 */
	private JPanel getPortJPanel(){
		JPanel p = new JPanel();
		p.setLayout(null);
		JLabel label = new JLabel("目标主机:");
		label.setBounds(60, 10, 60, 16);
		ipText.setBounds(120, 10, 100, 16);
		
		JLabel startPort = new JLabel("起始port:");
		startPort.setBounds(60, 40, 60, 16);
		 
		startPortText.setBounds(120, 40, 100, 16);
		
		JLabel endPort = new JLabel("结束port:");
		endPort.setBounds(60, 70, 60, 16);
		
		endPortText.setBounds(120, 70, 100, 16);
		
		p.add(label);
		p.add(ipText);
		p.add(startPort);
		p.add(startPortText);
		p.add(endPort);
		p.add(endPortText);
		p.add(getScanBtton());
		p.add(getStopScanBtton());
		p.add(getTextArea());
		return p;
	}
	
	/**
	 * 扫描按钮
	 * @author MingDing.Li
	 * @return
	 */
	private JButton getScanBtton(){
		scanButton.setBounds(120, 100, 70, 20);
		scanButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				scanButton.setEnabled(false);
				Ddos.scan = true;
				
				try {
					String ip = ipText.getText();
					int startPort = Integer.parseInt(startPortText.getText());
					int endPort = Integer.parseInt(endPortText.getText());
					
					// 开始扫描
					Ddos.findPort(ip, startPort, endPort, scanButton, scanText);
				} catch (Exception e2) {
					// 错误信息输出
					
				}
			}});
		
		
		return scanButton;
	}
	
	private JButton getAttakBtton(){
		attackButton.setBounds(120, 100, 70, 20);
		attackButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				attackButton.setEnabled(false);
				Ddos.attack = true;
				
				try {
					String ip = attackIPText.getText();
					int port = Integer.parseInt(portText.getText());
					
					// 开始扫描
					Ddos.ddosAttack(ip, port, attackButton, attackText);
				} catch (Exception e2) {
					// 错误信息输出
					
				}
			}});
		
		
		return attackButton;
	}
	
	/**
	 * 停止扫描按钮
	 * @author MingDing.Li
	 * @return
	 */
	private JButton getStopScanBtton(){
		JButton button = new JButton("停止");
		button.setBounds(200, 100, 70, 20);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				scanButton.setEnabled(true);
				Ddos.scan = false;
			}});
		
		return button;
	}
	
	private JScrollPane getTextArea(){
		scanText.setBounds(0, 120, 400, 400);
		scanText.setSelectedTextColor(Color.red);
		scanText.setLineWrap(true);
		scanText.setWrapStyleWord(true);
		scanText.setEditable(false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 120, 400, 400);
		
		scroll.setViewportView(scanText);
		
		final JScrollBar jsb = scroll.getVerticalScrollBar();
		jsb.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				jsb.setValue(jsb.getMaximum());
			}
			
		});
		
		return scroll;
	}
	
	/**
	 * 攻击选项卡
	 * @return
	 */
	private JPanel getAttackJPanel(){
		JPanel p = new JPanel();
		p.setLayout(null);
		JLabel label = new JLabel("目标主机:");
		label.setBounds(60, 10, 60, 16);
		attackIPText.setBounds(120, 10, 100, 16);
		
		JLabel portLable = new JLabel("目标端口:");
		portLable.setBounds(60, 40, 60, 16);
		 
		portText.setBounds(120, 40, 100, 16);
		
		
		p.add(label);
		p.add(attackIPText);
		p.add(portLable);
		p.add(portText);
		p.add(getAttakBtton());
		p.add(getStopAttackBtton());
		p.add(getTAttackextArea());
		
		return p;
	}
	/**
	 * 攻击日志输出
	 * @author MingDing.Li
	 * @return
	 */
	private JScrollPane getTAttackextArea() {
		attackText.setBounds(0, 120, 400, 400);
		attackText.setSelectedTextColor(Color.red);
		attackText.setLineWrap(true);
		attackText.setWrapStyleWord(true);
		attackText.setEditable(false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 120, 400, 400);
		
		scroll.setViewportView(attackText);
		
		final JScrollBar jsb = scroll.getVerticalScrollBar();
		jsb.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				jsb.setValue(jsb.getMaximum());
			}
			
		});
		
		return scroll;
	}
	/**
	 * 停止攻击
	 * @author MingDing.Li
	 * @return
	 */
	private Component getStopAttackBtton() {
		JButton button = new JButton("停止");
		button.setBounds(200, 100, 70, 20);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				attackButton.setEnabled(true);
				Ddos.attack = false;
			}});
		
		return button;
	}
	
	public void ddosMain(){
		JTabbedPane jp = new JTabbedPane(JTabbedPane.LEFT); 
		JPanel port = getPortJPanel();
		JPanel attack = getAttackJPanel();
		jp.add("port-扫描", port);
		jp.add("ddos-攻击", attack);

		Frame fram = new Frame("DDOS-xmsoftware");
		fram.add(jp, BorderLayout.CENTER);
		fram.setLocation(200, 100);
		fram.setSize(400, 400);
		fram.setResizable(false);
		fram.setVisible(true);
		
		//事件
		fram.addWindowListener(new WindowListener(){
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}});
	}
}
