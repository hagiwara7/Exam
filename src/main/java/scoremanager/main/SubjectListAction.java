package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//
		String cd = "";
		String school_cd = "";
		String name = "";
		List<Subject> subjects = null; 
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		SubjectDao subjectDao = new SubjectDao(); // 学生Dao
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		//
		cd = req.getParameter("f1");
		school_cd = req.getParameter("f2");
		name = req.getParameter("f3");
		
		//
		List<Subject> list = subjectDao.filter(teacher.getSchool());

		
		// セット
		req.setAttribute("subjects", list);
		
		req.setAttribute("cd", cd);
		req.setAttribute("school_cd", school_cd);
		req.setAttribute("name", name);
		
		// フォワード
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
		}
	}