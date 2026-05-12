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

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションから現在のユーザーを取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // エラーメッセージを格納するマップを作成
        Map<String, String> errors = new HashMap<>();

        // リクエストパラメータの取得
        String cd = req.getParameter("cd").trim();       // 科目コード
        String name = req.getParameter("name").trim();   // 科目名

        // ログイン中の教師が所属する学校を取得
        School school = teacher.getSchool();

        // --- 科目コードのバリデーション ---
        if (cd == null || cd.isEmpty()) {
            errors.put("1", "科目コードを入力してください");
        } else if (!cd.matches("[A-Za-z0-9]+")) { // 英数字チェック
            errors.put("1", "科目コードは英数字のみ使用できます");
        } else if (cd.length() != 3) {
            errors.put("1", "科目コードは3文字で入力してください");
        }

        // --- 科目名のバリデーション ---
        if (name == null || name.isEmpty()) {
            errors.put("2", "科目名を入力してください");
        }

        // --- 科目コードの重複チェック ---
        SubjectDao dao = new SubjectDao();
        if (dao.get(cd, school) != null) {
            errors.put("1", "科目コードは重複しています");
        }

        // エラーがなければ、科目を作成して保存
        if (errors.isEmpty()) {
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            subject.setSchool(school);

            // データベースに保存
            boolean success = dao.save(subject);

            if (success) {
                // 成功したら完了ページへフォワード
                req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
                return;
            } else {
                // データベース保存に失敗した場合のエラー
                errors.put("system", "データベースへの保存に失敗しました");
            }
        }

        // エラーがある場合、入力値とエラーをリクエストにセットして戻る
        req.setAttribute("cd", cd);
        req.setAttribute("name", name);
        req.setAttribute("errors", errors);
        req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
    }
}