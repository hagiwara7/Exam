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
			
			<!-- 「検索」ボタンを押したらTestListSubjectExecuteAction.javaに飛ぶように設定 -->
			<form action="TestListSubjectExecute.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded">
					<div class="col-3">
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

					<div class="col-3">
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

					<div class="col-2 text-center">
						<button class="btn btn-secondary">検索</button>
					</div>
				</div>
			</form>



			<!-- 学生番号で絞る -->
			<!-- 「検索」ボタンを押したらTestListStudentExecuteAction.javaに飛ぶように設定 -->
			<form action="TestListStudentExecute.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded">
					<div class="col-10">
						<label class="form-label">学生番号</label>
						<input class="form-control" type="text" name="f4" value="${f4}" />
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary">検索</button>
					</div>

					<div class="mt-2 text-warning">${errors.get("f4")}</div>
				</div>
			</form>

			<c:if test="${not empty student}">
				<div class="mx-3 mb-2">
					氏名：${student.name}（${student.no}）
				</div>
			</c:if>

			<c:if test="${not empty testListStudent}">
				<table class="table table-hover mx-3">
					<tr>
						<th>科目名</th>
						<th>科目コード</th>
						<th>回数</th>
						<th>点数</th>
					</tr>

					<c:forEach var="test" items="${testListStudent}">
						<tr>
							<td>${test.subjectName}</td>
							<td>${test.subjectCd}</td>
							<td>${test.num}</td>
							<td>${test.point}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>

			<c:if test="${searchType == 'student' && empty errors && empty testListStudent}">
				<div class="mx-3">成績情報が存在しませんでした。</div>
			</c:if>

		</section>
	</c:param>
</c:import>