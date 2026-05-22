package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        LocalDate today = LocalDate.now();
        int year = today.getYear(); //今年の年数を取得

        //今年から10年前までの年数を取得
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        //検索項目のデータを取得(クラス番号,科目)
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        List<String> classNumSet = classNumDao.filter(teacher.getSchool());
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        Map<String, String> errors = new HashMap<>();

        //取得した値をセット
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subjects", subjects);
        req.setAttribute("errors", errors);
        
        //JSPへフォワード
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
