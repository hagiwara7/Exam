<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>
			
			      <c:if test="${not empty subject }">
			          <div class="mx-3 mb-2">
					       科目：${subject.name}
				      </div>
			      </c:if>
			      
			      <c:if test="${not empty testListSubject }">
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>１回</th>
							<th>２回</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="test" items="${testListSubject }">
							<tr>
								<td>${test.entYear }</td>
								<td>${test.classNum }</td>
								<td>${test.studentno }</td>
								<td>${test.studentname }</td>
								<td>${empty test.point ? '-' : test.point}</td>
								<td>${empty test.point ? '-' : test.point}</td>
								
							</tr>
						</c:forEach>
					</table>
				 </c:if>
				 
					<c:if test="${empty testListSubject }">
					    <div>学生情報が存在しませんでした</div>
				    </c:if>
			</section>
		</c:param>
	</c:import>