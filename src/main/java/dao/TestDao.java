package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    private String baseSql =
        "select s.ent_year, s.class_num, s.no as student_no, s.name as student_name, t.point " +
        "from student s " +
        "left join test t on s.no = t.student_no " +
        "and t.subject_cd = ? " +
        "and t.school_cd = ? " +
        "and t.no = ? " +
        "where s.ent_year = ? " +
        "and s.class_num = ? " +
        "and s.school_cd = ? " +
        "and s.is_attend = true ";

    public Test get(Student student, Subject subject, School school, int no)
            throws Exception {

        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                "select * from test " +
                "where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
            );

            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                test = new Test();

                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(resultSet.getInt("no"));
                test.setPoint(resultSet.getInt("point"));
                test.setClassNum(resultSet.getString("class_num"));
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return test;
    }

    // SQLから取ってきたresultSetをList型に詰め直し
    private List<Test> postFilter(ResultSet resultSet, School school)
            throws Exception {

        List<Test> list = new ArrayList<>();

        while (resultSet.next()) {
            Test test = new Test();
            Student student = new Student();

            student.setEntYear(resultSet.getInt("ent_year"));
            student.setClassNum(resultSet.getString("class_num"));
            student.setNo(resultSet.getString("student_no"));
            student.setName(resultSet.getString("student_name"));
            student.setSchool(school);

            test.setStudent(student);
            test.setSchool(school);
            test.setClassNum(resultSet.getString("class_num"));

            if (resultSet.getObject("point") != null) {
                test.setPoint(resultSet.getInt("point"));
            }

            list.add(test);
        }

        return list;
    }

    // SQLからデータを取ってきて、Listに詰めるメソッド
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school)
            throws Exception {

        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(baseSql + "order by s.no");

            statement.setString(1, subject.getCd());
            statement.setString(2, school.getCd());
            statement.setInt(3, num);
            statement.setInt(4, entYear);
            statement.setString(5, classNum);
            statement.setString(6, school.getCd());

            resultSet = statement.executeQuery();

            list = postFilter(resultSet, school);

            for (Test test : list) {
                test.setSubject(subject);
                test.setNo(num);
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return list;
    }

    public boolean save(List<Test> list) throws Exception {
        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);

            for (Test test : list) {
                if (!save(test, connection)) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            connection.rollback();
            throw e;

        } finally {
            connection.close();
        }
    }

    public void save(Test test) throws Exception {

	    Connection con = getConnection();

	    // 既存確認
	    String checkSql =
	        "SELECT COUNT(*) FROM TEST " +
	        "WHERE SCHOOL_CD=? AND STUDENT_NO=? " +
	        "AND SUBJECT_CD=? AND NO=?";

	    PreparedStatement checkSt = con.prepareStatement(checkSql);

	    checkSt.setString(1, test.getSchoolCd());
	    checkSt.setString(2, test.getStudentNo());
	    checkSt.setString(3, test.getSubjectCd());
	    checkSt.setInt(4, test.getNo());

	    ResultSet rs = checkSt.executeQuery();

	    rs.next();

	    int count = rs.getInt(1);

	    if (count == 0) {

	        // INSERT
	        String insertSql =
	            "INSERT INTO TEST(SCHOOL_CD, STUDENT_NO, SUBJECT_CD, NO, POINT,CLASS_NUM) " +
	            "VALUES(?,?,?,?,?,?)";

	        PreparedStatement st = con.prepareStatement(insertSql);

	        st.setString(1, test.getSchoolCd());
	        st.setString(2, test.getStudentNo());
	        st.setString(3, test.getSubjectCd());
	        st.setInt(4, test.getNo());
	        st.setInt(5, test.getPoint());
	        st.setString(6, test.getClassNum());

	        st.executeUpdate();

	    } else {

	        // UPDATE
	        String updateSql =
	            "UPDATE TEST SET POINT=?, CLASS_NUM=? " +
	            "WHERE SCHOOL_CD=? AND STUDENT_NO=? " +
	            "AND SUBJECT_CD=? AND NO=?";

	        PreparedStatement st = con.prepareStatement(updateSql);

	        st.setInt(1, test.getPoint());
	        st.setString(2, test.getClassNum());

	        st.setString(3, test.getSchoolCd());
	        st.setString(4, test.getStudentNo());
	        st.setString(5, test.getSubjectCd());
	        st.setInt(6, test.getNo());

	        st.executeUpdate();
	    }

	    con.close();
	}
}