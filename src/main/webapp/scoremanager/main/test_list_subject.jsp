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
			<%-- 画面タイトルを表示 --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<%-- 検索条件入力エリア --%>
			<div class="border mx-3 mb-3 py-2 px-3 rounded" style="max-width: 100%;">

				<!-- 科目情報で絞る -->
				<form action="TestListSubjectExecute.action" method="get">
					<div class="row align-items-end mb-2">
						<%-- 科目情報検索の見出し --%>
						<div class="col-2 align-self-center">
							<span class="text-secondary">科目情報</span>
						</div>

						<%-- 入学年度を選択 --%>
						<div class="col-2">
							<label class="form-label mb-0">入学年度</label>
							<select class="form-select form-select-sm" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>

						<%-- クラスを選択 --%>
						<div class="col-2">
							<label class="form-label mb-0">クラス</label>
							<select class="form-select form-select-sm" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>
										${num}
									</option>
								</c:forEach>
							</select>
						</div>

						<%-- 科目を選択 --%>
						<div class="col-4">
							<label class="form-label mb-0">科目</label>
							<select class="form-select form-select-sm" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subjects}">
									<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
										${subject.name}
									</option>
								</c:forEach>
							</select>
						</div>

						<%-- 科目情報で検索 --%>
						<div class="col-2">
							<button class="btn btn-secondary btn-sm">検索</button>
						</div>
					</div>

					<%-- 科目情報検索のエラーがある場合に表示 --%>
					<c:if test="${not empty error}">
						<div class="row mb-2">
							<div class="col-10 offset-2 text-warning">
								${error}
							</div>
						</div>
					</c:if>
				</form>

				<%-- 科目情報検索と学生情報検索の区切り線 --%>
				<div class="border-top my-2"></div>

				<!-- 学生番号で絞る -->
				<form action="TestListStudentExecute.action" method="get">
					<div class="row align-items-end">
						<%-- 学生情報検索の見出し --%>
						<div class="col-2 align-self-center">
							<span class="text-secondary">学生情報</span>
						</div>

						<%-- 学生番号を入力 --%>
						<div class="col-6">
							<label class="form-label mb-0">学生番号</label>
							<input
								class="form-control form-control-sm"
								type="text"
								name="f4"
								value="${f4}"
								placeholder="学生番号を入力してください"
							/>
						</div>

						<%-- 学生情報で検索 --%>
						<div class="col-2">
							<button class="btn btn-secondary btn-sm">検索</button>
						</div>
					</div>

					<%-- 学生番号検索のエラーがある場合に表示 --%>
					<c:if test="${not empty errors['f4']}">
						<div class="row mt-2">
							<div class="col-10 offset-2 text-warning">
								${errors['f4']}
							</div>
						</div>
					</c:if>
				</form>

			</div>

			<%-- 検索条件が未入力の場合の案内メッセージ --%>
			<c:if test="${empty error && empty errors['f4'] && (empty f1 || f1 == 0) && (empty f2 || f2 == '0') && (empty f3 || f3 == '0') && empty f4}">
				<div class="mx-3 text-info">
					科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
				</div>
			</c:if>

			<%-- 科目別検索の結果がある場合、科目名を表示 --%>
			<c:if test="${empty error && not empty testListSubject && not empty subject}">
				<div class="mx-3 mt-2 mb-1">
					科目：${subject.name}
				</div>
			</c:if>

			<!-- 科目別で絞られた結果を表示 -->
			<c:if test="${not empty testListSubject}">
				<table class="table table-hover mx-3 mt-1">
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
						<%-- 成績一覧を1件ずつ表示 --%>
						<c:forEach var="test" items="${testListSubject}">
							<tr>
								<td>${test.entYear}</td>
								<td>${test.classNum}</td>
								<td>${test.studentNo}</td>
								<td>${test.studentName}</td>
								<%-- 1回目の点数がない場合は「-」を表示 --%>
								<td>${empty test.points['1'] ? '-' : test.points['1']}</td>
								<%-- 2回目の点数がない場合は「-」を表示 --%>
								<td>${empty test.points['2'] ? '-' : test.points['2']}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

			<%-- 条件を指定して検索したが、学生情報が存在しない場合に表示 --%>
			<c:if test="${empty testListSubject && empty error && not empty f1 && f1 != 0 && not empty f2 && f2 != '0' && not empty f3 && f3 != '0'}">
				<div class="mx-3 mt-3">
					学生情報がありませんでした
				</div>
			</c:if>

		</section>
	</c:param>
</c:import>