<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/QandA/QandA.css">

<!-- jquery -->
<script src="/js/QandA/QandA.js"></script>

<!-- 본문 내용 -->
<main>
<section id="contents">
	<div class="container">
		<section id="cont">
			<article class="column col1">
	
				<div class="main">
					<div class="qboard_name">
						[Q&A]
					</div>
						<div class="qdate">${q.q_date }</div>
						<div class="qhit">조회수: ${q.q_hit }</div>
					<div class="qtitle">
						${q.q_title }
					</div>
					<div class="qname">
						작성자 : ${q.q_id }
					</div>
						${q_cont }
				</div>
				
				<div id="q_menu">
					<input type="button" value="답변" 
					onclick="location='QandA_cont?q_no=${q.q_no}&page=${page }&state=reply';" />
					
					<input type="button" value="목록" 
					onclick="location='/QandA/QandA_list?page=${page}';" />
					
					<form method="post" action="/QandA_del_ok" onsubmit="return QandA_del_check();">
						<input type="hidden" name="q_no" value="${q.q_no}" />
   						<input type="hidden" name="page" value="${page}" />
   						
   						
   						<c:if test="${q.q_id == user_id || user_id=='admin'}" >
   							<input type="button" value="수정" 
							onclick="location='QandA_cont?q_no=${q.q_no}&page=${page }&state=edit';" />
							
							<input type="submit" value="삭제" id="del"
							onclick="location='QandA_list?q_no=${q.q_no}&page=${page};"/>					 
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

