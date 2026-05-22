package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
 
public class SubjectDao extends Dao {

	// 科目情報を取得
	public Subject get(String cd, School school) throws Exception {

		Connection con = getConnection();
		PreparedStatement st = null;

		Subject subject = new Subject();

		try {

			// 科目検索SQL
			st = con.prepareStatement(
				"select * from subject where school_cd=? and cd=?"
			);

			st.setString(1, school.getCd());
			st.setString(2, cd);

			ResultSet rs = st.executeQuery();

			// データが存在する場合
			if(rs.next()) {

				subject.setCd(cd);
				subject.setName(rs.getString("name"));
				subject.setSchool(school);

			} else {

				subject = null;
			}

		} catch(Exception e) {

			throw e;

		} finally {

			if(st != null) {
				try {
					st.close();
				} catch(SQLException sqle) {
					throw sqle;
				}
			}
			
			if(con != null) {
				try {
					con.close();
				} catch(SQLException sqle) {
					throw sqle;
				}
			}
		}

		return subject;
	}
 

	// 学校ごとの科目一覧を取得
	public List<Subject> filter(School school) throws Exception {
 
		Connection con = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		List<Subject> list = new ArrayList<>();

		try {

			// 科目一覧取得SQL
			st = con.prepareStatement(
				"select * from subject where school_cd=?"
			);

			st.setString(1, school.getCd());

			rs = st.executeQuery();

			// 検索結果をリストに追加
			while (rs.next()) {

				Subject subject = new Subject();

				subject.setCd(rs.getString("cd"));
				subject.setName(rs.getString("name"));
				subject.setSchool(school);

				list.add(subject);
			}

		} catch(Exception e){

			e.printStackTrace();

		} finally {

			if(st != null) {
				try {
					st.close();
				} catch(SQLException sqle){
					throw sqle;
				}
			}

			if(con != null) {
				try {
					con.close();
				} catch(SQLException sqle){
					throw sqle;
				}
			}
		}

		return list;
	}
 

	// 科目情報の登録・更新
	public boolean save(Subject subject) throws Exception {

		Connection con = getConnection();
		PreparedStatement st = null;

		int cnt = 0;

		try {

			// 既存データ確認
			Subject old = this.get(subject.getCd(), subject.getSchool());

			// 新規登録
			if(old == null) {

				st = con.prepareStatement(
					"insert into subject(cd, name, school_cd) values(?, ?, ?)"
				);

				st.setString(1, subject.getCd());
				st.setString(2, subject.getName());
				st.setString(3, subject.getSchool().getCd());

				cnt = st.executeUpdate();

			} else {

				// 更新処理
				st = con.prepareStatement(
					"update subject set name=? where cd=? and school_cd=?"
				);

				st.setString(1, subject.getName());
				st.setString(2, old.getCd());
				st.setString(3, old.getSchool().getCd());

				cnt = st.executeUpdate();
			}

		} catch(Exception e){

			throw e;

		} finally {

			if(st != null) {
				try {
					st.close();
				} catch(SQLException sqle){
					throw sqle;
				}
			}

			if(con != null) {
				try{
					con.close();
				} catch(SQLException sqle){
					throw sqle;
				}
			}
		}

		// 更新件数を判定
		if(cnt > 0){
			return true;
		} else {
			return false;
		}
	}
 
 
	// 科目情報を削除
	public boolean delete(Subject subject) throws Exception {

		Connection con = getConnection();
		PreparedStatement st = null;

		int cnt = 0;

		try {

			// 削除対象を確認
			Subject old = this.get(subject.getCd(), subject.getSchool());

			if(old == null) {

			} else {

				// 削除SQL
				st = con.prepareStatement(
					"delete from subject where cd=? and school_cd=?"
				);

				st.setString(1, subject.getCd());
				st.setString(2, subject.getSchool().getCd());

				cnt = st.executeUpdate();
			}

		} catch(Exception e){	

			throw e;

		} finally {

			if(st != null) {
				try {
					st.close();
				} catch(SQLException sqle) {
					throw sqle;
				}
			}

			if(con != null) {
				try {
					con.close();
				} catch(SQLException sqle) {
					throw sqle;
				}
			}
		}
 
		// 削除件数を判定
		if(cnt > 0) {
			return true;
		} else {
			return false;
		}
	}
}
