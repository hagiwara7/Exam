package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    //////////追記//////////
		// student_update.jsp（学生変更画面）に変更対象となる
		// 学生の情報を送る
		// 受け渡す情報を変数に入れてリクエストスコープに入れて
		// jspファイルに情報を渡す
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		String no=""; // 学生番号
		String name=""; // 氏名
		int ent_year=0; // 入学年度
		String class_num=""; // クラス番号
		boolean isAttend=false; // 在学フラグ
		
		Student student=new Student();
		StudentDao studentDao=new StudentDao(); // 学生の情報をDBから取得するのに必要
		ClassNumDao classNumDao=new ClassNumDao(); // クラスの情報をDBから取得するのに必要
		
		// リクエストパラメータの取得→変更対象の学生番号を取得
		no=req.getParameter("no");
		// DBから学生番号の情報を使って学生の詳細データ取得
		student=studentDao.get(no);
		// クラス情報の一覧を取得
		List<String> class_num_set=classNumDao.filter(teacher.getSchool());
		
		// 上で定義した変数にjspに受け渡すデータを格納して
		// リクエストスコープにセットする
		ent_year=student.getEntYear();
		name=student.getName();
		class_num=student.getClassNum();
		isAttend=student.isAttend();
		
		//
		req.setAttribute("ent_year", ent_year);
		req.setAttribute("no", no);
		req.setAttribute("name", name);
		req.setAttribute("class_num", class_num);
		req.setAttribute("class_num_set", class_num_set);
		req.setAttribute("isattend", isAttend);

		// JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}

}
