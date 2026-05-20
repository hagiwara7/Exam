package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String studentNo = req.getParameter("f4");

		if (studentNo != null) {
			studentNo = studentNo.trim();
		}

		StudentDao studentDao = new StudentDao();
		TestListStudentDao testListStudentDao = new TestListStudentDao();
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

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
		req.setAttribute("f4", studentNo);
		req.setAttribute("searchType", "student");

		if (studentNo == null || studentNo.isEmpty()) {
			errors.put("f4", "このフィールドを入力してください。");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("test_list.jsp").forward(req, res);
			return;
		}

		Student student = studentDao.get(studentNo);

		if (student == null || student.getSchool() == null
				|| !student.getSchool().getCd().equals(teacher.getSchool().getCd())) {

			req.setAttribute("student", null);
			req.setAttribute("testListStudent", new ArrayList<TestListStudent>());
			req.setAttribute("errors", errors);
			req.setAttribute("message", "成績情報が存在しませんでした");

			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
			return;
		}

		List<TestListStudent> testListStudent = testListStudentDao.filter(student);

		req.setAttribute("student", student);
		req.setAttribute("testListStudent", testListStudent);
		req.setAttribute("errors", errors);

		if (testListStudent == null || testListStudent.isEmpty()) {
			req.setAttribute("message", "成績情報が存在しませんでした");
		}

		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}