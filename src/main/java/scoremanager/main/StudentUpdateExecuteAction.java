package scoremanager.main;


import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    //////////追記//////////
		String no=""; // 学生番号
		String name=""; // 氏名
		int ent_year=0; // 入学年度
		String class_num=""; // クラス番号
		String isAttendStr=""; // リクエストパラメータで受け取る用
		boolean isAttend=false; // 在学フラグ
		
		Student student=new Student();
		StudentDao studentDao=new StudentDao(); // 学生の情報をDBから取得するのに必要
		
		//リクエストパラメータから取得
		ent_year=Integer.parseInt(req.getParameter("ent_year"));
		no=req.getParameter("no");
		name=req.getParameter("name");
		class_num=req.getParameter("class_num");
		isAttendStr=req.getParameter("is_attend");
//		System.out.println(ent_year);
//		System.out.println(no);
//		System.out.println(name);
//		System.out.println(class_num);
//		System.out.println(isAttendStr);
		
		//在籍中フラグに特に指定がなければ在籍中とみなす
		if(isAttendStr!=null) {
			isAttend=true;
		}
		
		//studentに変更する学生の情報をセット
		student.setNo(no);
		student.setName(name);
		student.setEntYear(ent_year);
		student.setClassNum(class_num);
		student.setAttend(isAttend);
		
		// DBに変更内容を保存
		studentDao.save(student);
		// JSPへフォワード 7
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}

}
