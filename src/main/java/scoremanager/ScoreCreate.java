package scoremanager.main;

import java.util.List;

import javax.security.auth.Subject;

import bean.Student;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ScoreCreate")
public class ScoreCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {

            // 学生一覧取得
            StudentDao studentDao = new StudentDao();
            List<Student> list = studentDao.filter(null);

            // 科目一覧取得
            SubjectDao subjectDao = new SubjectDao();
            List<Subject> subjectList = subjectDao.filter(null);

            // JSPへセット
            request.setAttribute("list", list);
            request.setAttribute("subjectList", subjectList);

            // JSPへ遷移
            request.getRequestDispatcher(
                    "/scoremanager/main/score_create.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute("error",
                    "成績登録画面の表示に失敗しました");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}