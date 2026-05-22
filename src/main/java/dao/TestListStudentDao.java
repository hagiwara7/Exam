package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    // 学生別成績一覧を取得するための基本SQL文
    private String baseSql =
        "select subject.name as subject_name, " +
        "subject.cd as subject_cd, " +
        "test.no as num, " +
        "test.point as point " +
        "from test " +
        "join subject on test.subject_cd = subject.cd " +
        "and test.school_cd = subject.school_cd ";

    /**
     * postFilterメソッド
     * リザルトセットの内容をTestListStudent型のリストに変換する
     *
     * @param rSet:ResultSet
     * @return 学生別成績一覧:List<TestListStudent>
     * @throws Exception
     */
    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {

        // リストを初期化
        List<TestListStudent> list = new ArrayList<>();

        // リザルトセットを全件走査
        while (rSet.next()) {
            // 学生別成績インスタンスを初期化
            TestListStudent testListStudent = new TestListStudent();

            // 学生別成績インスタンスに検索結果をセット
            testListStudent.setSubjectName(rSet.getString("subject_name"));
            testListStudent.setSubjectCd(rSet.getString("subject_cd"));
            testListStudent.setNum(rSet.getInt("num"));
            testListStudent.setPoint(rSet.getInt("point"));

            // リストに学生別成績インスタンスを追加
            list.add(testListStudent);
        }

        return list;
    }

    /**
     * filterメソッド
     * 学生を指定して成績一覧を取得する
     *
     * @param student:Student
     * @return 学生別成績一覧:List<TestListStudent>
     * @throws Exception
     */
    public List<TestListStudent> filter(Student student) throws Exception {

        // リストを初期化
        List<TestListStudent> list = new ArrayList<>();

        // データベースへのコネクションを確立
        Connection connection = getConnection();

        // プリペアードステートメント
        PreparedStatement statement = null;

        // リザルトセット
        ResultSet rSet = null;

        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(
                baseSql +
                "where test.student_no = ? " +
                "and test.school_cd = ? " +
                "order by subject.cd, test.no"
            );

            // プリペアードステートメントに学生番号をバインド
            // 1つ目の?に「student.getNo()」の値をセット
            statement.setString(1, student.getNo());

            // プリペアードステートメントに学校コードをバインド
            // 2つ目の?に「student.getSchool().getCd()」の値をセット
            statement.setString(2, student.getSchool().getCd());

            // プリペアードステートメントを実行
            rSet = statement.executeQuery();

            // 検索結果をリストに変換
            list = postFilter(rSet);

        } finally {
            // リザルトセットを閉じる
            if (rSet != null) {
                rSet.close();
            }

            // プリペアードステートメントを閉じる
            if (statement != null) {
                statement.close();
            }

            // コネクションを閉じる
            if (connection != null) {
                connection.close();
            }
        }

        return list;
    }
}