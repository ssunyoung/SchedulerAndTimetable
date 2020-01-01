<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
	<script>
		function modal_data() {
			$("#passwordChangeModal").on("click");
		}

	</script>
</head>
<body>
<!-- 비밀번호 변경 메인 페이지 내용 -->
   <div class="container-fluid">
      <div class="row" style="height: 100%">
         <div class="col-lg-4 md-4" align="center" style="background-color: #FBD8C8;">
         <div class="row" style="height: 30%;"></div>
            <div class="row" style="height: 30%;">
         		<div class="container" align=right style="margin-right:50px;"><h1>Change<br><br>Password</h1></div>
         		</div>
         		 </div>

         <div class="col-lg-4 md-4" align="center" style="background-color: white;">
            <div class="row" style="height: 20%;">
            </div>
            <div class="row" style="height: 60%;">
					<div class="lg-form box">
					<h5> Information Check </h5> <br>
					<form action="changePassword" method="POST">
					<label>E-mail address</label> <input type="text" name="email" class="form-control" placeholder="(ex) seo123"> <br>
					 <select name="address" class="form-control">
							<option value="">선 택</option>
							<option value="@skuniv.ac.kr">skuniv.ac.kr</option>
							<option value="@naver.com">naver.com</option>
							<option value="@gmail.com">gmail.co.kr</option>
							<option value="@daum.net">daum.net</option>
							<option value="@nate.com">nate.com</option>
						</select> <br>
							<label>Name</label>
							<input type="text" name="name" class="form-control" placeholder="(ex) 정스프링"> <br>
							<div align="center">
							
							<input type="button" onClick="location.href='login'" value="Login" class="btn btn-light" style="background-color: #FAAC58; width: 100px; margin-right:40px;">
							<input type="submit" name="submit" value="Check" class="btn btn-light" style="background-color: #FAAC58; width: 100px;">
						</div>
					</form>
					</div>
				</div>
				
            </div>
			<div class="col-lg-4 md-4" style="background-color: white;">
				<div class="row" style="height: 40%;"></div>
				<div class="row" style="height: 20%;">
					<div class="container" align=left>
						<font color=#61380B><h5>${message}</h5></font>
						<br>
						<c:choose>
							<c:when test="${isButton}">
								<button type="button" onClick="modal_data()" class="btn" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#passwordChangeModal" style="background-color:#FAAC58">Change Password</button>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${isMessage}">
								<h5>${modalMessage}</h5><br>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${isLog}">
								<a href="login">Login Page</a>
							</c:when>
	    				</c:choose>
					</div>
				</div>
				<div class="row" style="height: 40%;"></div>
			</div>
		</div>
      </div>
      
<!-- 비밀번호 변경 버튼 팝업 -->
    <div class="modal fade" id="passwordChangeModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">Change Password Form</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
          <form action="changePasswordPost" method="POST" class="form-group">
        <div class="modal-body">
          	<label>New Password</label><br>
 				<input type="password" name="password" id="password" class="form-control" placeholder="Input New Password">
          	<label>New Password Check</label><br>
          		<input type="password" name="passwordConfirm" id="passwordConfirm" class="form-control"  placeholder="Password Check">
          		<input type="hidden" name="Memail" id="Memail" class="form-control" value="${fullEmail}">
          		<br><br>
        </div>   
        <div class="modal-footer">
			<input type="submit" name="change" value="Change" class="btn btn-light">
		</div>
        </form>
      </div>
    </div>
  </div>


</body>
</html>