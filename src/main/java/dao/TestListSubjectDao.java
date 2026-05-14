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

			for (TestListSubject testSub : list) {
				if (testSub.getStudentNo().equals(studentNo)) {
					target = testSub;
					break;
				}
			}

			if (target == null) {
				target = new TestListSubject();
				target.setEntYear(rs.getInt("ent_year"));
				target.setClassNum(rs.getString("class_num"));
				target.setStudentNo(studentNo);
				target.setStudentName(rs.getString("student_name"));
				list.add(target);
			}

			target.setNo(rs.getInt("no"));
			target.setPoint(rs.getInt("point"));
		}

		return list;
	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
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