package scoremanager.main;

import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログインユーザを取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 科目登録画面へフォワード
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}
