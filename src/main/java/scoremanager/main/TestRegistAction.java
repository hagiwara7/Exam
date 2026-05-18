package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {

		StudentDao sDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		ClassNumDao cDao = new ClassNumDao();

		School school = new School();

		school.setCd("2000");

		request.getSession().setAttribute(
		    "school",
		    school
		);
		// DBからプルダウン用データ取得
		request.setAttribute(
				"subjectSet",
				subDao.filter(school)
		);

		request.setAttribute(
				"classNumSet",
				cDao.filter(school)
		);

		request.setAttribute(
				"entYearSet",
				sDao.filterEntYear(school)
		);

		// パラメータ取得
		String entYear = request.getParameter("entYear");
		String classNum = request.getParameter("classNum");
		String subjectCd = request.getParameter("subjectCd");
		String no = request.getParameter("no");

		request.setAttribute("entYear", entYear);
		request.setAttribute("classNum", classNum);
		request.setAttribute("subjectCd", subjectCd);
		request.setAttribute("no", no);

		// 検索実行
		if (entYear != null &&
			!entYear.isEmpty() &&
			classNum != null &&
			!classNum.isEmpty() &&
			subjectCd != null &&
			!subjectCd.isEmpty() &&
			no != null &&
			!no.isEmpty()) {

			List<Student> students =
					sDao.filter(
							school,
							Integer.parseInt(entYear),
							classNum,
							true
					);

			request.setAttribute(
					"students",
					students
			);
			Map<String, Integer> points =
			        new HashMap<>();

			TestDao tDao = new TestDao();

			for (Student student : students) {

			    Test test = tDao.get(
			            student.getNo(),
			            subjectCd,
			            no,
			            school
			    );

			    if (test != null) {

			        points.put(
			                student.getNo(),
			                test.getPoint()
			        );
			    }
			}

			request.setAttribute(
			        "points",
			        points
			);

			// 科目名取得
			List<Subject> subs =
					subDao.filter(school);

			for (Subject s : subs) {

				if (s.getCd().equals(subjectCd)) {

					request.setAttribute(
							"subjectName",
							s.getName()
					);
				}
			}
		}

		request.getRequestDispatcher(
				"TestRegist.jsp"
		).forward(request, response);
	}
}
