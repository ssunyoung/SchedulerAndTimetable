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
<!-- 이메일 찾기 메인 페이지 내용 -->
   <div class="container-fluid">
      <div class="row" style="height: 100%">
         <div class="col-lg-4 md-4" align="center" style="background-color: #B8E3F4;">
         <div class="row" style="height: 30%;"></div>
            <div class="row" style="margin-top:auto; margin-bottom:auto;">
         		<div class="container" align=right style="margin-right:50px;"><h1>Find<br><br>Email</h1></div>
         		</div>
         </div>

         <div class="col-lg-4 md-4" align="center" style="background-color: white;">
            <div class="row" style="height: 20%;">
            </div>
            <div class="row" style="height: 60%;">
					<div class="lg-form box">
					<h5> Information Check </h5> <br>
					<form action="findEmail" method="POST">
							<label>Student ID</label>
							<input type="number" name="studentId" class="form-control" placeholder="(ex) 201xxxxxxx"><br>
							<label>Name</label>
							<input type="text" name="name" class="form-control" placeholder="(ex) 정스프링"> <br>
							<div align="center">
							
							<input type="button" onClick="location.href='login'" value="Login" class="btn btn-light" style="background-color: #88BCF4; width: 100px; margin-right:40px;">
							<input type="submit" name="submit" value="Find" class="btn btn-light" style="background-color: #88BCF4; width: 100px;">
						</div>
					</form>
					</div>
				</div>
				
            </div>
			<div class="col-lg-4 md-4" style="background-color: white;">
				<div class="row" style="height: 40%;"></div>
				<div class="row" style="height: 20%;">
					<div class="container" align=left>
						<font color=#304A66><h5>${message}</h5></font>
					</div>
				</div>
			</div>
		</div>
      </div>
</body>
</html>