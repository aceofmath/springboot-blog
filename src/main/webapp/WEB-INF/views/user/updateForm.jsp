<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" value="${principal.user.username }" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>
		
		<c:if test="${empty principal.user.provider}">
			<div class="form-group">
				<label for="password">Password</label> 
				<input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>
		
		<div class="form-group">
			<label for="email">Email</label> 
			<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
		</div>
	</form>
	
	<c:if test="${empty principal.user.provider}">
	<button id="btn-update" class="btn btn-primary">회원수정완료</button>
	</c:if>
	<c:if test="${not empty principal.user.provider}">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	</c:if>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


