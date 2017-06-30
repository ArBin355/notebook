package com.linglifu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.linglifu.dao.BaseDAO;
import com.linglifu.util.StringUtil;
import com.linglifu.vo.Diary;

public class DiaryDAO extends BaseDAO {

	/**
	 * 查询日记列表
	 * 
	 * @param userId
	 *            登录的用户ID
	 * @param whereCause
	 *            查询条件
	 * @param query
	 *            查询参数
	 * @return 包含查询到的Diary对象ArrayList
	 */
	public List<Diary> query(String userId, String whereCause, String query) {
		List<Diary> list = null;
		Connection conn = this.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder(
				"SELECT id, user_id, date, weather, mood, title, content FROM _diary WHERE user_id = ? ");
		if (!StringUtil.isNull(whereCause) && !StringUtil.isNull(query))
			sb.append("AND " + whereCause + " like '%" + query + "%'");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, userId);
			rs = ps.executeQuery();
			list = new ArrayList<Diary>();
			Diary d;
			while (rs.next()) {
				d = new Diary();
				d.setId(rs.getString(1));
				d.setUserId(rs.getString(2));
				d.setDate(rs.getString(3));
				d.setWeather(rs.getString(4));
				d.setMood(rs.getString(5));
				d.setTitle(rs.getString(6));
				d.setContent(rs.getString(7));
				list.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.destory(rs, ps, conn);
		}
		System.out.println("DiaryDAO->" + list);
		return list;
	}

	/**
	 * 向数据库中添加日记
	 * 
	 * @param diary
	 *            添加的日记
	 * @return 执行条数
	 */
	public int insert(Diary diary) {
		int res = 0;
		Connection conn = this.getConnection();
		String sql = "INSERT INTO _diary (id, user_id, date, weather, mood, title, content) VALUES(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, diary.getId());
			ps.setString(2, diary.getUserId());
			ps.setString(3, diary.getDate());
			ps.setString(4, diary.getWeather());
			ps.setString(5, diary.getMood());
			ps.setString(6, diary.getTitle());
			ps.setString(7, diary.getContent());
			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.destory(null, ps, conn);
		}
		return res;
	}
}
