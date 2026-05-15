package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
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

        // パラメータ取得
        String schoolCd = req.getParameter("schoolCd");
        String subjectCd = req.getParameter("subjectCd");
        String classNum = req.getParameter("classNum");

        int no = Integer.parseInt(
                req.getParameter("no")
        );

        String[] studentNos =
                req.getParameterValues("student_no");

        String[] points =
                req.getParameterValues("point");

        TestDao dao = new TestDao();

        for (int i = 0; i < studentNos.length; i++) {

            // Bean生成
            School school = new School();
            school.setCd(schoolCd);

            Student student = new Student();
            student.setNo(studentNos[i]);

            Subject subject = new Subject();
            subject.setCd(subjectCd);

            // Test生成
            Test test = new Test();

            test.setSchool(school);
            test.setStudent(student);
            test.setSubject(subject);

            test.setNo(no);
            test.setClassNum(classNum);

            // 点数
            int point = 0;

            if (points[i] != null &&
                !points[i].isEmpty()) {

                point = Integer.parseInt(points[i]);
            }

            test.setPoint(point);

            // 保存
            dao.save(test);
        }

        req.setAttribute(
                "message",
                "登録しました"
        );

        req.getRequestDispatcher(
                "score_create_done.jsp"
        ).forward(req, res);
    }
}
