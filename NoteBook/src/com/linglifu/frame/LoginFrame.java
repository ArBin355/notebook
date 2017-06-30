package com.linglifu.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.linglifu.dao.impl.UserDAO;
import com.linglifu.util.RandomUtil;
import com.linglifu.util.StringUtil;
import com.linglifu.vo.SafeCode;
import com.linglifu.vo.User;

public class LoginFrame extends JFrame {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 200;
	private static final String FRAME_NAME = "用户登录";
	private SafeCode safeCode;

	public LoginFrame() {
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
		JPanel panel = new JPanel(new GridLayout(4, 2));
		JLabel nameLabel = new JLabel("账号");
		JLabel pwdLabel = new JLabel("密码");

		final JTextField nameTextField = new JTextField();
		final JPasswordField pwdField = new JPasswordField();
		pwdField.setHorizontalAlignment(JPasswordField.CENTER);

		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		pwdLabel.setHorizontalAlignment(JLabel.CENTER);
		nameTextField.setHorizontalAlignment(JTextField.CENTER);

		final JLabel codeLabel = new JLabel();
		codeLabel.setText("验证码");
		JButton changeBtn = new JButton("换一个");
		changeBtn.setHorizontalAlignment(JButton.CENTER);
		codeLabel.setHorizontalAlignment(JLabel.CENTER);

		safeCode = RandomUtil.getSafeCode();// 获取验证码对象
		codeLabel.setText(safeCode.getCode());

		JLabel inputCodeLabel = new JLabel("验证码");
		inputCodeLabel.setHorizontalAlignment(JLabel.CENTER);
		final JTextField codeTextField = new JTextField();
		codeTextField.setHorizontalAlignment(JTextField.CENTER);

		panel.add(nameLabel);
		panel.add(nameTextField);
		panel.add(pwdLabel);
		panel.add(pwdField);
		panel.add(codeLabel);
		panel.add(changeBtn);
		panel.add(inputCodeLabel);
		panel.add(codeTextField);

		JPanel btnPanel = new JPanel(new GridLayout(1, 3));
		JButton backPwdBtn = new JButton("找回密码");
		JButton regBtn = new JButton("注册");
		JButton loginBtn = new JButton("登录");
		btnPanel.add(backPwdBtn);
		btnPanel.add(regBtn);
		btnPanel.add(loginBtn);

		changeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				safeCode = RandomUtil.getSafeCode();
				codeLabel.setText(safeCode.getCode());
			}
		});

		regBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				new RegFrame();
			}
		});
		backPwdBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				new BackPwdFrame();
			}
		});

		loginBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				UserDAO userDAO = new UserDAO();
				String name = nameTextField.getText().trim();
				String pwd = pwdField.getText().trim();
				String inputCode = codeTextField.getText().trim();
				if (!StringUtil.isNull(name) && !StringUtil.isNull(pwd)
						&& !StringUtil.isNull(inputCode)) {

					if (safeCode.getAnswer() == Integer.parseInt(inputCode)) {
						User user = userDAO.find(name, pwd);
						if (user != null) {
							new MainFrame(user);
							dispose();// 销毁
						} else {
							JOptionPane.showMessageDialog(null, "用户名／密码错误",
									"提示：", JOptionPane.YES_OPTION);
						}
					} else {// 验证码错误
						JOptionPane.showMessageDialog(null, "验证码错误", "提示：",
								JOptionPane.YES_OPTION);
						codeTextField.setText("");
					}

				}
			}
		});

		this.add(panel, BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
}
