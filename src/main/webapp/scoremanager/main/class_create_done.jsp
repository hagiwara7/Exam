<%-- score_create_done.jsp --%>
<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点登録完了
	</c:param>

	<c:param name="content">

		<section class="me-4">

			<h2 class="mb-4 bg-light p-2">
				クラス情報登録
			</h2>

			<div class="alert alert-success text-center">

				<c:choose>

					<c:when test="${message != null}">
						${message}
					</c:when>

					<c:otherwise>
						登録が完了しました
					</c:otherwise>

				</c:choose>

			</div>

			<div class="mt-4">

				<a href="ClassCreate.action"
					class="btn btn-primary">

					戻る

				</a>
				<a href="ClassList.action"
					class="btn btn-primary">
					
					クラス一覧
				</a>

			</div>

		</section>

	</c:param>

</c:import>
