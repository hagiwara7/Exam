package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {

	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {

		request.setCharacterEncoding("UTF-8");

		String oldClassNum =
				request.getParameter("old_class_num");

		String classNum =
				request.getParameter("class_num");

		if (classNum == null || classNum.isEmpty()) {

			request.setAttribute("error",
					"クラス番号を入力してください");

			request.getRequestDispatcher("class_update.jsp")
				.forward(request, response);

			return;
		}

		HttpSession session = request.getSession();

		Teacher teacher =
				(Teacher) session.getAttribute("user");

		ClassNum classnum = new ClassNum();

		classnum.setClass_num(classNum);
		classnum.setSchool(teacher.getSchool());

		ClassNumDao dao = new ClassNumDao();

		boolean result =
				dao.update(oldClassNum, classnum);

		if (result) {

			request.setAttribute("message",
					"変更が完了しました");

		} else {

			request.setAttribute("error",
					"変更に失敗しました");
		}

		request.getRequestDispatcher("class_update_done.jsp")
			.forward(request, response);
	}
}