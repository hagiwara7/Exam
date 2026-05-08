package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action{
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    //////////追記//////////
		// subject_update.jsp（科目変更画面）に変更対象となる
		// 学生の情報を送る
		// 受け渡す情報を変数に入れてリクエストスコープに入れて
		// jspファイルに情報を渡す
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String cd=""; // 科目コード
		String name=""; // 科目名
		
		Subject subject=new Subject();
		SubjectDao subjectDao=new SubjectDao();// 科目の情報をDBから取得するのに必要
		
		// リクエストパラメータの取得→変更対象の科目コードを取得
		cd=req.getParameter("cd");
		// DBから科目コードと学校コードの情報を使って科目の詳細データ取得
		subject = subjectDao.get(cd, teacher.getSchool());

		// 上で定義した変数にjspに受け渡すデータを格納して
		// リクエストスコープにセットする	
		name=subject.getName();
		
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);

		//JSPへフォワード
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}

}
