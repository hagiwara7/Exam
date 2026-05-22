package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		//科目番号と科目名を取得
		String cd = req.getParameter("subject_cd");
		String name = req.getParameter("subject_name");

		
		SubjectDao subjectDao=new SubjectDao();
		//科目番号と教員情報から科目情報を取得
		Subject subject = subjectDao.get(cd, teacher.getSchool());

		
		req.setAttribute("subject",subject);
		
		// JSPへフォワード
		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);

	}
}
