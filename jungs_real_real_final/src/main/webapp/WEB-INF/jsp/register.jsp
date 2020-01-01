<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<!-- 회원가입 메인 페이지 내용 -->
	<div class="container-fluid">
      <div class="row" style="height: 100%">
			<div class="col-lg-4 md-4" style="background-color: #D7F8D6;">
				<div class="row" style="height: 30%;"></div>
				<div class="row" style="height: 30%;">
					<div class="container" align=right style="margin-right:50px;">
						<h1>Register<br><br>Page</h1>
					</div>
				</div>
			</div>
			<div class="col-lg-4 md-4" align="center"
			style="background-color: white;">

			<div class="row" style="height: 10%;"></div>
			<div class="row" style="height: 80%;">
				<div class="ld-form box">
					<form action="registerCheck" class="form-group" method="POST">
						<label>E-mail address</label> <input type="text" name="email" class="form-control" placeholder="(ex) seo123"> <br> 
						<select name="address" class="form-control">
							<option value="">선 택</option>
							<option value="@skuniv.ac.kr">skuniv.ac.kr</option>
							<option value="@naver.com">naver.com</option>
							<option value="@gmail.com">gmail.co.kr</option>
							<option value="@daum.net">daum.net</option>
							<option value="@nate.com">nate.com</option>
						</select> <br>
						<label>Student ID</label>
						<input type="number" name="studentId" class="form-control" placeholder="(ex) 201*******">
						
						<label>Name</label>
						<input type="text" name="name" class="form-control" placeholder="(ex) 정스프링">
						
						<label>Password</label>
						<input type="password" name="password" class="form-control"> 
						
						<label>Password check</label>
						<input type="password" name="passwordConfirm" class="form-control">
						<br> 
						<div align="center">
						<input type="button" onClick="location.href='login'" value="Login" class="btn btn-light" style="background-color:#A9E5A8; width:100px; margin-right:40px;">
						<input type="submit" value="Register" class="btn btn-light" style="background-color:#A9E5A8; width:100px;">
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-lg-4 md-4" style="background-color: white;">
			<div class="row" style="height: 40%;"></div>
			<div class="row" style="height: 20%;">
				<div class="container" align=left>
					<font color=#354A35><h5>${message}</h5></font>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>