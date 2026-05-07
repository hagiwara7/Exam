package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestStudentDao extends Dao{
	
	private List<TestListStudent> postFilter(ResultSet resultSet, Student student) throws Exception {

		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while (resultSet.next()) {
				// 学生インスタンスを初期化
				TestListStudent testListStudent = new TestListStudent();
				// 学生インスタンスに検索結果をセット
				testListStudent.setSubjectName(resultSet.getString("subject_name"));
				testListStudent.setSubjectCd(resultSet.getString("subject_cd"));
				testListStudent.setNum(resultSet.getInt("num"));
				testListStudent.setPoint(resultSet.getInt("point"));
				// リストに追加
				list.add(testListStudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<TestListStudent> filter(Student student) throws Exception {

		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String order = " order by no asc";


		try {
			// プリペアードステートメントにSQL文をセット
			statement=connection.prepareStatement("select * from test"+order);
			// プリペアードステートメントを実行
			resultSet=statement.executeQuery();
			// リストへの格納処理を実行
			list=postFilter(resultSet, student);

			
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}

}
