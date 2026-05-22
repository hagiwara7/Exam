package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassListAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        Teacher teacher =
                (Teacher)request.getSession().getAttribute("user");

        ClassNumDao dao = new ClassNumDao();

        List<String> classList =
                dao.filter(teacher.getSchool());

        request.setAttribute("classList", classList);

        // 画面表示
        request.getRequestDispatcher("class_list.jsp")
               .forward(request, response);
    }
}