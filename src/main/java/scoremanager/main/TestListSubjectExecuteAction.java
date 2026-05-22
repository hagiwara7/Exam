package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

		// セッション情報を取得
		HttpSession session = req.getSession();
		// セッションからログイン中の教員情報を取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 各Daoを初期化
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		TestListSubjectDao testListSubjectDao = new TestListSubjectDao();

		// リクエストパラメータを取得
		String entYear = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");

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
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);

		// 入学年度・クラス・科目のいずれかが未選択の場合
		if (entYear == null || entYear.equals("0")
				|| classNum == null || classNum.equals("0")
				|| subjectCd == null || subjectCd.equals("0")) {

			req.setAttribute("error", "入学年度とクラスと科目を選択してください");
			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
			return;
		}

		// 選択された科目コードから科目情報を取得
		Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

		// 入学年度・クラス・科目・学校を指定して科目別成績一覧を取得
		List<TestListSubject> testListSubject =
				testListSubjectDao.filter(Integer.parseInt(entYear), classNum, subject, teacher.getSchool());

		// 科目情報と科目別成績一覧をリクエストスコープにセット
		req.setAttribute("subject", subject);
		req.setAttribute("testListSubject", testListSubject);

		// 成績参照画面へフォワード
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	}
}