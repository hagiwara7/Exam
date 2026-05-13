package scoremanager.main;

import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws Exception {

        // パラメータ取得
    	String schoolCd = req.getParameter("schoolCd");
        String studentNo = req.getParameter("student_no");
        String subjectCd = req.getParameter("subject_cd");
        int no = Integer.parseInt(req.getParameter("no"));
        int point = Integer.parseInt(req.getParameter("point"));
        String classNum = req.getParameter("class_num");

        // Beanへセット
        Test test = new Test();

        test.setSchoolCd("oom");
        test.setStudentNo(studentNo);
        test.setSubjectCd(subjectCd);
        test.setNo(no);
        test.setPoint(point);
        test.setClassNum(classNum);

        // DB登録
        TestDao dao = new TestDao();

        dao.save(test);

        // 完了画面
        req.getRequestDispatcher("score_create_done.jsp")
           .forward(req, res);
    }
}