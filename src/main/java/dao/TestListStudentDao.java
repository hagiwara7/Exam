package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    private String baseSql =
        "select subject.name as subject_name, " +
        "subject.cd as subject_cd, " +
        "test.no as num, " +
        "test.point as point " +
        "from test " +
        "join subject on test.subject_cd = subject.cd " +
        "and test.school_cd = subject.school_cd ";

    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {

        List<TestListStudent> list = new ArrayList<>();

        while (rSet.next()) {
            TestListStudent testListStudent = new TestListStudent();

            testListStudent.setSubjectName(rSet.getString("subject_name"));
            testListStudent.setSubjectCd(rSet.getString("subject_cd"));
            testListStudent.setNum(rSet.getInt("num"));
            testListStudent.setPoint(rSet.getInt("point"));

            list.add(testListStudent);
        }

        return list;
    }

    public List<TestListStudent> filter(Student student) throws Exception {

        List<TestListStudent> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(
                baseSql +
                "where test.student_no = ? " +
                "and test.school_cd = ? " +
                "order by subject.cd, test.no"
            );

            statement.setString(1, student.getNo());
            statement.setString(2, student.getSchool().getCd());

            rSet = statement.executeQuery();

            list = postFilter(rSet);

        } finally {
            if (rSet != null) {
                rSet.close();
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
}