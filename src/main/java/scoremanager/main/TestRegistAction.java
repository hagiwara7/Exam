package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        // セッション取得
        HttpSession session = request.getSession();

        // ログインユーザー取得
        Teacher user = (Teacher)session.getAttribute("user");

        // ログインチェック
        if (user == null) {
            response.sendRedirect("Login.action");
            return;
        }

        // school取得
        School school = user.getSchool();

        // 科目一覧取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school);

        // クラス一覧取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(school);

        // JSPへ値を渡す
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("classList", classList);

        // 画面表示
        request.getRequestDispatcher("TestRegist.jsp")
               .forward(request, response);
    }
}