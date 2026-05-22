	package scoremanager.main;
	
	import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
	
	public class ClassCreateExecuteAction extends Action {
	
		@Override
		public void execute(
				HttpServletRequest request,
				HttpServletResponse response
				) throws Exception {
	
			// 文字コード
			request.setCharacterEncoding("UTF-8");
	
			// 入力値取得
			String classNum = request.getParameter("class_num");
	
			// 未入力チェック
			if (classNum == null || classNum.isEmpty()) {
	
				request.setAttribute("error", "クラス番号を入力してください");
	
				request.getRequestDispatcher("class_create.jsp")
					.forward(request, response);
	
				return;
			}
	
			// ログイン情報取得
			HttpSession session = request.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");
	
			// DAO
			ClassNumDao dao = new ClassNumDao();
	
			// 重複チェック
			ClassNum old = dao.get(classNum, teacher.getSchool());
	
			if (old != null) {
	
				request.setAttribute("error", "既に登録されています");
	
				request.getRequestDispatcher("class_create.jsp")
					.forward(request, response);
	
				return;
			}
	
			// Beanへセット
			ClassNum classnum = new ClassNum();
	
			classnum.setClass_num(classNum);
			classnum.setSchool(teacher.getSchool());
	
			boolean result = dao.save1(classnum);
	
			if (result) {
	
				request.setAttribute("message", "登録が完了しました");
	
				request.getRequestDispatcher(
						"class_create_done.jsp")
					.forward(request, response);
	
				return;
	
			} else {
	
				request.setAttribute("error", "登録に失敗しました");
	
				request.getRequestDispatcher(
						"class_create.jsp")
					.forward(request, response);
	
				return;
			}
		}
	}