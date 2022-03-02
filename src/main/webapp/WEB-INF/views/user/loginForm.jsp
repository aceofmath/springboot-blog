<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
				
		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="/oauth2/authorization/google"><img height="38px" src="/image/google.png" /></a>
		<a href="/oauth2/authorization/facebook"><img height="38px" src="/image/facebook.png" /></a>
		<a href="/oauth2/authorization/naver"><img height="38px" src="/image/naver.png" /></a>
		<a href="/oauth2/authorization/kakao"><img height="38px" src="/image/kakao.png" /></a>
	</form>
	
</div>

<%@ include file="../layout/footer.jsp"%>


