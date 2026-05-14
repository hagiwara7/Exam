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
<<<<<<< HEAD
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
=======
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>
>>>>>>> branch 'master' of https://github.com/hagiwara7/Exam.git
			
<<<<<<< HEAD
			<!-- 科目別検索 -->
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
						<div class="text-warning">${errors.f1}</div>
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
						<div class="text-warning">${errors.f2}</div>
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
						<div class="text-warning">${errors.f3}</div>
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary">検索</button>
					</div>
				</div>
			</form>

			<!-- 学生番号検索 -->
			<form action="TestListStudentExecute.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded">
					<div class="col-10">
						<label class="form-label">学生番号</label>
						<input class="form-control" type="text" name="f4" value="${f4}" />
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary">検索</button>
					</div>

					<div class="mt-2 text-warning">${errors.f4}</div>
				</div>
			</form>

			<!-- 科目別で絞られた結果を表示 -->
			<c:if test="${not empty subject}">
				<div class="mx-3 mb-2">
					科目：${subject.name}
				</div>
			</c:if>

			<c:if test="${not empty test_list}">
				<table class="table table-hover mx-3">
					<thead>
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>
						</tr>
					</thead>
						<tbody>
							<c:forEach var="test" items="${test_list}">
								<tr>
									<td>${test.entYear}</td>
									<td>${test.classNum}</td>
									<td>${test.studentNo}</td>
									<td>${test.studentName}</td>
						
									<td>
										<c:choose>
											<c:when test="${test.no == 1}">
												${test.point}
											</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</td>
						
									<td>
										<c:choose>
											<c:when test="${test.no == 2}">
												${test.point}
											</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</thead>
				</table>
			</c:if>
		</section>
	</c:param>
</c:import>
