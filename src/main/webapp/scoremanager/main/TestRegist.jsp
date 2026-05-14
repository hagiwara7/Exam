<%-- TestRegist.jsp 成績登録画面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">

	<section class="me-4">

		<h2 class="mb-4 bg-light p-2">成績管理</h2>

		<form action="TestRegist.action" method="get">

			<div class="border rounded p-4 mb-4">

				<div class="row align-items-end">
			
					<div class="col-3">
						<label>入学年度</label>
						<select name="entYear" class="form-select">
							<c:forEach var="year" items="${entYearSet}">
								<option value="${year}">
									${year}
								</option>
							</c:forEach>
						</select>
					</div>
			
					<div class="col-2">
						<label>クラス</label>
						<select name="classNum" class="form-select">
							<c:forEach var="num" items="${classNumSet}">
								<option value="${num}">
									${num}
								</option>
							</c:forEach>
						</select>
					</div>
			
					<div class="col-3">
						<label>科目</label>
						<select name="subjectCd" class="form-select">
							<c:forEach var="sub" items="${subjectSet}">
								<option value="${sub.cd}">
									${sub.name}
								</option>
							</c:forEach>
						</select>
					</div>
			
					<div class="col-2">
						<label>回数</label>
						<select name="no" class="form-select">
							<option value="1">1</option>
							<option value="2">2</option>
						</select>
					</div>
			
					<div class="col-1">
					<button type="submit"
						class="btn btn-secondary px-4 text-nowrap">
						検索
					</button>
					</div>
			
				</div>
			
			</div>

		</form>


		<c:if test="${students != null}">

						<form action="TestRegistExecute.action" method="post">
			
			    <input type="hidden" name="subjectCd" value="${subjectCd}">
			    <input type="hidden" name="no" value="${no}">
			    <input type="hidden" name="classNum" value="${param.classNum}">
			    <input type="hidden" name="entYear" value="${param.entYear}">
			    <input type="hidden" name="schoolCd" value="${schoolCd}">
			
			    <table class="table">
			
			        <tr>
			            <th>入学年度</th>
			            <th>クラス</th>
			            <th>学生番号</th>
			            <th>氏名</th>
			            <th>点数</th>
			        </tr>
			
			        <c:forEach var="student" items="${students}" varStatus="st">
			
			            <tr>
			                <td>${student.entYear}</td>
			                <td>${student.classNum}</td>
			
			                <td>
			                    ${student.no}
			                    <input type="hidden" name="student_no" value="${student.no}">
			                </td>
			
			                <td>${student.name}</td>
			
			                <td>
			                    <input type="text"
			                           name="point"
			                           class="form-control"
			                           value="${points[st.index]}">
			                </td>
			            </tr>
			
			        </c:forEach>
			
			    </table>
			
			    <button type="submit" class="btn btn-primary">
			        登録して終了
			    </button>
			
			</form>

		</c:if>

	</section>

	</c:param>

</c:import>