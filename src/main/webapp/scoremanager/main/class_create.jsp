<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title" value="クラス情報登録" />

    <c:param name="content">

        <div class="mx-3">

            <!-- タイトル -->
            <h2 class="p-3 mb-4 bg-secondary bg-opacity-10">
                クラス情報登録
            </h2>

            <!-- 登録フォーム -->
            <form action="ClassCreateExecute.action"
                  method="post">

                <div class="mb-4">

                    <label class="form-label fs-5">
                        クラス番号
                    </label>

                    <input type="text"
                           name="class_num"
                           class="form-control"
                           required>

                </div>

                <!-- エラー -->
                <c:if test="${error != null}">

                    <div class="text-warning mb-3 fs-5">
                        ${error}
                    </div>

                </c:if>

                <!-- ボタン -->
                <div class="mb-3">

					<button class="btn btn-primary">

                        登録

                    </button>

                </div>

            </form>

            <!-- 戻る -->
            <div>

                <a href="ClassList.action"
                   class="mt-3">

                    戻る

                </a>

            </div>

        </div>

    </c:param>

</c:import>