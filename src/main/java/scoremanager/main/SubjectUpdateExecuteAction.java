package scoremanager.main;
import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		Map<String, String> errors = new HashMap<>();
		
		String cd=""; // 科目コード
		String name=""; // 科目名
		
		SubjectDao subjectDao=new SubjectDao(); // 科目の情報をDBから取得するのに必要
		
		//リクエストパラメータから取得
		cd=req.getParameter("cd");
		name=req.getParameter("name");
		
        School school = teacher.getSchool();
        
        //科目変更中に変更対象が削除された際の処理
        if (subjectDao.get(cd, school) == null) {
        	errors.put("1", "科目情報が存在しません");//エラー文をセット
     
    	    req.setAttribute("errors", errors);
    		req.setAttribute("cd", cd);
    		req.setAttribute("name", name);
    		req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);//前のサーブレットに戻る
        }
        
      //subjectに変更する科目の情報をセット
		 Subject subject=new Subject();
		 subject.setCd(cd);
		 subject.setName(name);
		 subject.setSchool(teacher.getSchool());
		
	  // DBに変更内容を保存
		 subjectDao.save(subject);
		 
	  // jspへフォワード
		 req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	}
}
