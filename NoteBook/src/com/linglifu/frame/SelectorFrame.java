package com.linglifu.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.linglifu.vo.User;

public class SelectorFrame extends JFrame implements ActionListener {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 200;
	private static final String FRAME_NAME = "功能列表";
	private static final String COMMOND_LOGIN = "login";
	private static final String COMMOND_SETTINGS = "settings";
	private static final String COMMOND_WRITE_NOTE = "writeNote";
	private static final String COMMOND_QUERY_NOTE = "queryNote";
	private static final String COMMOND_EXISTS = "exists";
	private static final String COMMOND_LOGOUT = "loginOut";

	public static SelectorFrame INSTANCE;
	private JButton loginBtn;

	public SelectorFrame() {
		INSTANCE = this;
		this.init();
	}

	public void updateLoginBtn() {
		if (MainFrame.mUser != null) {
			loginBtn.setActionCommand(COMMOND_LOGOUT);
			loginBtn.setText("［退出登录］");
			loginBtn.setBackground(Color.YELLOW);
		}
	}

	private void init() {
		this.setTitle(FRAME_NAME);
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 获得显示器大小对象
		this.setSize(WIDTH, HEIGHT);
		Dimension frameSize = this.getSize(); // 获得窗口大小对象
		if (frameSize.width > displaySize.width)
			frameSize.width = displaySize.width; // 窗口的宽度不能大于显示器的宽度
		if (frameSize.height > displaySize.height)
			frameSize.height = displaySize.height; // 窗口的高度不能大于显示器的高度
		this.setLocation((displaySize.width - frameSize.width) / 2,
				(displaySize.height - frameSize.height) / 2); // 设置窗口居中显示器显示
		this.setLayout(new BorderLayout());
		this.initFrame();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void initFrame() {
		JPanel contentPanel = new JPanel(new GridLayout(5, 1));
		loginBtn = new JButton("登录系统");
		JButton settingsBtn = new JButton("系统设置");
		JButton writeNoteBtn = new JButton("写日记");
		JButton queryNoteBtn = new JButton("查找日记");
		JButton existsBtn = new JButton("退出系统");
		loginBtn.addActionListener(this);
		loginBtn.setActionCommand(COMMOND_LOGIN);
		settingsBtn.addActionListener(this);
		settingsBtn.setActionCommand(COMMOND_SETTINGS);
		writeNoteBtn.addActionListener(this);
		writeNoteBtn.setActionCommand(COMMOND_WRITE_NOTE);
		queryNoteBtn.addActionListener(this);
		queryNoteBtn.setActionCommand(COMMOND_QUERY_NOTE);
		existsBtn.addActionListener(this);
		existsBtn.setActionCommand(COMMOND_EXISTS);

		contentPanel.add(loginBtn);
		contentPanel.add(settingsBtn);
		contentPanel.add(writeNoteBtn);
		contentPanel.add(queryNoteBtn);
		contentPanel.add(existsBtn);
		this.add(contentPanel);

	}

	public void actionPerformed(ActionEvent arg0) {
		System.out.println("---" + arg0 + "---");
		if (arg0.getActionCommand().equals(COMMOND_LOGIN))
			new LoginFrame();
		if (arg0.getActionCommand().equals(COMMOND_LOGOUT)) {
			MainFrame.mUser = null;
			MainFrame.INSTACE.dispose();
			loginBtn.setText("登录系统");
			loginBtn.setActionCommand(COMMOND_LOGIN);
		}

		if (arg0.getActionCommand().equals(COMMOND_SETTINGS)) {
			if (MainFrame.mUser != null) {
				JOptionPane.showMessageDialog(null, "正在执行系统设置功能！", "提示：",
						JOptionPane.YES_OPTION);
			} else
				JOptionPane.showMessageDialog(null, "用户未登录，请先登录", "提示：",
						JOptionPane.YES_OPTION);
		}

		if (arg0.getActionCommand().equals(COMMOND_WRITE_NOTE)) {
			if (MainFrame.mUser != null) {
				new MainFrame(MainFrame.mUser);
			} else
				JOptionPane.showMessageDialog(null, "用户未登录，请先登录", "提示：",
						JOptionPane.YES_OPTION);
		}
		if (arg0.getActionCommand().equals(COMMOND_QUERY_NOTE)) {
			if (MainFrame.mUser != null) {
				// JOptionPane.showMessageDialog(null, "正在执行系统设置功能！", "提示：",
				// JOptionPane.YES_OPTION);
				String search = null;
				String whereCause = null;
				new FindFrame(whereCause, search);
			} else
				JOptionPane.showMessageDialog(null, "用户未登录，请先登录", "提示：",
						JOptionPane.YES_OPTION);
		}
		if (arg0.getActionCommand().equals(COMMOND_EXISTS)) {
			JOptionPane.showMessageDialog(null, "谢谢使用，再见！", "提示：",
					JOptionPane.YES_OPTION);
			this.dispose();
		}
	}
}
