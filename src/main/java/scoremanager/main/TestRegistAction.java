// TestRegistAction.java

package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Subject;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {

		// DAO
		StudentDao sDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();

		// パラメータ取得
		String entYear = request.getParameter("entYear");
		String classNum = request.getParameter("classNum");
		String subjectCd = request.getParameter("subjectCd");
		String no = request.getParameter("no");

		// プルダウン用
		request.setAttribute("entYearSet", sDao.getEntYearSet());
		// 修正後
		request.setAttribute("classNumSet", sDao.getClassNumSet());
		request.setAttribute("subjectSet", subDao.filter());

		// 検索条件保持
		request.setAttribute("entYear", entYear);
		request.setAttribute("classNum", classNum);
		request.setAttribute("subjectCd", subjectCd);
		request.setAttribute("no", no);

		// 条件指定時のみ検索
		if (entYear != null &&
			classNum != null &&
			subjectCd != null &&
			no != null) {

			List<Student> students =
					sDao.filter(
							Integer.parseInt(entYear),
							classNum
					);

			request.setAttribute("students", students);

			// 科目名取得
			List<Subject> subs = subDao.filter();

			for (Subject s : subs) {
				if (s.getCd().equals(subjectCd)) {
					request.setAttribute(
							"subjectName",
							s.getName()
					);
				}
			}
		}

		request.getRequestDispatcher(
				"TestRegist.jsp"
		).forward(request, response);
	}
}