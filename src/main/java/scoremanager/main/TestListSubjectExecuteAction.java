package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		TestListSubjectDao testListSubjectDao = new TestListSubjectDao();

		String entYear = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");

		Map<String, String> errors = new HashMap<>();

		LocalDate today = LocalDate.now();
		int year = today.getYear();

		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year; i++) {
			entYearSet.add(i);
		}

		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		List<Subject> subjects = subjectDao.filter(teacher.getSchool());

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subjects", subjects);

		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		
		
		// エラー対応
		if (entYear == null || entYear.equals("0")) {
			errors.put("f1", "入学年度を選択してください。");
		}

		if (classNum == null || classNum.equals("0")) {
			errors.put("f2", "クラスを選択してください。");
		}

		if (subjectCd == null || subjectCd.equals("0")) {
			errors.put("f3", "科目を選択してください。");
		}

		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("test_list.jsp").forward(req, res);
			return;
		}

		Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

		List<TestListSubject> testListSubject =
				testListSubjectDao.filter(Integer.parseInt(entYear), classNum, subject, teacher.getSchool());

		req.setAttribute("subject", subject);
		req.setAttribute("test_list", testListSubject);

		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	}
}
