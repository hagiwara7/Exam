package scoremanager.main;

import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");

        // 単体パラメータ

        String schoolCd  = req.getParameter("schoolCd");
        String subjectCd  = req.getParameter("subjectCd");
        String classNum   = req.getParameter("classNum");

        String entYearStr = req.getParameter("entYear");
        String noStr      = req.getParameter("no");

        if (schoolCd == null || subjectCd == null || noStr == null) {
            throw new IllegalArgumentException("必須パラメータ不足");
        }

        int no = Integer.parseInt(noStr);

        // 複数データ

        String[] studentNos = req.getParameterValues("student_no");
        String[] points     = req.getParameterValues("point");

        if (studentNos == null || points == null) {
            throw new IllegalArgumentException("学生データが取得できません");
        }

        TestDao dao = new TestDao();


        // 1件ずつ保存

        for (int i = 0; i < studentNos.length; i++) {

            Test test = new Test();

            test.setSchoolCd(schoolCd);
            test.setStudentNo(studentNos[i]);
            test.setSubjectCd(subjectCd);
            test.setNo(no);
            test.setClassNum(classNum);

            // 点数（空対策）
            int point = 0;
            if (points[i] != null && !points[i].isEmpty()) {
                try {
                    point = Integer.parseInt(points[i]);
                } catch (NumberFormatException e) {
                    point = 0;
                }
            }
            test.setPoint(point);

            // DAOで保存
            dao.save(test);
        }
        // 完了後遷移
        req.setAttribute("message", "登録しました");
        req.getRequestDispatcher("score_create_done.jsp").forward(req, res);
    }
}