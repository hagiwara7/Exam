<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">クラス情報変更</c:param>

	<c:param name="content">

		<section class="me-4">

			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				クラス情報変更
			</h2>

			<form action="ClassUpdateExecute.action" method="post">

				<input type="hidden"
					name="old_class_num"
					value="${classnum.class_num}">

				<div class="mb-3">

					<label class="form-label">
						クラス番号
					</label>

					<input type="text"
						name="class_num"
						value="${classnum.class_num}"
						class="form-control"
						required>

				</div>

				<c:if test="${error != null}">
					<div class="text-warning mb-3">
						${error}
					</div>
				</c:if>

				<button class="btn btn-primary">
					変更
				</button>

			</form>

			<div class="mt-3">
				<a href="ClassList.action">戻る</a>
			</div>

		</section>

	</c:param>

</c:import>