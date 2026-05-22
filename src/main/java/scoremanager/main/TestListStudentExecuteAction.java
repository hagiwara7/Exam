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

		// セッションからログイン中の教員情報を取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// リクエストパラメータから学生番号を取得
		String studentNo = req.getParameter("f4");

		// 学生番号が入力されている場合、前後の空白を削除
		if (studentNo != null) {
			studentNo = studentNo.trim();
		}

		// 各Daoを初期化
		StudentDao studentDao = new StudentDao();
		TestListStudentDao testListStudentDao = new TestListStudentDao();
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		// エラーメッセージ用のMapを初期化
		Map<String, String> errors = new HashMap<>();

		// 入学年度のリストを作成
		LocalDate today = LocalDate.now();
		int year = today.getYear();

		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year; i++) {
			entYearSet.add(i);
		}

		// ログイン中の教員の学校に紐づくクラス番号一覧と科目一覧を取得
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		List<Subject> subjects = subjectDao.filter(teacher.getSchool());

		// 画面表示に必要な値をリクエストスコープにセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subjects", subjects);
		req.setAttribute("f4", studentNo);
		req.setAttribute("searchType", "student");

		// 学生番号が未入力の場合
		if (studentNo == null || studentNo.isEmpty()) {
			errors.put("f4", "このフィールドを入力してください。");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("test_list.jsp").forward(req, res);
			return;
		}

		// 学生番号を指定して学生情報を取得
		Student student = studentDao.get(studentNo);

		// 学生情報が存在しない、またはログイン中の教員と学校が異なる場合
		if (student == null || student.getSchool() == null
				|| !student.getSchool().getCd().equals(teacher.getSchool().getCd())) {

			req.setAttribute("student", null);
			req.setAttribute("testListStudent", new ArrayList<TestListStudent>());
			req.setAttribute("errors", errors);
			req.setAttribute("message", "成績情報が存在しませんでした");

			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
			return;
		}

		// 学生情報を指定して学生別成績一覧を取得
		List<TestListStudent> testListStudent = testListStudentDao.filter(student);

		// 学生情報と成績一覧をリクエストスコープにセット
		req.setAttribute("student", student);
		req.setAttribute("testListStudent", testListStudent);
		req.setAttribute("errors", errors);

		// 成績情報が存在しない場合
		if (testListStudent == null || testListStudent.isEmpty()) {
			req.setAttribute("message", "成績情報が存在しませんでした");
		}

		// 学生別成績参照画面へフォワード
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}