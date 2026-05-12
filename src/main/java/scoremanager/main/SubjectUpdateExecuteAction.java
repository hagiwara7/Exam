package scoremanager.main;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class SubjectUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
 
		String cd=""; // 科目コード
		String name=""; // 科目名
		
		Subject subject=new Subject();
		SubjectDao subjectDao=new SubjectDao(); // 科目の情報をDBから取得するのに必要
		
		//リクエストパラメータから取得
		cd=req.getParameter("cd");
		name=req.getParameter("name");
		
		//subjectに変更する科目の情報をセット
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());
		
		// DBに変更内容を保存
		subjectDao.save(subject);
		// JSPへフォワード 7
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	}
}