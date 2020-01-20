<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>

	<!-- Style -->
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/shop/pay_list.css">

	<div class="container">
		<div class="basket_title">
			<span>${user_id}</span> 님의 주문 내역
		</div>
		
       	<form method="post">
		<div class="basket_list">
			<!-- 상품 구매 내역 목록 테이블 부분-->
               <div class="basket_table">
               	<p class="basket_head">
               		<span style="width: 7%;">주문 번호</span>
               		<span style="width: 30%;">상품명</span>
               		<span style="width: 15%;">결제금액</span>
               		<span style="width: 12%;">결제일</span>
               		<span style="width: 15%;">처리상태</span>
               	</p>
               	<%-- 상품 구매 내역 목록 테이블 시작 --%>
               	<c:choose>
               		<%-- 구매 내역이 없을 때 --%>
               		<c:when test="${empty list}">
               			<div class="basket_body">
							<div class="column table_none">
								주문 내역이 없습니다.
							</div>
						</div>
               		</c:when>
               		
               		<%-- 구매 내역이 있을 때 --%>
               		<c:otherwise>
               			<c:forEach var="p" items="${list}" varStatus="status">
							<div class="basket_body">
		                    	<div class="column table_no">
		                   			${p.pay_no}
		                    	</div>
		                    	<div class="column table_title">
		                    		<c:if test="${p.validity == 1}">
		                    			<a href="pay_item_list_go?pay_no=${p.pay_no}&validity=${p.validity}">
		                   				${payNameList[status.index].product_name}</a>
		                    		</c:if>
		                    		<c:if test="${p.validity == 2 || p.validity == 3}">
			                   			<a href="pay_item_list_go?pay_no=${p.pay_no}&validity=${p.validity}">
			                   			${payNameList2[status.index].product_name}</a>
		                    		</c:if>
		                    	</div>
		                    	<div class="column table_price">
		                    		\ <fmt:formatNumber pattern="###,###,###" value="${p.pay_price}" />
		                    	</div>
		                    	<div class="column table_date">
		                    		${p.pay_date}
			                    </div>
			                    <div class="column table_view">
			                    	<c:if test="${p.validity == 1}">결제 확인 중</c:if>
			                    	<c:if test="${p.validity == 2}">결제 확인</c:if>
			                    	<c:if test="${p.validity == 3}">배송중</c:if>
		                    	</div>
		                    </div>
                    	</c:forEach>
               		</c:otherwise>
               	</c:choose>
               	
               <%--//상품 구매 내역 목록 테이블 시작 --%>
                </div>
               <!--//상품 구매 내역 목록 목록 테이블 부분-->
           	</div>
              	<%-- basket list --%>
              	
			<div class="basket_buy">
				<button type="button" id="basketList_btn" onclick="location.href='total_shop?page=${page}&find_field=item_name&find_name=';">상품 목록</button>
			</div>
		</form>
	</div>
	
<jsp:include page="../include/footer.jsp" />