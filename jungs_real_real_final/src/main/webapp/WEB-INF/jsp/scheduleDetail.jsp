<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<script>
	// 일정 삭제 모달
	function modal_delete(auto, date, title) {
		console.log(auto);
		console.log(auto);
		console.log(auto);

		$(".modal-body #m_auto").val(auto);
		$(".modal-body #m_date").val(date);
		$(".modal-body #m_title").val(title);
		$("#infoModal").on("click");

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
		<h3>< Planner Detail ></h3>
		<hr>
		<br>
	</div>
</header>
<body>
<!-- 일정 상세보기하는 메인 페이지 내용 -->

<div class="container">
	<div class="row" style="height: 100%">
		<div class="col-lg-3 md-3" style="background-color: white;"></div>
		<div class="col-lg-6 md-6" style="background-color: white;">
			<form action="scheduleUpdate" method="POST" class="form-group">
				<div class="md-form">
					<input type="hidden" name="auto" class="form-control" value="${ auto }">
					<input type="text" readonly name="date" class="form-control" value="${ date }">
					<label>Title</label> 
					<input type="hidden" name="last_title" class="form-control" value="${ title }">
					<input type="text" name="title" class="form-control" value="${ title }">
					<br>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label>Start Time</label>
							<input type="time" name="start" class="form-control" value="${ start }">
						</div>
						<br>
						<div class="form-group col-md-6">
							<label> Finish Time</label>
							<input type="time" name="finish" class="form-control" value="${ finish }">
						</div>
					</div>
					<label>Color</label>
					<input type="color" name="color" class="form-control" value="${ color }">
					<br>
					<label>Place</label>
					<input type="text" name="place" class="form-control" value="${ place }">
					<br>
					<label>Memo</label>
					<textarea class="form-control" name="memo"></textarea>
					<br> 
            
					<div align="right"><br>
						<input type="button" onClick="modal_delete('${auto}','${date}','${title}')" name="delete" value="Delete"  class="btn btn-danger" style="width:100px; margin-right:330px;" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#deleteModal">
						<input type="submit" name="modify" class="btn btn-success" style="width:100px;" value="Modify">
					</div>
                  
				</div>
			</form>
		</div>
		<div class="col-lg-3 md-3" style="background-color: white;"></div>
	</div>
</div>

   	<!-- 일정 삭제 팝업 -->
	<div class="modal fade" id="deleteModal" style="overflow: auto;">
      <div class="modal-dialog modal-dialog-centered">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title">Delete Schedule</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="scheduleDelete" method="POST" class="form-group">
               <div class="modal-body">         
                  <input type="hidden" id="m_date" name="m_date">
                  <input type="hidden" id="m_auto" name="m_auto">
                  
                  <input type="text" readonly id="m_title" name="m_title" class="form-control">
                  <br>
                  <h6>일정을 삭제하시겠습니까?</h6>
               </div>
               
               <div class="modal-footer">
                  <input type="submit" name="delete" value="Delete" class="btn btn-danger">
               </div>
            </form>
         </div>
      </div>
   </div>
</body>
</html>