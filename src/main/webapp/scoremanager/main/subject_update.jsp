<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form action="SubjectUpdateExecute.action" method="get">
				<div class="mx-auto py-2">
					<label class="mx-auto py-2" for="cd">科目コード</label><br>
					<input class="border border-0 ps-3" type="text" id="cd" value="${cd }" name="cd" readonly />
				</div>
				<div class="mx-auto py-2">
					<label for="name">教科名</label><br>
					<input class="form-control" type="text" id="name" name="name" value="${name }" required maxlength="30" />
				</div>
				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" name="login" value="変更"/>
				</div>
			</form>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>