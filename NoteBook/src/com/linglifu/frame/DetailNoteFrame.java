package com.linglifu.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.ByteOrder;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.linglifu.dao.impl.DiaryDAO;
import com.linglifu.enumtype.MoodEnum;
import com.linglifu.enumtype.WeatherEnum;
import com.linglifu.util.DateUtil;
import com.linglifu.util.StringUtil;
import com.linglifu.vo.Diary;
import com.linglifu.vo.User;

public class DetailNoteFrame extends JFrame {
	private static final int WIDTH = 700;
	private static final int HEIGHT = 520;
	private static final String FRAME_NAME = "日记管理";

	public static DetailNoteFrame INSTACE;

	public DetailNoteFrame(Diary diary) {
		INSTACE = this;
		this.init(diary);
	}

	private void init(Diary diary) {
		SelectorFrame.INSTANCE.updateLoginBtn();// 登录成功后更新登录按钮
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
		this.initFrame(diary);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void initFrame(Diary diary) {
		JPanel contentPanel = new JPanel(new GridLayout(10, 1));
		JLabel titleLabel = new JLabel();
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setText("标题：" + diary.getTitle());

		JLabel moodLabel = new JLabel();
		moodLabel.setHorizontalAlignment(JLabel.CENTER);
		moodLabel.setText("心情：" + diary.getMood());

		JLabel weatherLabel = new JLabel();
		weatherLabel.setHorizontalAlignment(JLabel.CENTER);
		weatherLabel.setText("天气：" + diary.getWeather());

		JLabel dateLabel = new JLabel();
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		dateLabel.setText("日期：" + diary.getDate());

		JLabel contentLabel = new JLabel();
		contentLabel.setHorizontalAlignment(JLabel.CENTER);
		contentLabel.setText(diary.getTitle());

		contentPanel.add(titleLabel);
		contentPanel.add(moodLabel);
		contentPanel.add(weatherLabel);
		contentPanel.add(dateLabel);
		contentPanel.add(contentLabel);

		JButton backBtn = new JButton("返回");
		backBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		this.add(contentPanel, BorderLayout.CENTER);
		this.add(backBtn, BorderLayout.SOUTH);
	}
}
