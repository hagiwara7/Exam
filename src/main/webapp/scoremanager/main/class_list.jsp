<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title" value="クラス管理" />

    <c:param name="content">

        <h2 class="mb-4 p-3 bg-secondary bg-opacity-10">
            クラス管理
        </h2>

        <div class="text-end mb-3">
            <a href="ClassCreate.action">新規登録</a>
        </div>

        <table class="table">

            <tr>
                <th>クラス番号</th>
                <th></th>
            </tr>

            <c:forEach var="c" items="${classList}">

                <tr>

                    <td>${c}</td>

                    <td>
                        <a href="ClassUpdate.action?class_num=${c}">
                            変更
                        </a>
                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:param>

</c:import>