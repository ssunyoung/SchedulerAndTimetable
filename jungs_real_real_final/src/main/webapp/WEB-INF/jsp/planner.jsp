<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<script>
	function modal_info(date,auto) {

		$(".modal-body #md_auto").val(auto);
		$(".modal-header #md_date").val(date);
		$("#infoModal").on("click");

	}

	function modal_delete(auto) {
		$(".modal-body #auto_delete").val(auto);
		$("#deleteModal").on("click");

	}

	function modal_update(auto, title) {
		$(".modal-body #auto_update").val(auto);
		$(".modal-body #title_update").val(title);
		$("#deleteModal").on("click");

	}

	function modal_add() {
		$("#addModal").on("click");

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
        <h3>Make Your Planner!</h3>
        <hr>
        <br>
     </div>
</header>
<!-- 플래너 메인 페이지 내용 -->
	<div class="container">
		<!-- 새로운 스케줄 등록 버튼 -->
		<div align="left">
			<input type="button" onClick="modal_add()" name="add" value="Add New Planner"  class="btn btn-info" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#addModal">
		</div><br>
		
		<div class="row">
			<!-- 생성되어 있는 planner의 name 리스트를 출력하는 box -->
			<c:choose>
				<c:when test="${ isList eq true }" >
					<div class="col-md-2" style="border:1px solid gray; margin:0 auto 0 0; padding:20px;">
					   <div align="left">
					      <br>
					      <c:forEach items="${ title }" var="tt" varStatus="i">
								<div class="dropdown" style="margin: 0px 0px 30px 10px;">
									<a href="planner?auto=${auto[i.index]}" style="margin-right:5px;"><b>${tt}</b></a>
									<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
										<span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li style="margin-left:20px;"><a href="javascript:void(0);" class="text-dark" onClick="modal_delete('${auto[i.index]}')" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#deleteModal">
												<b> Delete </b>
											</a><hr>
					     				</li>
										<li style="margin-left:20px;"><a href="javascript:void(0);" class="text-dark" onClick="modal_update('${auto[i.index]}', '${tt}')" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#updateModal">
												<b> Modify </b>
											</a>
					     				</li>
										<li></li>
									</ul>
								</div>
					      </c:forEach>
					    </div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-md-2" style="border:1px solid gray; margin:0 auto; padding:auto; visibility:hidden;"></div>
				</c:otherwise>
			</c:choose>
			<!-- 좌측 리스트에서 클릭시 해당 Planner을 출력하는 box -->
			<c:choose>
				<c:when test="${ isList eq true }" >
					<c:choose>
						<c:when test="${ isClick eq true }" >
							<div class="col-md-9" align="center" style="margin:0; padding:0;">
								<table class="table table-bordered text-center">
									<tr>
										<th style="width:14%;"><font color="red">Sun</font></th>
										<th style="width:14%;">Mon</th>
										<th style="width:14%;">Tue</th>
										<th style="width:14%;">Wed</th>
										<th style="width:14%;">Thu</th>
										<th style="width:14%;">Fri</th>
										<th style="width:14%;"><font color="blue">Sat</font></th>
									</tr>
									<tr><td colspan="7"></td></tr>
									<c:forEach items="${ dateList }" var="dateList" varStatus="dl">
										<c:choose>
											<c:when test="${ dl.index%7 == 0 }">
												<tr>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${ dateList != 'x' }">
												<c:choose>
													<c:when test="${ dateList == 'no' }">
														<td style="margin:0; padding:0;">
															<div style="background-color:#e8e8e8; opacity:0.5; margin:0; padding:2px; height:30px;"></div>
															<div style="margin:0; padding:2px; height:150px;">
															
															</div>
														</td>
													</c:when>
													<c:otherwise>
														<td style="margin:0; padding:0;">
															<div style="background-color:#e8e8e8; margin:0; padding:2px; height:30px;">
																<a href="javascript:void(0);" class="text-dark" onClick="modal_info('${dateList}','${p_auto}')" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#infoModal">${dateList}</a>
															</div>
															<div style="margin:0; padding:2px; height:150px;">
																<c:forEach items="${ scheduleList }" var="scheduleList">
																	<c:choose>
																		<c:when test="${ dateList eq scheduleList.date }">
																			<div style="background-color:${scheduleList.color};"><a href="scheduleDetail?auto=${scheduleList.auto}&title=${scheduleList.title}&date=${scheduleList.date}" class="text-dark">${ scheduleList.title }</a></div>
																		</c:when>
																		<c:otherwise>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</div>
														</td>
													</c:otherwise>
												</c:choose>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${ dl.index%7 == 6 }">
												</tr>
											</c:when>
										</c:choose>
									</c:forEach>
								</table>
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-9" align="center" style="margin:auto; padding:auto; padding-top:50px; padding-right:50px;">
								<div>${ message }</div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="col-md-9" align="center" style="margin:auto; padding:auto; padding-top:50px; padding-right:50px;">
						<div>${ message }</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<br><br>	

	<!-- 캘린더 추가 팝업 -->
	<div class="modal fade" id="addModal" style="overflow: auto;">
      <div class="modal-dialog modal-dialog-centered">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title">Add Calendar</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <form action="addPlanner" method="POST" class="form-group">
               <div class="modal-body">         
                  <label>Calendar Name</label><br>
                  <input type="text" name="m_title" id="m_title" class="form-control">
                  <br>         
                  <label>Fist Date</label><br> 
                  <input type="date" name="m_first" id="m_first" class="form-control">
                  <br> 
                  <label>Last Date</label><br>
                  <input type="date" name="m_last" id="m_last" class="form-control">
                  <br> 
               </div>
               
               <div class="modal-footer">
                  <input type="submit" name="add" value="Add" style="width:100px;" class="btn btn-success">
               </div>
            </form>
         </div>
      </div>
   </div>
	
		<!-- 캘린더 삭제 팝업 -->
	<div class="modal fade" id="deleteModal" style="overflow: auto;">
      <div class="modal-dialog modal-dialog-centered">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title">Add Calendar</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <form action="deletePlanner" method="POST" class="form-group">
               <div class="modal-body">     
                   <input type="hidden" id="auto_delete" name="auto_delete">
                <h5>삭제 하시겠습니까?</h5>
               </div>
               
               <div class="modal-footer">
                  <input type="submit" name="delete" value="Delete" class="btn btn-danger">
               </div>
            </form>
         </div>
      </div>
   </div>
	
	<!-- 캘린더 수정 팝업 -->
	<div class="modal fade" id="updateModal" style="overflow: auto;">
      <div class="modal-dialog modal-dialog-centered">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title">Add Calendar</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <form action="updatePlanner" method="POST" class="form-group">
               <div class="modal-body">     
                  <input type="hidden" id="auto_update" name="auto_update">
                  <input type="text" id="title_update" name="title_update" class="form-control">
                  
               </div>
               
               <div class="modal-footer">
                  <input type="submit" name="modify" value="Modify" class="btn btn-danger">
               </div>
            </form>
         </div>
      </div>
   </div>
   
	<!-- day 클릭시 정보 나타내기 -->
	<div class="modal fade" id="infoModal" style="overflow: auto;">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<form action="scheduleAdd" method="POST" class="form-group">
					<div class="modal-header" align="center">
						<h4 class="modal-title">
							<input type="text" readonly class="form-control-plaintext" name="md_date" id="md_date">
						</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<input type="hidden" name="md_auto" id="md_auto" >
							<c:forEach items="${ scheduleList }" var="scheduleList" varStatus="sl">
								<c:choose>
									<c:when test="${ scheduleList.date eq dateList[i]}">
										<div style="padding-bottom:5px; background-color:${scheduleList.color};"><c:out value="ss"/></div>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</div>
					
					<div class="modal-footer">
						<input type="submit" class="btn btn-success" value="Add" style="width:80px; height:40px;">
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>


