package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	// 成績一覧取得用SQL
	private String baseSql =
			"SELECT "
			+ "t.subject_cd, "
			+ "sub.name AS subject_name, "
			+ "t.no AS test_no, "
			+ "t.point AS test_point, "
			+ "s.no AS student_no, "
			+ "s.name AS student_name, "
			+ "s.ent_year AS ent_year, "
			+ "t.class_num AS class_num "
			+ "FROM test t "
			+ "JOIN subject sub ON t.subject_cd = sub.cd "
			+ "JOIN student s ON t.student_no = s.no "
			+ "WHERE s.ent_year = ? "
			+ "AND t.class_num = ? "
			+ "AND t.subject_cd = ? "
			+ "AND s.school_cd = ? "
			+ "ORDER BY student_no";

	private List<TestListSubject> postFilter(ResultSet rs) throws Exception {

		List<TestListSubject> list = new ArrayList<>();

		while (rs.next()) {

			String studentNo = rs.getString("student_no");

			TestListSubject target = null;

			// 同じ学生番号のデータを探す
			for (TestListSubject testSub : list) {
				if (testSub.getStudentNo().equals(studentNo)) {
					target = testSub;
					break;
				}
			}

			// 学生情報を新規作成
			if (target == null) {

				target = new TestListSubject();

				target.setEntYear(rs.getInt("ent_year"));
				target.setClassNum(rs.getString("class_num"));
				target.setStudentNo(rs.getString("student_no"));
				target.setStudentName(rs.getString("student_name"));

				list.add(target);
			}

			// 点数情報をMapに追加
			int no = rs.getInt("test_no");
			int point = rs.getInt("test_point");

			target.getPoints().put(String.valueOf(no), point);
		}

		return list;
	}

	// 条件に一致する成績一覧を取得
	public List<TestListSubject> filter(
			int entYear,
			String classNum,
			Subject subject,
			School school) throws Exception {

		List<TestListSubject> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = con.prepareStatement(baseSql);

			st.setInt(1, entYear);
			st.setString(2, classNum);
			st.setString(3, subject.getCd());
			st.setString(4, school.getCd());

			rs = st.executeQuery();

			// 検索結果をリスト化
			list = this.postFilter(rs);

		} catch (Exception e) {

			throw e;

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
}
