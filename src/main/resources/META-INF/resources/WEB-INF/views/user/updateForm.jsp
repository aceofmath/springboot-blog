<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id}" />
		<input type="hidden" id="h_provider" value="${principal.user.provider}" />
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
		
		<div class="form-group">
			<label for="postcode">우편번호</label>
			<div class="input-group mb-2">
				<input type="text" value="${principal.user.postcode}" class="form-control" placeholder="우편번호" id="postcode" readonly>
				<button type="button" class="btn btn-primary" id="btn-postcode" class="btn btn-primary">우편번호찾기</button>
			</div>
		</div>
		
		<div class="form-group">
			<label for="address">주소</label> 
			<input type="text" value="${principal.user.address}" class="form-control" placeholder="주소" id="address" readonly>
		</div>
		
		<div class="form-group">
			<label for="detailAddress">상세주소</label> 
			<input type="text" value="${principal.user.detailAddress}" class="form-control" placeholder="상세주소" id="detailAddress">
		</div>
	</form>
	
	<button id="btn-update" class="btn btn-primary">회원수정완료</button>

</div>

<script src="/js/user.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<%@ include file="../layout/footer.jsp"%>


