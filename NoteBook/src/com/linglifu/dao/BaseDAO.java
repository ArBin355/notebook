package com.linglifu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO {
	// 数据库登录名／密码
	private static final String NAME = "root";
	private static final String PWD = "root";
	private static final String DRIVER = "com.mysql.jdbc.Driver";// 数据库驱动
	// 数据库连接URL
	private static final String URL = "jdbc:mysql://localhost:3306/notebookswing?useUnicode=true&characterEncoding=utf-8";

	/**
	 * 获取数据库连接
	 * 
	 * @return Connection对象
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);// 加载mysql驱动
									// 成功加载后，会将Driver类的实例注册到DriverManager类中
			System.out.println("---加载数据库驱动成功---");
			conn = DriverManager.getConnection(URL, NAME, PWD);// 获取数据库连接
			System.out.println("---数据库连接成功---");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("---加载数据库驱动失败---");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("---数据库连接失败---");
		}
		return conn;
	}

	/**
	 * 关闭数据库资源
	 * 
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public void destory(ResultSet rs, PreparedStatement ps, Connection conn) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
