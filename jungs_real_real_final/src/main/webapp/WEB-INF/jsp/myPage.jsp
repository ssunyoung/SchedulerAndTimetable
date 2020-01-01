<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<script>
//회원정보 삭제 확인
   function modal_delete() {
      var email = document.getElementById("email").value;
      var password = document.getElementById("password").value;
      var passwordConfirm = document.getElementById("passwordConfirm").value;

      console.log(email + ", "+ password+"!!");
      
      $(".modal-body #m_email").val(email);
      $(".modal-body #m_password").val(password);
      $(".modal-body #m_passwordConfirm").val(passwordConfirm);

      $("#deleteModal").on("click");
   }
</script>
</head>
<body>
<header>
		<!-- MenuBar(NavBar) 출력 -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<h2><span style="margin:10px 40px 5px 40px;">JUNGs</span></h2>
		<div class="collapse navbar-collapse" id="navbarNavDropdown">
			<ul class="navbar-nav">
				<li class="nav-item" style="margin:10px 30px 5px 50px;"><a class="nav-link" href="timetable">My_TimeTable</a>
				</li>
				<li class="nav-item" style="margin:10px 40px 5px 30px;"><a class="nav-link" href="planner?auto=0">My_Planner</a>
				</li>
			</ul>
		</div>
		<span style="margin:10px 2rem 5px 40px;">
			<input type="button" onClick="location.href='myPage'" class="btn btn-light" value="My Page" style="width: 100px;">
			<input type="button" onClick="location.href='logout'" class="btn btn-light" value="Logout" style="width: 100px;">
		</span>
	</nav>
	<div class="container" align="center">
	   <br>
	   <h3>< Member Information ></h3>
	   <hr>
	   <br>
	   <font color="red"><h5>${message}</h5></font>
	</div>
</header>
<body>
<!-- 마이페이지 메인 내용 -->

   <div class="container">
      <div class="row" style="height: 100%">
      <div class="col-lg-3 md-3" style="background-color: white;"></div>
      <div class="col-lg-6 md-6" style="background-color: white;">
         <form action="myPageUpdate" method="POST" class="form-group">
               <div class="md-form">
                  <label>E-Mail Address</label> 
                  <input type="text" name="email" id="email" class="form-control" value="${ memberEmail }" disabled>
                  <br>
                  <label>Student ID</label>
                  <input type="number" name="studentId" class="form-control" value="${ memberStudentId }" maxlength="10">
                  <br>
                  <label>Name</label>
                  <input type="text" name="name" class="form-control" value="${ memberName }">
                  <br>
                  <label>Current Password</label>
                  <input type="password" name="password" id="password" class="form-control">
                  <br>
                  <label>Password check</label>
                  <input type="password" name="passwordConfirm" id="passwordConfirm" class="form-control">
                  <br> 
            
                  <div align="right"><br>                     
                  	<input type="button" onClick="modal_delete()" name="delete" class="btn btn-danger" style="width:100px;" value="Delete" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#deleteModal" style="margin-left:20px;">
                  	<input type="submit" name="modify" class="btn btn-success" style="margin-left:330px; width:100px;" value="Modify">
                     
                  </div>
               </div>
             </form>
      </div>
      <div class="col-lg-3 md-3" style="background-color: white;"></div>
   </div>
   </div>
   
   <!-- 삭제 확인 모달 -->
   <div class="modal fade" id="deleteModal" style="overflow: auto;">
      <div class="modal-dialog modal-dialog-centered">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title">Delete Member</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="myPageDelete" method="POST" class="form-group">
               <div class="modal-body">
                  <input type="hidden" name="m_password" id="m_password" class="form-control">
                     <input type="hidden" name="m_passwordConfirm" id="m_passwordConfirm" class="form-control">
                     
                  <input type="text" name="m_email" id="m_email" class="form-control" style="text-align:center;" disabled><br>
                  <label>회원을 탈퇴하시겠습니까?</label><br>
                  <br>
                  <label>탈퇴를 하실 경우 모든 회원 정보가 사라집니다.</label>
               </div>
               <div class="modal-footer">
                  <input type="submit" name="ok" value="OK" class="btn btn-danger">
               </div>
            </form>
         </div>
         <div class="col-lg-4 md-4" style="background-color: white;"></div>
      </div>
   </div>

   
</body>

</html>