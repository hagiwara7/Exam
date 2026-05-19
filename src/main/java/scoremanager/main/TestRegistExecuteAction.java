package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws Exception {

        req.setCharacterEncoding("UTF-8");

        // ログイン学校情報
        School school = (School) req.getSession().getAttribute("school");

        // パラメータ取得
        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");
        String subjectCd = req.getParameter("subjectCd");
        String noStr = req.getParameter("no");

        int entYear = Integer.parseInt(entYearStr);
        int no = Integer.parseInt(noStr);

        // DAO
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();
        ClassNumDao classNumDao = new ClassNumDao();

        // 科目取得
        Subject subject = subjectDao.get(subjectCd, school);

        // 学生一覧取得
        List<Student> students =
                studentDao.filter(school, entYear, classNum, true);

        // エラー格納用
        Map<String, String> errors = new HashMap<>();

        // 入力値保持用
        Map<String, String> points = new HashMap<>();

        // -----------------------------
        // 入力チェック
        // -----------------------------
        for (Student student : students) {

            String pointStr =
                    req.getParameter("point_" + student.getNo());

            // 入力値保持
            points.put(student.getNo(), pointStr);

            // 未入力
            if (pointStr == null || pointStr.isEmpty()) {

                errors.put(
                        student.getNo(),
                        "点数を入力してください"
                );

                continue;
            }

            try {

                int point = Integer.parseInt(pointStr);

                // 範囲チェック
                if (point < 0 || point > 100) {

                    errors.put(
                            student.getNo(),
                            "0～100の範囲で入力してください"
                    );
                }

            } catch (NumberFormatException e) {

                errors.put(
                        student.getNo(),
                        "数値で入力してください"
                );
            }
        }

        // エラーがある場合
        if (!errors.isEmpty()) {

            // プルダウン再設定
            req.setAttribute(
                    "subjectSet",
                    subjectDao.filter(school)
            );

            req.setAttribute(
                    "classNumSet",
                    classNumDao.filter(school)
            );

            req.setAttribute(
                    "entYearSet",
                    studentDao.filterEntYear(school)
            );

            // エラー
            req.setAttribute("errors", errors);

            // 入力値保持
            req.setAttribute("points", points);

            // 一覧再表示
            req.setAttribute("students", students);

            // 科目
            req.setAttribute("subject", subject);

            // 選択値保持
            req.setAttribute("entYear", entYear);
            req.setAttribute("classNum", classNum);
            req.setAttribute("subjectCd", subjectCd);
            req.setAttribute("no", no);

            req.getRequestDispatcher("TestRegist.jsp")
               .forward(req, res);

            return;
        }
        // 登録処理
        for (Student student : students) {

            String pointStr =
                    req.getParameter("point_" + student.getNo());

            int point = Integer.parseInt(pointStr);

            Test test = new Test();

            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setClassNum(student.getClassNum());
            test.setNo(no);
            test.setPoint(point);

            testDao.save(test);
        }

        // 完了画面
        req.getRequestDispatcher("score_create_done.jsp")
           .forward(req, res);
    }
}
