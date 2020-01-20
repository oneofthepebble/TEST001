<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>

	<!-- Style -->
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/main/font-awesome.css">
    <link rel="stylesheet" href="/css/shop/shop_admin_paylist.css">
    
    <!-- JavaScript Libraries -->
    <script src="/js/jquery.js"></script>

	<div class="container">
		<div class="basket_title">
			<span class="admin_title">관리자 주문 내역 관리</span>
			<div class="searchResult">
	            <span class="admin_subtitle_sub">"<c:if test="${find_name == ''}">전체</c:if>${find_name}"</span>
	            에 대한 <span>"${totalcount}"</span> 개의 검색 결과가 있습니다.
            </div>
		</div>
		
       	<form method="post">
		<div class="basket_list">
			<!-- 상품 구매 내역 목록 테이블 부분-->
               <div class="basket_table">
               	<p class="basket_head">
               		<span style="width: 5%;">주문 번호</span>
               		<span style="width: 10%;">고객 아이디</span>
               		<span style="width: 30%;">상품명</span>
               		<span style="width: 10%;">결제금액</span>
               		<span style="width: 15%;">결제일</span>
               		<span style="width: 15%;">처리상태</span>
               		<span style="width: 15%;">결제확인</span>
               	</p>
               	<%-- 상품 구매 내역 목록 테이블 시작 --%>
               	<c:choose>
               		<%-- 구매 내역이 없을 때 --%>
               		<c:when test="${empty payList}">
               			<div class="basket_body">
							<div class="column table_none">
								고객 주문 내역이 없습니다.
							</div>
						</div>
               		</c:when>
               		
               		<%-- 구매 내역이 있을 때 --%>
               		<c:otherwise>
               			<c:forEach var="p" items="${payList}" varStatus="status">
							<div class="basket_body">
		                    	<div class="column table_no">
		                   			${p.pay_no}
		                    	</div>
		                    	<div class="column table_id">
		                    		${p.user_id}
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
			                    	<c:if test="${p.validity == 1}">상품 요청</c:if>
			                    	<c:if test="${p.validity == 2}">결제 확인</c:if>
			                    	<c:if test="${p.validity == 3}">발송 처리 완료</c:if>
		                    	</div>
		                    	<div class="column table_confirm">
		                    		<c:if test="${p.validity == 1}">
		                    			<button class="confirm_order_btn" 
		                    			onclick="javascript: form.action='pay_admin_confirm?pay_no=${p.pay_no}';">주문확정하기</button>
		                    		</c:if>
			                    	<c:if test="${p.validity == 2}">
			                    		<button class="confirm_send_btn" 
		                    			onclick="javascript: form.action='pay_admin_sendItem?pay_no=${p.pay_no}';">발송확정하기</button>
			                    	</c:if>
			                    	<c:if test="${p.validity == 3}">
			                    		<span>발송완료</span>
			                    	</c:if>
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
              	
           	<%-- 페이징 처리 --%>
           	<div class="paging_wrapper">
        <%-- 검색전 페이징 --%>
        	<c:if test="${(empty find_field) && (empty find_name)}">
        		<%-- page가 1페이지 이하일때 --%>
        		<c:if test="${page <=1}"></c:if>
        		<%--//page가 1페이지 이하일때 --%>
				<c:if test="${page >1}">
					<a href="admin_paylist?page=${page-1}">
		                <i class="fa fa-angle-left" aria-hidden="true"></i>
	                	<span class="ir_su">prev</span>
		            </a>
				</c:if>

					<%-- 쪽번호 출력부분 --%>
					<c:forEach var="n" begin="${startpage}" end="${endpage}" step="1">
						<c:if test="${n == page}">
							<span class="recentPage">${n}</span>
						</c:if>

						<c:if test="${n != page}">
							<a href="admin_paylist?page=${n}">${n}</a>
						</c:if>
					</c:forEach>

					<c:if test="${page>=maxpage}"></c:if>
					<c:if test="${page<maxpage}">
						<a href="admin_paylist?page=${page+1}"> <i
							class="fa fa-angle-right" aria-hidden="true"></i> 
						</a>
							<span class="ir_su">next</span>
					</c:if>
					<%--//쪽번호 출력부분 --%>
				</c:if>
				
        <%--//검색전 페이징 --%>
        
        <%-- 검색후 페이징 --%>
        	<c:if test="${(!empty find_field) || (!empty find_name)}">
        		<%-- page가 1페이지 이하일때 --%>
        		<c:if test="${page <=1}"></c:if>
				<c:if test="${page >1}">
					<a href="admin_paylist?page=${page-1}&find_field=${find_field}&find_name=${find_name}">
		                <i class="fa fa-angle-left" aria-hidden="true"></i>
	                	<span class="ir_su">prev</span>
		            </a>
				</c:if>

					<%-- 쪽번호 출력부분 --%>
					<c:forEach var="n" begin="${startpage}" end="${endpage}" step="1">
						<c:if test="${n == page}">
							<span class="recentPage">${n}</span>
						</c:if>

						<c:if test="${n != page}">
							<a href="admin_paylist?page=${n}&find_field=${find_field}&find_name=${find_name}">${n}</a>
						</c:if>
					</c:forEach>

					<c:if test="${page>=maxpage}"></c:if>
					<c:if test="${page<maxpage}">
						<a href="admin_paylist?page=${page+1}&find_field=${find_field}&find_name=${find_name}"> 
						<i class="fa fa-angle-right" aria-hidden="true"></i> 
						</a>
						<span class="ir_su">next</span>
					</c:if>
					<%--//쪽번호 출력부분 --%>
				</c:if>
        	<%--//page가 1페이지 이하일때 --%>
        <%--//검색후 페이징 --%>
        </div>
           	
       	<%--//페이징 처리 --%>
        <!-- 검색 바 -->
        <div class="searchBar">
            <!-- 검색폼 -->
                <div class="searchRow">
                    <select name="find_field" class="search_box">
                        <option value="pay_no" 
                        <c:if test="${find_field == 'pay_no' }">
                        ${'selected'}</c:if>>주문번호</option>
                        <option value="user_id" 
                        <c:if test="${find_field == 'user_id' }">
                        ${'selected'}</c:if>>유저아이디</option>
                    </select>
                    
                    <input name="find_name" id="find_name" 
                    style="width: 250px;" placeholder="검색어를 입력해주세요."
                    class="search_word" value="${find_name}">
                    <button class="search_btn" id="search_btn" title="검색" 
                    	onclick="javascript: form.action='admin_paylist';">
                        <i class="fa fa-search" aria-hidden="true"></i>
                    </button>
                </div>
                </div>
        <!--//검색 바 -->
		</form>
		<div class="basket_buy">
			<button type="button" id="basketList_btn" onclick="location.href='total_shop?page=${page}&find_field=item_name&find_name=';">상품 목록</button>
		</div>
	</div>
	<script>
	$('.confirm_order_btn').on('click', function(){
		var ask = confirm('해당 주문을 확정 하시겠습니까?');
		
		if(ask) {
			
		}else {
			return false;
		}
	});
	
	$('.confirm_send_btn').on('click', function(){
		var ask = confirm('해당 주문상품을 발송 하시겠습니까?');
		
		if(ask) {
			
		}else {
			return false;
		}
	});
	</script>
	
<jsp:include page="../include/footer.jsp" />