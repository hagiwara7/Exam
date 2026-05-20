<!-- 学生番号が入力された場合 -->

<%-- 成績参照JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- 学生番号で得点を表示 -->
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>

			<div class="border mx-3 mb-3 py-2 px-3 rounded" style="max-width: 100%;">

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

					<c:if test="${not empty errors.f4}">
						<div class="row mt-2">
							<div class="col-10 offset-2 text-warning">
								${errors.f4}
							</div>
						</div>
					</c:if>
				</form>

			</div>

			<c:if test="${empty f4 && empty errors.f4 && empty message}">
				<div class="mx-3 text-info">
					科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
				</div>
			</c:if>

			<c:if test="${not empty student}">
				<div class="mx-3 mt-2">
					氏名：${student.name}（${student.no}）
				</div>
			</c:if>

			<c:if test="${not empty testListStudent}">
				<table class="table table-hover mx-3 mt-2">
					<thead>
						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th>回数</th>
							<th>点数</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="test" items="${testListStudent}">
							<tr>
								<td>${test.subjectName}</td>
								<td>${test.subjectCd}</td>
								<td>${test.num}</td>
								<td>${test.point}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${not empty message}">
				<div class="mx-3">
					${message}
				</div>
			</c:if>

		</section>
	</c:param>
</c:import>