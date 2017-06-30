package com.linglifu.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.linglifu.dao.impl.DiaryDAO;
import com.linglifu.vo.Diary;

public class FindFrame extends JFrame implements ActionListener {
	private static final int WIDTH = 360;
	private static final int HEIGHT = 200;
	private static final String FRAME_NAME = "查找日记";

	private static final String QUERY_BY_TITLE = "title";
	private static final String QUERY_BY_CONTENT = "content";
	private static final String QUERY_BY_DATE = "date";
	private static final String QUERY_BY_MOOD = "mood";
	private static final String QUERY_BY_WEATHER = "weather";

	public static FindFrame INSTACE;
	private List<Diary> mItemList;
	private JList diaryJList;
	private JScrollPane scrollPane;

	public FindFrame(String whereCause, String search) {
		INSTACE = this;
		this.init(whereCause, search);
	}

	private void init(String whereCause, String search) {
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
		this.initFrame(whereCause, search);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void loadSearchList(String whereCause, String search) {
		DiaryDAO diaryDAO = new DiaryDAO();
		mItemList = diaryDAO.query(MainFrame.mUser.getId(), whereCause, search);
		if (mItemList != null && mItemList.size() > 0) {
			scrollPane = new JScrollPane();
			DefaultListModel listModel = new DefaultListModel();
			for (Diary d : mItemList)
				listModel.addElement("标题：" + d.getTitle() + "\t 日期："
						+ d.getDate());
			diaryJList = new JList(listModel);
			diaryJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 只能选择一个
			scrollPane.setViewportView(diaryJList);

			diaryJList.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent arg0) {
					if (arg0.getValueIsAdjusting()) {
						int selectedIndex = diaryJList.getSelectedIndex();
						Diary selectedDiary = mItemList.get(selectedIndex);// 选择的日记对象
						System.out.println("选择了 " + selectedDiary);
						new DetailNoteFrame(selectedDiary);
					}

				}
			});

			JPanel contentPanel = new JPanel();
			contentPanel.add(diaryJList);

			this.add(contentPanel, BorderLayout.CENTER);
		}

	}

	private void initFrame(String whereCause, String search) {
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

		this.loadSearchList(whereCause, search);

		JButton backBtn = new JButton("返回");
		backBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		menuBar.add(findMenu);

		this.add(menuBar, BorderLayout.NORTH);
		this.add(backBtn, BorderLayout.SOUTH);
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
		this.dispose();
		new FindFrame(whereCause, search);
	}
}
