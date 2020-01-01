<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<script>
		// 강의 자세히 보기
		function modal_info(subject, professor, day, color, start, finish, place, auto) {
			console.log(auto);
			
			$(".modal-body #m_subject").val(subject);
			$(".modal-body #m_professor").val(professor);
			$(".modal-body #m_day").val(day);
			$(".modal-body #m_color").val(color);
			$(".modal-body #m_start").val(start);
			$(".modal-body #m_finish").val(finish);
			$(".modal-body #m_place").val(place);
			$(".modal-body #m_auto").val(auto);
			$("#infoModal").on("click");
			
		}

		// 강의 삭제 확인
 		function modal_delete(){
 	 		var auto = document.getElementById("m_auto").value;
			var subject=document.getElementById("m_subject").value;
			console.log(subject + auto);
			$(".modal-body #md_subject").val(subject);
			$(".modal-body #md_auto").val(auto);
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
        <h3>Make Your TimeTable!</h3>
        <hr>
        <br>
     </div>
</header>
<!-- 시간표 메인 페이지 내용 -->
	<div class="container">
		<div class="row">
		<!-- 강좌 등록 부분 -->
 			<div class="col-md-4" style="border:1px solid gray; margin:0 auto; padding:35px; border-radius:12px;">
				<form action="timetableInsert" method="POST" class="form-group">
               <div class="md-form">
                  <label>SUBJECT</label> 
                  <input type="text" name="subject" class="form-control"><br>
                  <label>PROFESSOR</label>
                  <input type="text" name="professor" class="form-control"><br>
                  <label>COLOR</label>
                  <input type="color" name="color" class="form-control" value="#A9D0F5"><br>
                  <label>DAY of the week</label>
                  <select name="day" id="day" class="form-control">
                  	<option>월</option>
                  	<option>화</option>
                  	<option>수</option>
                  	<option>목</option>
                  	<option>금</option>
                  </select><br>
                  <label>START</label>
	                 <select name="start" id="start" class="form-control">
	                 	<c:forEach var="list" items="${time}" varStatus="stime">
	                 		<option><c:out value="${list}" /></option>
	                 	</c:forEach>
	                 </select><br>
                  <label>FINISH</label>
	                 <select name="finish" id="finish" class="form-control">
	                 	<c:forEach var="list" items="${time}" varStatus="ftime">
	                 		<option><c:out value="${list}" /></option>
	                 	</c:forEach>
	                 </select><br>
                  <label>PLACE</label>
                  <input type="text" name="place" class="form-control">
                  <div align="right">
                  <br><input type="submit" name="submit" class="btn btn-light" style="width:100px;" value="Add">
                  </div>
                  </div>
               </form>
		</div>
	<c:choose>
	<c:when test="${isTable}">
		<!-- 시간표 부분 -->
		<div class="col-md-8" style="margin:auto; margin-top:10px; padding:auto;">
			<div>
				<table class="table table-bordered text-center">
					<tbody>
						<tr>
							<th style="width:15%; padding:5px;"></th>
							<th style="width:15%; padding:5px;">월</th>
							<th style="width:15%; padding:5px;">화</th>
							<th style="width:15%; padding:5px;">수</th>
							<th style="width:15%; padding:5px;">목</th>
							<th style="width:15%; padding:5px;">금</th>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 시간 출력 부분 -->
				<div>
					<table class="table table-bordered text-center">
						<tbody>
							<tr>
								<th style="width: 15%; padding: 0;">
									<div class="text-center" style="font-size: 1rem;">
										<c:forEach var="list" items="${time}" varStatus="stat">
											<c:choose>
												<c:when test="${stat.count%5 eq 1}">
													<div style="background-color: white; height: 1.25rem;">
														${list}
													</div>
												</c:when>
												<c:otherwise>
													<div style="background-color: white; height: 1.25rem;">
														<font color="lightgray">${list}</font>
													</div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</div>
								</th>
								<!-- 시간표 출력 부분 -->
								<c:set var="start" value="${start}" />
								<c:set var="finish" value="${finish}" />
								<c:set var="day" value="${day}" />

								<c:forEach begin="0" end="4" varStatus="week">
									<td style="width: 15%; padding: 0;">
										<div class="text-center" style="font-size: 0.5rem;">
										<c:forEach begin="0" end="45" varStatus="stat">
											<c:forEach var="timeList" items="${timeList}" varStatus="tc">
													<c:choose>
														<c:when test="${week.index==day[tc.index]}">
															<c:choose>
																<c:when test="${stat.index > start[tc.index] && stat.index <= finish[tc.index]}">
																	<div style="background-color: ${timeList.color}; height: 1.25rem;">
																		<br>
																	</div>
																</c:when>
																<c:when test="${stat.index == start[tc.index]}">
																	<div style="background-color: ${timeList.color}; height: 1.25rem; font-size: 1.0rem;">
																		<a href="javascript:void(0);" class="text-dark" onClick="modal_info('${timeList.subject}','${timeList.professor}', '${timeList.day}', '${timeList.color}', '${timeList.start}', '${timeList.finish}', '${timeList.place}', '${timeList.auto}')" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#infoModal">${timeList.subject}</a>																	
																	</div>
																</c:when>
																<c:when test = "${day[tc.index] == day[tc.index+1] && stat.index < start[tc.index+1] && stat.index >= finish[tc.index]}">
																		<div style="background-color: white; height: 1.25rem;">
																			<br>
																		</div>
																</c:when>
																<c:when test="${day[tc.index] == day[tc.index+1] && stat.index >= 0 && stat.index < start[tc.index]}">
																	<div style="background-color: white; height: 1.25rem;">
																			<br>
																	</div>
																</c:when>
																<c:when test="${(day[tc.index] != day[tc.index+1] && day[tc.index] != day[tc.index-1])}">
																	<div style="background-color: white; height: 1.25rem;">
																		<br>
																	</div>
																</c:when>
															</c:choose>
														</c:when>
													</c:choose>
												</c:forEach>
											</c:forEach>
															
										</div>
									</td>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div align="center" class="col-md-8" style="margin:0; padding-right:50px; padding-top:130px;">
				<div>${ message }</div>
			</div>
		</c:otherwise>
		</c:choose>
	</div>
	</div>
	<br><br>
	
	<!-- 정보 확인 팝업 -->
	<div class="modal fade" id="infoModal" style="overflow: auto;">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Lecture Information</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				
				<form action="timetableUpdate" method="POST" class="form-group">
					<div class="modal-body">
						<input type="hidden" name="m_auto" id="m_auto" class="form-control">
						
						<label>Subject</label><br> 
						<input type="text" name="m_subject" id="m_subject" class="form-control" value="">
						
						<label>Professor</label><br>
						<input type="text" name="m_professor" id="m_professor" class="form-control">
						
						<label>Color</label><br>
						<input type="color" name="m_color" id="m_color" class="form-control">
						
						<label>Day</label><br>
						<select name="m_day" id="m_day" class="form-control">
							<option>월</option>
		                  	<option>화</option>
		                  	<option>수</option>
		                  	<option>목</option>
		                  	<option>금</option>
						</select>
						
						<label>Start</label><br>
						<select name="m_start" id="m_start" class="form-control">
	                 	<c:forEach var="list" items="${time}" varStatus="stime">
	                 		<option><c:out value="${list}" /></option>
	                 	</c:forEach>
	              		</select>
	              		
						<label>Finish</label><br>
						<select name="m_finish" id="m_finish" class="form-control">
	                 	<c:forEach var="list" items="${time}" varStatus="ftime">
	                 		<option><c:out value="${list}" /></option>
	                 	</c:forEach>
	              		</select>
						
						<label>Place</label><br>
						<input type="text" name="m_place" id="m_place" class="form-control">
						<br>
					</div>
					
					<div class="modal-footer">
						<input type="submit" name="modify" value="Modify" class="btn btn-success">
						<input type="button" onClick="modal_delete()" name="delete" value="Delete" class="btn btn-danger" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#deleteModal" style="margin-left:20px;">
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- 삭제 확인 모달 -->
	<div class="modal fade" id="deleteModal" style="overflow: auto;">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Delete Lecture</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form action="timetableDelete" method="POST" class="form-group">
					<div class="modal-body">
						<input type="hidden" name="md_auto" id="md_auto" class="form-control">				
						<input type="text" name="md_subject" id="md_subject" class="form-control" style="text-align:center;" disabled>
						<br>
						<h6>강의를 삭제 하시겠습니까?</h6>
					</div>
					<div class="modal-footer">
						<input type="submit" name="ok" value="OK" class="btn btn-danger">
					</div>
				</form>
			</div>
		</div>
	</div>
	
</body>

</html>