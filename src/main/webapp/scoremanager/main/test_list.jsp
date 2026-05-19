<%-- 成績参照JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- 科目別で得点を表示 -->
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<div class="border mx-3 mb-3 py-2 px-3 rounded" style="max-width: 100%;">

				<!-- 科目情報で絞る -->
				<form action="TestListSubjectExecute.action" method="get">
					<div class="row align-items-center mb-2">
						<div class="col-2 d-flex align-items-center">
							<span class="text-secondary">科目情報</span>
						</div>

						<div class="col-2">
							<label class="form-label">入学年度</label>
							<select class="form-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-2">
							<label class="form-label">クラス</label>
							<select class="form-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>
										${num}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-4">
							<label class="form-label">科目</label>
							<select class="form-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subjects}">
									<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
										${subject.name}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-2">
							<button class="btn btn-secondary mt-4">検索</button>
						</div>
					</div>
				</form>

				<div class="border-top my-2"></div>

				<!-- 学生番号で絞る -->
				<form action="TestListStudentExecute.action" method="get">
					<div class="row align-items-center">
						<div class="col-2 d-flex align-items-center">
							<span class="text-secondary">学生情報</span>
						</div>

						<div class="col-6">
							<label class="form-label">学生番号</label>
							<input
								class="form-control"
								type="text"
								name="f4"
								value="${f4}"
								placeholder="学生番号を入力してください"
								required
							/>
						</div>

						<div class="col-2">
							<button class="btn btn-secondary mt-4">検索</button>
						</div>
					</div>
				</form>

			</div>

			<div class="mx-3 text-info">
				科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
			</div>

			<div class="mx-3 mt-2 text-warning">
				${errors.get("f4")}
			</div>

		</section>
	</c:param>
</c:import>