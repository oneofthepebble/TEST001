
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>

<link rel="stylesheet"
	href="<c:url value="/resources/css/MyPage/MyPage.css"/>"></link>
<link rel="stylesheet" href="<c:url value="/resources/css/reset.css" />"></link>
<script src="<c:url value="/resources/js/MyPage/MyPage.js" />"></script>

<!-- 헤더 사진 -->
<script>
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/login_wallpaper.jpg); background-position:top; background-size:cover;');
});
</script>

<section>
	<!-- MyPageContainer 영역 -->
	<div id="MyPageContainer">
	<!-- include 장바구니 목록 -->
		<a href="#"><input id="ShopLink" type="button" value="장바구니"/></a>
		<form action="#">
			<!-- MemberInfos 회원 정보를 담을 Items -->
			<div id="MemberInfos">
				<div class="MemberInfo">
					<span>회</span> <span>원</span> <span>정</span> <span>보</span>
				</div>
				<!-- MemberInfo -->
				
				<div class="MemberID UpdateMember" name="user_id"><p>ID : </p><span class="MemberValue">${MemberInfo.user_id}</span></div>

				<div class="MemberName UpdateMember"><p>Name : </p> <span>${MemberInfo.user_name}</span></div>

				<div class="MemberPassword UpdateMember">
					<div class="Member_password" id="Member_password">
						<input class="updateBtn" id="updateBtn_password" type="button"
							value="비밀번호 변경" onclick="updatePwd();">
					</div>
					<div class="Update_Pwd_Area" id="Update_Pwd_Area">
						<input id="Update_Pwd" name="user_pwd" type="password" placeholder="변경할 비밀번호를 입력하세요">
						<input class="updateBtn" id="Update_Pwd_ok" type="button" value="확인" onclick="updatePwd_ok();">
					</div>
				</div>

				<div class="MemberBirthDay UpdateMember"><p>Birthday : </p> <span>${MemberInfo.user_birthday}</span> </div>

				<div class="MemberE_mail UpdateMember" id="user_update_email">
					<div class="Member_Email" id="Member_Email">
						<p class="OneLine">E-Mail : <span>${MemberInfo.user_email}</span></p>
						<input class="updateBtn" id="updateBtn_Email" type="button"
							value="수정" onclick="updateEmail();">
					</div>
					<div class="Update_Email_Area" id="Update_Email_Area">
						<input id="Update_Email" name="user_email" type="text" placeholder="E-mail을 입력하세요"> <input
							class="updateBtn" id="Update_Email_ok" type="button" value="확인"
							onclick="updateEmail_ok();">
					</div>
				</div>
				<!-- MemberE_mail -->

				<div class="MemberPhoneNumber UpdateMember">
					<div class="Member_PhoneNumber" id="Member_PhoneNumber">
						<p class="OneLine">Phone : <span>${MemberInfo.user_phone}</span></p>
						<input id="updateBtn_PN" type="button" value="수정"
							onclick="updatePN();">
					</div>

					<div class="Update_PN_Area" id="Update_PN_Area">
						<input id="Update_PN" type="text" placeholder="00*-000*-0000" onKeyup="inputPhoneNumber(this);" maxlength="13"> <input
							id="Update_PN_ok" type="button" value="확인"
							onclick="updatePN_ok();">
					</div>
				</div>
				<!-- MemberPhoneNumber -->
			</div>
			<!-- end MemberInfos -->
		</form>
		<!-- MyPage 안에 게시판 영역 -->

		<!-- start NoticeBoards -->
		<div id="NoticeBoards">
			<!-- 입양 게시판 -->
			<div class="AdoptBoard BoardContainer">
				<div class="Adopt">
					<span>입</span> <span>양</span> <span>게</span> <span>시</span> <span>판</span>
				</div>

				<div class="AdoptCont">
					<ul class="Adopt_Ul">
						<c:if test="${!empty Board}">
							<c:forEach var="Board" items="${Board}">
								<li class="OneLine"><a href="/cont?no=${Board.back_end_list_no}&page=1">${Board.back_end_list_title}</a></li>
							</c:forEach>
						</c:if>
						<c:if test="${empty Board}">
						<li class="OneLine">목록이 없습니다.</li>
						</c:if>
					</ul>
				</div>
			</div>
			<!-- AdoptBoard -->

			<!-- 일반 게시판 -->
			<div class="NormalBoard BoardContainer">
				<div class="Normal">
					<span>일</span> <span>반</span> <span>게</span> <span>시</span> <span>판</span>
				</div>

				<div class="NormalCont">
					<ul class="Normal_Ul">
						<c:if test="${!empty NormalBoard}">
							<c:forEach var="NBoard" items="${NormalBoard}">
								<li class="OneLine"><a href="/normal_board_list_cont?normal_no=${NBoard.normal_no}">${NBoard.normal_title}</a></li>
							</c:forEach>
						</c:if>
						<c:if test="${empty NormalBoard}">
						<li class="OneLine">목록이 없습니다.</li>
						</c:if>
					</ul>
				</div>
			</div>
			<!-- NormalBoard -->

		</div>
		<!-- end NoticeBoards -->

		
	</div>
	<!-- MyPageContainer -->
</section>
<jsp:include page="../include/footer.jsp" />



