package com.linglifu.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.linglifu.dao.impl.UserDAO;
import com.linglifu.enumtype.Question;
import com.linglifu.util.RandomUtil;
import com.linglifu.util.StringUtil;
import com.linglifu.vo.SafeCode;
import com.linglifu.vo.User;

public class RegFrame extends JFrame {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 400;
	private static final String FRAME_NAME = "用户注册";
	private SafeCode safeCode;

	public RegFrame() {
		this.init();
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
		JPanel panel = new JPanel(new GridLayout(9, 2));

		List<JLabel> labelList = new ArrayList<JLabel>();
		List<JTextField> textFieldList = new ArrayList<JTextField>();

		JLabel nameLabel = new JLabel("账号");
		final JTextField nameTextField = new JTextField();
		labelList.add(nameLabel);
		textFieldList.add(nameTextField);

		JLabel nicknameLabel = new JLabel("昵称");
		final JTextField nicknameTextField = new JTextField();
		labelList.add(nicknameLabel);
		textFieldList.add(nicknameTextField);

		JLabel pwdLabel = new JLabel("密码");
		final JPasswordField pwdField = new JPasswordField();
		pwdField.setHorizontalAlignment(JPasswordField.CENTER);
		labelList.add(pwdLabel);

		JLabel confirmPwdLabel = new JLabel("确认密码");
		final JPasswordField confirmPwdField = new JPasswordField();
		pwdField.setHorizontalAlignment(JPasswordField.CENTER);
		labelList.add(confirmPwdLabel);

		JLabel questionLabel = new JLabel("密保问题");
		labelList.add(questionLabel);
		final JComboBox questionComboBox = new JComboBox(Question.QUESTIONS);

		JLabel answerLabel = new JLabel("密保答案");
		final JTextField answerTextField = new JTextField();
		labelList.add(answerLabel);
		textFieldList.add(answerTextField);

		JLabel emailLabel = new JLabel("Email");
		final JTextField emailTextField = new JTextField();
		labelList.add(emailLabel);
		textFieldList.add(emailTextField);

		safeCode = RandomUtil.getSafeCode();
		JButton changeBtn = new JButton("换一个");
		final JLabel showCodeLabel = new JLabel(safeCode.getCode());
		labelList.add(showCodeLabel);

		changeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				safeCode = RandomUtil.getSafeCode();
				showCodeLabel.setText(safeCode.getCode());
			}
		});

		JLabel inputCodeLabel = new JLabel("输入验证码");
		final JTextField inputCodeTextField = new JTextField();
		labelList.add(inputCodeLabel);
		textFieldList.add(inputCodeTextField);

		for (JLabel label : labelList)
			label.setHorizontalAlignment(JLabel.CENTER);
		for (JTextField tf : textFieldList)
			tf.setHorizontalAlignment(JTextField.CENTER);

		panel.add(nameLabel);
		panel.add(nameTextField);
		panel.add(nicknameLabel);
		panel.add(nicknameTextField);

		panel.add(pwdLabel);
		panel.add(pwdField);
		panel.add(confirmPwdLabel);
		panel.add(confirmPwdField);

		panel.add(questionLabel);
		panel.add(questionComboBox);
		panel.add(answerLabel);
		panel.add(answerTextField);

		panel.add(emailLabel);
		panel.add(emailTextField);
		panel.add(showCodeLabel);
		panel.add(changeBtn);
		panel.add(inputCodeLabel);
		panel.add(inputCodeTextField);

		JPanel btnPanel = new JPanel(new GridLayout(1, 2));
		JButton backBtn = new JButton("上一步");
		JButton submitBtn = new JButton("确定");
		btnPanel.add(backBtn);
		btnPanel.add(submitBtn);

		backBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dispose();// 销毁窗口
			}
		});

		submitBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int index = questionComboBox.getSelectedIndex();// 下拉列表选择的索引
				String question = Question.QUESTIONS[index];// 密保问题
				String name = nameTextField.getText().trim();// 用户名
				String nickname = nicknameTextField.getText().trim();// 昵称
				String pwd = pwdField.getText().trim();// 密码
				String confirmPwd = confirmPwdField.getText().trim();// 确认密码
				String answer = answerTextField.getText().trim();// 密保答案
				String email = emailTextField.getText().trim();// 邮箱
				String inputCode = inputCodeTextField.getText().trim();
				if (!StringUtil.isNull(name) && !StringUtil.isNull(nickname)
						&& !StringUtil.isNull(pwd)
						&& !StringUtil.isNull(confirmPwd)
						&& !StringUtil.isNull(answer)
						&& !StringUtil.isNull(email)
						&& !StringUtil.isNull(inputCode)) {

					if (safeCode.getAnswer() == Integer.parseInt(inputCode)) {
						if (!pwd.equals(confirmPwd))
							JOptionPane.showMessageDialog(null, "两次密码不一致",
									"提示：", JOptionPane.YES_OPTION);
						else {
							String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";// 邮箱正则表达式
							Pattern regex = Pattern.compile(check);
							Matcher matcher = regex.matcher(email);
							if (matcher.matches()) {
								User user = new User();
								user.setId(UUID.randomUUID().toString());
								user.setName(name);
								user.setNickname(nickname);
								user.setPwd(confirmPwd);
								user.setQuestion(question);
								user.setAnswer(answer);
								user.setEmail(email);
								System.out.println(user);
								UserDAO userDAO = new UserDAO();
								int res = 0;
								try {
									res = userDAO.insert(user);
								} catch (Exception e) {
									e.printStackTrace();
									JOptionPane.showMessageDialog(null,
											"服务器错误", "提示：",
											JOptionPane.YES_OPTION);
								}
								if (res > 0) {
									JOptionPane.showMessageDialog(null, "注册成功",
											"提示：", JOptionPane.YES_OPTION);
									dispose();// 销毁
								} else
									JOptionPane.showMessageDialog(null,
											"用户名已存在", "提示：",
											JOptionPane.YES_OPTION);
							} else {
								// 邮箱格式错误
								JOptionPane.showMessageDialog(null,
										"邮箱格式有误，请如实填写！", "提示：",
										JOptionPane.YES_OPTION);
								emailTextField.setText("");
							}

						}
					} else {
						JOptionPane.showMessageDialog(null, "验证码错误", "提示：",
								JOptionPane.YES_OPTION);
						safeCode = RandomUtil.getSafeCode();
						showCodeLabel.setText(safeCode.getCode());
					}

				} else {// 输入信息存在null值
					JOptionPane.showMessageDialog(null, "请补全信息", "提示：",
							JOptionPane.YES_OPTION);
				}

			}
		});

		this.add(panel, BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
}
