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
	   <h3>< Planner Add ></h3>
	   <hr>
	   <br>
	</div>
</header>
<body>
<!-- 일정 추가하는 메인 페이지 내용 -->
   <div class="container">
      <div class="row" style="height: 100%">
      <div class="col-lg-3 md-3" style="background-color: white;"></div>
      <div class="col-lg-6 md-6" style="background-color: white;">
         <form action="scheduleAddCheck" method="POST" class="form-group">
               <div class="md-form">
               	<input type="hidden" name="auto" class="form-control" value="${ auto }">
               	<input type="text" readonly name="date" class="form-control" value="${ date }">
                  <label>Title</label> 
                  <input type="text" name="title" class="form-control">
                  <br>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label>Start Time</label> 
							<input type="time" name="startTime" class="form-control">
						</div>
						<br>
						<div class="form-group col-md-6">
							<label> Finish Time</label> 
							<input type="time" name="finishTime" class="form-control">
						</div>
					</div>
				<label>Color</label>
                  <input type="color" name="color" class="form-control" value="#F6CEF5">
                  <br>
                  <label>Place</label>
                  <input type="text" name="place" class="form-control">
                  <br>
                  <label>Memo</label>
                  <textarea class="form-control" name="memo"></textarea>
                  <br> 
            
                  <div align="right"><br>
                     <input type="reset" class="btn btn-danger" style="width:100px; margin-right:330px;" value="reset">              
                     <input type="submit" name="add" class="btn btn-success" style="width:100px;" value="Add">
                  </div>
               </div>
             </form>
      </div>
      <div class="col-lg-3 md-3" style="background-color: white;"></div>
   </div>
   </div>
   
</body>
</html>