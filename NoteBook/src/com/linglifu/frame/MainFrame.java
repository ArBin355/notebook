package com.linglifu.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

public class MainFrame extends JFrame implements ActionListener {
	private static final int WIDTH = 700;
	private static final int HEIGHT = 520;
	private static final String FRAME_NAME = "日记管理";

	private static final String QUERY_BY_TITLE = "title";
	private static final String QUERY_BY_CONTENT = "content";
	private static final String QUERY_BY_DATE = "date";
	private static final String QUERY_BY_MOOD = "mood";
	private static final String QUERY_BY_WEATHER = "weather";

	public static User mUser;
	public static MainFrame INSTACE;

	public MainFrame(User user) {
		INSTACE = this;
		mUser = user;
		this.init();
	}

	private void init() {
		System.out.println("---登录后的用户" + mUser + "---");
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
		this.initFrame();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void initFrame() {
		JPanel contentPanel = new JPanel(new BorderLayout());
		JMenuBar menuBar = new JMenuBar();
		MenuShortcut ms = new MenuShortcut(KeyEvent.VK_C);
		JMenu findMenu = new JMenu("查找");
		findMenu.setMnemonic('C');
		JMenuItem queryByTitleItem = new JMenuItem("按标题查找");
		JMenuItem queryByContentItem = new JMenuItem("按内容查找");
		JMenuItem queryByDateItem = new JMenuItem("按日期查找");
		JMenuItem queryByMoodItem = new JMenuItem("按心情查找");
		JMenuItem queryByWeatherItem = new JMenuItem("按天气查找");

		queryByTitleItem.addActionListener(this);
		queryByContentItem.addActionListener(this);
		queryByDateItem.addActionListener(this);
		queryByMoodItem.addActionListener(this);
		queryByWeatherItem.addActionListener(this);

		queryByTitleItem.setActionCommand(QUERY_BY_TITLE);
		queryByContentItem.setActionCommand(QUERY_BY_CONTENT);
		queryByDateItem.setActionCommand(QUERY_BY_DATE);
		queryByMoodItem.setActionCommand(QUERY_BY_MOOD);
		queryByWeatherItem.setActionCommand(QUERY_BY_WEATHER);

		findMenu.add(queryByTitleItem);
		findMenu.add(queryByContentItem);
		findMenu.add(queryByDateItem);
		findMenu.add(queryByMoodItem);
		findMenu.add(queryByWeatherItem);

		JPanel paramsPanel = new JPanel(new GridLayout(3, 2));// 心情、天气、日期
		paramsPanel.setSize(WIDTH / 2, HEIGHT / 2);

		final String date = DateUtil.currentDate2String();
		JLabel dateLabel = new JLabel("日期");
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel showDateLabel = new JLabel(date);
		showDateLabel.setHorizontalAlignment(JLabel.CENTER);

		final WeatherEnum[] wes = WeatherEnum.values();
		JLabel weatherLabel = new JLabel("天气");
		weatherLabel.setHorizontalAlignment(JLabel.CENTER);
		final JComboBox weatherBox = new JComboBox();
		for (WeatherEnum ws : wes) {// 添加天气列表
			weatherBox.addItem(ws.getValue());
		}

		final MoodEnum[] mes = MoodEnum.values();
		JLabel moodLabel = new JLabel("心情");
		moodLabel.setHorizontalAlignment(JLabel.CENTER);
		final JComboBox moodBox = new JComboBox();
		for (MoodEnum me : mes) {// 添加心情列表
			moodBox.addItem(me.getValue());
		}

		JPanel diaryPanel = new JPanel(new BorderLayout());
		JLabel titleLabel = new JLabel("标题");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		final JTextField titleTextField = new JTextField(28);// column 12
		JLabel contentLabel = new JLabel("正文");
		contentLabel.setHorizontalAlignment(JLabel.CENTER);
		final JTextArea contentArea = new JTextArea(18, 30);

		JPanel diaryTitlePanel = new JPanel();
		JPanel diaryContentPanel = new JPanel();
		diaryTitlePanel.add(titleLabel);
		diaryTitlePanel.add(titleTextField);
		diaryContentPanel.add(contentLabel);
		diaryContentPanel.add(contentArea);

		diaryPanel.add(diaryTitlePanel, BorderLayout.NORTH);
		diaryPanel.add(diaryContentPanel, BorderLayout.CENTER);

		JButton submitBtn = new JButton("确定提交");
		submitBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String userId = mUser.getId();
				String weather = wes[weatherBox.getSelectedIndex()].getValue();
				String mood = mes[moodBox.getSelectedIndex()].getValue();
				String title = titleTextField.getText();
				String content = contentArea.getText();
				if (!StringUtil.isNull(title) && !StringUtil.isNull(content)) {// 标题、内容都不为空
					if (title.length() > 12) {// 标题过长
						JOptionPane.showMessageDialog(null, "标题长度不能超过12",
								"提示：", JOptionPane.YES_OPTION);
					} else {
						DiaryDAO diaryDAO = new DiaryDAO();
						Diary d = new Diary();
						d.setId(UUID.randomUUID().toString());
						d.setUserId(userId);
						d.setDate(date);
						d.setMood(mood);
						d.setWeather(weather);
						d.setTitle(title);
						d.setContent(content);
						int res = diaryDAO.insert(d);// 插入数据库
						if (res > 0) {
							JOptionPane.showMessageDialog(null, "已保存", "提示：",
									JOptionPane.YES_OPTION);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "保存失败，服务器错误！",
									"提示：", JOptionPane.YES_OPTION);
						}
					}
				}
			}
		});
		JButton backBtn = new JButton("返回");
		backBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		JPanel btnPanel = new JPanel(new GridLayout(1, 2));
		btnPanel.add(submitBtn);
		btnPanel.add(backBtn);

		menuBar.add(findMenu);

		paramsPanel.add(dateLabel);
		paramsPanel.add(showDateLabel);
		paramsPanel.add(weatherLabel);
		paramsPanel.add(weatherBox);
		paramsPanel.add(moodLabel);
		paramsPanel.add(moodBox);
		contentPanel.add(paramsPanel, BorderLayout.NORTH);// 日期、心情、天气等参数
		contentPanel.add(diaryPanel, BorderLayout.CENTER);// 日记正文
		contentPanel.add(btnPanel, BorderLayout.SOUTH);
		this.add(menuBar, BorderLayout.NORTH);
		this.add(contentPanel);
	}

	public void actionPerformed(ActionEvent arg0) {
		String whereCause = null;
		if (arg0.getActionCommand().equals(QUERY_BY_TITLE)) {
			whereCause = QUERY_BY_TITLE;
		} else if (arg0.getActionCommand().equals(QUERY_BY_CONTENT)) {
			whereCause = QUERY_BY_CONTENT;

		} else if (arg0.getActionCommand().equals(QUERY_BY_DATE)) {
			whereCause = QUERY_BY_DATE;

		} else if (arg0.getActionCommand().equals(QUERY_BY_MOOD)) {
			whereCause = QUERY_BY_MOOD;

		} else if (arg0.getActionCommand().equals(QUERY_BY_WEATHER)) {
			whereCause = QUERY_BY_WEATHER;
		}
		String search = JOptionPane.showInputDialog(null, "查询条件");
		System.out.println("whereCause --->" + whereCause + "search--->"
				+ search);
		new FindFrame(whereCause, search);
	}
}
