package com.linglifu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.linglifu.dao.BaseDAO;
import com.linglifu.vo.User;

public class UserDAO extends BaseDAO {

	/**
	 * 查找登录用户
	 * 
	 * @param name
	 *            账号
	 * @param pwd
	 *            密码
	 * @return 登录的用户对象
	 */
	public User find(String name, String pwd) {
		User user = null;
		Connection conn = this.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT id, name, pwd, nickname, question, answer, email FROM _user WHERE name = ? AND pwd = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getString(1));
				user.setName(rs.getString(2));
				user.setPwd(rs.getString(3));
				user.setNickname(rs.getString(4));
				user.setQuestion(rs.getString(5));
				user.setAnswer(rs.getString(6));
				user.setEmail(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.destory(rs, ps, conn);
		}
		return user;
	}

	/**
	 * 找回密码
	 * 
	 * @param user
	 * @return
	 */
	public int update(User user) {
		int res = 0;
		Connection conn = this.getConnection();
		PreparedStatement ps = null;
		String sql = "UPDATE _user SET pwd = ? WHERE name = ? AND question = ? AND answer = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getPwd());
			ps.setString(2, user.getName());
			ps.setString(3, user.getQuestion());
			ps.setString(4, user.getAnswer());
			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.destory(null, ps, conn);
		}
		return res;
	}

	/**
	 * 用户注册 插入到数据库
	 * 
	 * @param user
	 *            注册的用户
	 * @return 执行条数
	 * @throws SQLException
	 */
	public int insert(User user) {
		int res = 0;
		Connection conn = this.getConnection();
		String sql = "INSERT INTO _user (id, name, pwd, nickname, question, answer, email) VALUES(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPwd());
			ps.setString(4, user.getNickname());
			ps.setString(5, user.getQuestion());
			ps.setString(6, user.getAnswer());
			ps.setString(7, user.getEmail());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.destory(null, ps, conn);
		}

		return res;
	}
}
