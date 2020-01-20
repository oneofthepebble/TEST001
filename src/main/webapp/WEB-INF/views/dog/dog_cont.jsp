<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/dog_board/dog.css">

<!-- jquery -->
<script src="/js/dog_board/dog.js"></script>

<!-- 본문 내용 -->
<main>
<section id="contents">
	<div class="container">
		<section id="cont">
			<article class="column col1">
				<div class="main">
					${dog_cont }
				</div>
				
				<div id="dog_menu">
					<c:set var="b" value="${user_id }" />
					<c:if test="${b=='admin' }">
					<input type="button" value="수정" 
					onclick="location='dog_cont?dog_no=${d.dog_no}&page=${page }&state=edit';" />
					
					</c:if>
					
					<input type="button" value="목록" 
					onclick="location='/dog/total_dog?page=${page}';" />
					
					<form method="post" action="/dog_del_ok" onsubmit="return del_check();">
						<input type="hidden" name="dog_no" value="${d.dog_no}" />
   						<input type="hidden" name="page" value="${page}" />
   						<input type="hidden" name="dog_img" value="${d.dog_file }" />
   						
   						<c:set var="b" value="${user_id }" />
						<c:if test="${b=='admin' }">
						<input type="submit" value="삭제" id="del"
						onclick="location='dog_cont?dog_no=${d.dog_no}&page=${page}&state=del';"/>
						
						</c:if>
					</form>	
				</div>
				
				
				
			</article>
		</section>
	</div>
</section>
<!-- contents --> 
</main>

<jsp:include page="../include/footer.jsp" />

