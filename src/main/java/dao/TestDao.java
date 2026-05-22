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

    // 科目別成績一覧を取得するための基本SQL文
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

    /**
     * getメソッド
     * 学生・科目・学校・回数を指定して成績情報を1件取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        // 成績インスタンスを初期化
        Test test = null;
        // データベースへのコネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet resultSet = null;

        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(
                "select * from test " +
                "where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
            );
            // プリペアードステートメントに値をバインド
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            // プリペアードステートメントを実行
            resultSet = statement.executeQuery();

            // リザルトセットが存在する場合
            if (resultSet.next()) {
                // 成績インスタンスを生成
                test = new Test();
                // 成績インスタンスに検索結果をセット
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(resultSet.getInt("no"));
                test.setPoint(resultSet.getInt("point"));
                test.setClassNum(resultSet.getString("class_num"));
            }
        } finally {
            // リザルトセットを閉じる
            if (resultSet != null) {
                resultSet.close();
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
        return test;
    }

    /**
     * postFilterメソッド
     * リザルトセットの内容をTest型のリストに変換する
     */
    private List<Test> postFilter(ResultSet resultSet, School school) throws Exception {
        // リストを初期化
        List<Test> list = new ArrayList<>();

        // リザルトセットを全件走査
        while (resultSet.next()) {
            // 成績インスタンスを生成
            Test test = new Test();
            // 学生インスタンスを生成
            Student student = new Student();

            // 学生インスタンスに検索結果をセット
            student.setEntYear(resultSet.getInt("ent_year"));
            student.setClassNum(resultSet.getString("class_num"));
            student.setNo(resultSet.getString("student_no"));
            student.setName(resultSet.getString("student_name"));
            student.setSchool(school);

            // 成績インスタンスに学生・学校・クラス番号をセット
            test.setStudent(student);
            test.setSchool(school);
            test.setClassNum(resultSet.getString("class_num"));

            // 点数が存在する場合は成績インスタンスに点数をセット
            if (resultSet.getObject("point") != null) {
                test.setPoint(resultSet.getInt("point"));
            }

            // リストに成績インスタンスを追加
            list.add(test);
        }
        return list;
    }

    /**
     * filterメソッド
     * 入学年度・クラス番号・科目・回数・学校を指定して成績一覧を取得する
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        // リストを初期化
        List<Test> list = new ArrayList<>();
        // データベースへのコネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet resultSet = null;

        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + "order by s.no");

            // プリペアードステートメントに値をバインド
            statement.setString(1, subject.getCd());
            statement.setString(2, school.getCd());
            statement.setInt(3, num);
            statement.setInt(4, entYear);
            statement.setString(5, classNum);
            statement.setString(6, school.getCd());

            // プリペアードステートメントを実行
            resultSet = statement.executeQuery();

            // 検索結果をリストに変換
            list = postFilter(resultSet, school);

            // 取得した成績一覧に科目情報と回数をセット
            for (Test test : list) {
                test.setSubject(subject);
                test.setNo(num);
            }
        } finally {
            // リザルトセットを閉じる
            if (resultSet != null) {
                resultSet.close();
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

    /**
     * saveメソッド
     * 成績リストをまとめて登録・更新する
     */
    public boolean save(List<Test> list) throws Exception {
        // データベースへのコネクションを確立
        Connection connection = getConnection();

        try {
            // 自動コミットを無効化
            connection.setAutoCommit(false);

            // 成績リストを1件ずつ登録・更新
            for (Test test : list) {
                if (!save(test, connection)) {
                    // 登録・更新に失敗した場合はロールバック
                    connection.rollback();
                    return false;
                }
            }

            // すべて成功した場合はコミット
            connection.commit();
            return true;
        } catch (Exception e) {
            // 例外発生時はロールバック
            connection.rollback();
            throw e;
        } finally {
            // コネクションを閉じる
            connection.close();
        }
    }

    /**
     * saveメソッド
     * 成績情報を1件登録・更新する
     */
    private boolean save(Test test, Connection connection) throws Exception {
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int line = 0;

        // 既存データがあれば更新
        try {
            // プリペアードステートメントにUPDATE文をセット
            statement = connection.prepareStatement(
                "update test set point = ? " +
                "where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
            );
            // プリペアードステートメントに値をバインド
            statement.setInt(1, test.getPoint());
            statement.setString(2, test.getStudent().getNo());
            statement.setString(3, test.getSubject().getCd());
            statement.setString(4, test.getSchool().getCd());
            statement.setInt(5, test.getNo());

            // プリペアードステートメントを実行
            line = statement.executeUpdate();
        } finally {
            // プリペアードステートメントを閉じる
            if (statement != null) {
                statement.close();
            }
        }

        // 更新件数が0件の場合は新規登録
        if (line == 0) {
            try {
                // プリペアードステートメントにINSERT文をセット
                statement = connection.prepareStatement(
                    "insert into test(student_no, subject_cd, school_cd, no, point, class_num) " +
                    "values(?, ?, ?, ?, ?, ?)"
                );
                // プリペアードステートメントに値をバインド
                statement.setString(1, test.getStudent().getNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setString(3, test.getSchool().getCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClassNum());

                // プリペアードステートメントを実行
                line = statement.executeUpdate();
            } finally {
                // プリペアードステートメントを閉じる
                if (statement != null) {
                    statement.close();
                }
            }
        }
        return line == 1;
    }

    /**
     * saveメソッド
     * 成績情報を1件登録・更新する
     */
    public boolean save(Test test) throws Exception {
        // データベースへのコネクションを確立
        Connection con = getConnection();
        // プリペアードステートメント
        PreparedStatement st = null;
        // リザルトセット
        ResultSet rs = null;
        // 実行件数
        int count = 0;

        try {
            // 既存データを確認するSQL文をセット
            st = con.prepareStatement(
                "select * from test " +
                "where student_no=? " +
                "and subject_cd=? " +
                "and school_cd=? " +
                "and no=?"
            );
            // プリペアードステートメントに値をバインド
            st.setString(1, test.getStudent().getNo());
            st.setString(2, test.getSubject().getCd());
            st.setString(3, test.getSchool().getCd());
            st.setInt(4, test.getNo());

            // プリペアードステートメントを実行
            rs = st.executeQuery();

            // 既存データが存在するか判定
            boolean exists = rs.next();

            // リザルトセットとプリペアードステートメントを閉じる
            rs.close();
            st.close();

            // 既存データが存在する場合は更新
            if (exists) {
                // プリペアードステートメントにUPDATE文をセット
                st = con.prepareStatement(
                    "update test set point=?, class_num=? " +
                    "where student_no=? " +
                    "and subject_cd=? " +
                    "and school_cd=? " +
                    "and no=?"
                );
                // プリペアードステートメントに値をバインド
                st.setInt(1, test.getPoint());
                st.setString(2, test.getClassNum());
                st.setString(3, test.getStudent().getNo());
                st.setString(4, test.getSubject().getCd());
                st.setString(5, test.getSchool().getCd());
                st.setInt(6, test.getNo());
            } else {
                // 既存データが存在しない場合は新規登録
                st = con.prepareStatement(
                    "insert into test " +
                    "(student_no, subject_cd, school_cd, no, point, class_num) " +
                    "values (?, ?, ?, ?, ?, ?)"
                );
                // プリペアードステートメントに値をバインド
                st.setString(1, test.getStudent().getNo());
                st.setString(2, test.getSubject().getCd());
                st.setString(3, test.getSchool().getCd());
                st.setInt(4, test.getNo());
                st.setInt(5, test.getPoint());
                st.setString(6, test.getClassNum());
            }

            // プリペアードステートメントを実行
            count = st.executeUpdate();
        } finally {
            // リザルトセットを閉じる
            if (rs != null) {
                rs.close();
            }
            // プリペアードステートメントを閉じる
            if (st != null) {
                st.close();
            }
            // コネクションを閉じる
            if (con != null) {
                con.close();
            }
        }
        return count > 0;
    }

    /**
     * getメソッド
     * 学生番号・科目コード・回数・学校を指定して成績情報を1件取得する
     */
    public Test get(String studentNo, String subjectCd, String no, School school) throws Exception {
        // 成績インスタンスを初期化
        Test test = null;
        // データベースへのコネクションを確立
        Connection con = getConnection();

        // SQL文を作成
        String sql =
            "SELECT * FROM TEST " +
            "WHERE STUDENT_NO = ? " +
            "AND SUBJECT_CD = ? " +
            "AND NO = ? " +
            "AND SCHOOL_CD = ?";

        // プリペアードステートメントにSQL文をセット
        PreparedStatement st = con.prepareStatement(sql);

        // プリペアードステートメントに値をバインド
        st.setString(1, studentNo);
        st.setString(2, subjectCd);
        st.setInt(3, Integer.parseInt(no));
        st.setString(4, school.getCd());

        // プリペアードステートメントを実行
        ResultSet rs = st.executeQuery();

        // リザルトセットが存在する場合
        if (rs.next()) {
            // 成績インスタンスを生成
            test = new Test();
            // 成績インスタンスに点数をセット
            test.setPoint(rs.getInt("POINT"));
        }

        // リザルトセットを閉じる
        rs.close();
        // プリペアードステートメントを閉じる
        st.close();
        // コネクションを閉じる
        con.close();

        return test;
    }
}