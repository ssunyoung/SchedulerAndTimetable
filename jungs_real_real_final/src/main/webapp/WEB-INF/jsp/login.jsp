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
<!-- 로그인 메인 페이지 내용 -->
   <div class="container-fluid">
      <div class="row" style="height: 100%">
         <div class="col-lg-4 md-4" style="background-color: #ECDCEB;">
         	 <div class="row" style="height: 30%;"></div>
            <div class="row" style="height: 30%;">
         		<div class="container" align=right style="margin-right:50px;"><h1>JUNGs page<br><br>Login</h1></div>
         		</div>
		</div>
         <div class="col-lg-4 md-4" align="center" style="background-color: white;">
            <div class="row" style="height: 25%;"></div>
            <div class="row" style="height: 60%;">
                  <div class="lg-form box">
                     <form action="loginConfirm" method="POST">
                        <label>E-mail address</label>
                        <input type="text" name="email" class="form-control" placeholder="(ex) seo123">
                        <br>
                        <select name="address" class="form-control">
                        	<option value="">선 택</option>
                        	<option value="@skuniv.ac.kr">skuniv.ac.kr</option>
                        	<option value="@naver.com">naver.com</option>
                        	<option value="@gmail.com">gmail.co.kr</option>
                        	<option value="@daum.net">daum.net</option>
                        	<option value="@nate.com">nate.com</option>
                        </select>
                        <br>
                        <label>Password</label>
                        <input type="password" name="password" class="form-control"><br>
                        <div align="center">
                        <input type="submit" name="submit" value="Login" class="btn btn-light" style="background-color:#EBA5E8; width:250px; margin-right:40px;">
                        </div>
                        
                        <br>
                         <div align="center"  >
                         <a href="register" style="color:red;">Member Register</a>
                         	<br><br><a href="findEmail" style="margin-right:60px;">Find Email</a><a href="changePassword">Change Password</a>
                         </div>
                     </form>
                  </div>
            </div>
         </div>
          <div class="col-lg-4 md-4" style="background-color: white;">
				<div class="row" style="height: 40%;"></div>
				<div class="row" style="height: 20%;">
					<div class="container" align=left> 
					<font color=#3F203E><h5>${message}</h5></font></div>
				</div>
			</div>
      </div>
   </div>
 

</body>
</html>