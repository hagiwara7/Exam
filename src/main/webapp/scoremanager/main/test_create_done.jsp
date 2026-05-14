<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">

	<section class="me-4">

		<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
			成績登録完了
		</h2>

		<div class="alert alert-success mx-3">

			${message}

		</div>

		<div class="mx-3">

			<a href="TestList.action" class="btn btn-primary">
				成績一覧へ
			</a>

		</div>

	</section>

	</c:param>

</c:import>