package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateAction extends Action {

	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {

		String classNum = request.getParameter("class_num");

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		ClassNumDao dao = new ClassNumDao();

		ClassNum classnum =
				dao.get(classNum, teacher.getSchool());

		request.setAttribute("classnum", classnum);

		request.getRequestDispatcher("class_update.jsp")
			.forward(request, response);
	}
}