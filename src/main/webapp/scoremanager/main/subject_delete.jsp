<%-- 科目削除JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
			<form action="SubjectDeleteExecute.action" method="post">

			<p>「${subject.name }(${subject.cd })」を削除してもよろしいですか</p>
			
			<input type="hidden" name="subject_cd" value="${subject.cd }">
			<input type="hidden" name="subject_name" value="${subject.name }">
			
			    <div class="mx-auto py-2">
					<input class="btn btn-danger" type="submit" name="login" value="削除"/>
				</div>
				
			</form>
			<br>
			<br>
			<br>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>