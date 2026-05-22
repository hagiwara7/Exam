package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		//
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		//科目コードと科目名を取得
		String cd = req.getParameter("subject_cd");
		String name = req.getParameter("subject_name");

		
		SubjectDao subjectDao=new SubjectDao();
		
		Subject subject = new Subject();

		//削除対象の情報をセット
		subject.setCd(cd);
		subject.setSchool(teacher.getSchool());
		
		subjectDao.delete(subject);
		
		//JSPへフォワード
		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}
}
