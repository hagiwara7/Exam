package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

		HttpSession session = request.getSession();

		Teacher teacher =
		        (Teacher) session.getAttribute("user");

		School school1 = teacher.getSchool();

		request.getSession().setAttribute(
		    "school",
		    school1
		);

		// DBからプルダウン用データ取得
		request.setAttribute(
				"subjectSet",
				subDao.filter(school1)
		);

		request.setAttribute(
				"classNumSet",
				cDao.filter(school1)
		);

		request.setAttribute(
				"entYearSet",
				sDao.filterEntYear(school1)
		);

		// パラメータ取得

		String entYear =
		        request.getParameter("entYear");

		String classNum =
		        request.getParameter("classNum");

		String subjectCd =
		        request.getParameter("subjectCd");

		String no =
		        request.getParameter("no");

		// 選択値保持
		
		request.setAttribute("entYear", entYear);
		request.setAttribute("classNum", classNum);
		request.setAttribute("subjectCd", subjectCd);
		request.setAttribute("no", no);


		// 検索ボタン押下時のみエラー表示

		if (
		    entYear != null ||
		    classNum != null ||
		    subjectCd != null ||
		    no != null
		) {

		    if (
		        entYear == null || entYear.isEmpty() ||
		        classNum == null || classNum.isEmpty() ||
		        subjectCd == null || subjectCd.isEmpty() ||
		        no == null || no.isEmpty()
		    ) {

		        request.setAttribute(
		                "selectError",
		                "入学年度とクラスと科目と回数を選択してください"
		        );

		        request.getRequestDispatcher(
		                "TestRegist.jsp"
		        ).forward(request, response);

		        return;
		    }
		}


		// 検索実行

		if (
			entYear != null &&
			!entYear.isEmpty() &&
			classNum != null &&
			!classNum.isEmpty() &&
			subjectCd != null &&
			!subjectCd.isEmpty() &&
			no != null &&
			!no.isEmpty()
		) {

			List<Student> students =
					sDao.filter(
							school1,
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
			            school1
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

			Subject subject =
			        subDao.get(subjectCd, school1);

			request.setAttribute(
			        "subject",
			        subject
			);
		}

		request.getRequestDispatcher(
				"TestRegist.jsp"
		).forward(request, response);
	}
}
