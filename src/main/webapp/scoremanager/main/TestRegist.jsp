<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

	<section class="me-4">

		<!-- タイトル -->
		<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
			成績登録
		</h2>

		<!-- フォーム -->
		<form action="${pageContext.request.contextPath}/scoremanager/main/TestCreateExecute.action"
		      method="post">
		
		    <div class="mb-3">
		        <label class="form-label">学生番号</label>
		
		        <input type="text"
		               name="student_no"
		               class="form-control"
		               required>
		    </div>
		
		    <div class="mb-3">
		        <label class="form-label">科目コード</label>
		
		        <input type="text"
		               name="subject_cd"
		               class="form-control"
		               required>
		    </div>
		
		    <div class="mb-3">
		        <label class="form-label">回数</label>
		
		        <select name="no" class="form-select">
		            <option value="1">1回</option>
		            <option value="2">2回</option>
		            <option value="3">3回</option>
		        </select>
		    </div>
		
		    <div class="mb-3">
		        <label class="form-label">点数</label>
		
		        <input type="number"
		               name="point"
		               class="form-control"
		               required>
		    </div>
			<div class="mb-3">
			    <label>クラス番号</label>
			    <input type="text" name="class_num" class="form-control">
			</div>
		
		    <button class="btn btn-primary">
		        登録
		    </button>
		
		</form>

	</section>

	</c:param>

</c:import>