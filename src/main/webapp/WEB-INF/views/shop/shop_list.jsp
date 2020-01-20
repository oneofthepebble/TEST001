<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>

    <!-- Style -->
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/shop/shop_list.css">
    <link rel="stylesheet" href="/css/main/font-awesome.css">
    
    <script src="/js/shop/shop.js"></script>
    
	<div class="container">
        <form method="GET" action="total_shop">
        <%-- 검색 결과 수 --%>
        <div class="searchResultRow">
            <div class="searchResult">
	            <span>"<c:if test="${find_name == ''}">전체</c:if>${find_name}"</span>
	            에 대한 <span>"${totalcount}"</span>
	            개의 검색 결과가 있습니다.
            </div>
	        <div class="admin_write">
	        	<c:if test="${checkCount == 1}">
	        		<button type="button" onclick="location.href='shop_write?page=${page}';">관리자 글쓰기</button>
	            <button type="button" onclick="location.href='admin_paylist_go';">관리자 주문내역</button>
	        	</c:if>
	      
	            <button type="button" id="basketList" onclick="location.href='basket_list_go?page=${page}';">장바구니</button>
	            <button type="button" id="payList" onclick="location.href='pay_list_go?page=${page}';">주문 내역</button>
	        </div>
        </div>
        <%--//검색 결과 수 --%>
		
		<%-- 검색 결과 목록 --%>
        <div class="searchList">
            <!-- 인기순 상품 목록 -->
            <!-- <div class="bestGoodsList">
               <div class="shopListTitle">인기 상품 목록</div>

               <div class="itemContainer">
                <div class="itemImgBox">
                    <div class="itemImg">
                        <a href="#">
                            <img src="/img/shop/goods01.jpg" alt="imgTest">
                        </a>
                    </div>
                    <span class="bestCount">Best 1</span>
                </div>
                
                <div class="itemNameRow">
                    <span><a href="#" class="itemName">고양이 화장실</a></span>
                </div>
                <div class="itemPriceRow">
                    <span class="itemPrice">\20,000</span>
                </div>
                <div class="itemRecoRow">
                    <span class="bestNum">이 제품이 좋아요 ! : 1</span>
                </div>
               </div>

               <div class="itemContainer">
                <div class="itemImgBox">
                    <div class="itemImg">
                        <a href="#">
                            <img src="/img/shop/goods02.jpg" alt="imgTest">
                        </a>
                    </div>
                    <span class="bestCount">Best 2</span>
                </div>
                
                <div class="itemNameRow">
                    <span><a href="#" class="itemName">고양이 화장실</a></span>
                </div>
                <div class="itemPriceRow">
                    <span class="itemPrice">\20,000</span>
                </div>
                <div class="itemRecoRow">
                    <span class="bestNum">이 제품이 좋아요 ! : 1</span>
                </div>
               </div>

               <div class="itemContainer">
                <div class="itemImgBox">
                    <div class="itemImg">
                        <a href="#">
                            <img src="/img/shop/goods03.jpg" alt="imgTest">
                        </a>
                    </div>
                    <span class="bestCount">Best 3</span>
                </div>
                
                <div class="itemNameRow">
                    <span><a href="#" class="itemName">고양이 화장실</a></span>
                </div>
                <div class="itemPriceRow">
                    <span class="itemPrice">\20,000</span>
                </div>
                <div class="itemRecoRow">
                    <span class="bestNum">이 제품이 좋아요 ! : 1</span>
                </div>
                </div>
            </div> -->
            <!--//인기순 상품 목록 -->


            <!-- 일반 상품 목록 -->
            <div class="goodsList">
                <div class="shopListTitle">상품 리스트</div>

				<%-- db에 상품 정보가 있을 때 --%>
				<c:if test="${!empty slist}">
				<c:forEach var="n" items="${slist}">
					<div class="itemContainer">
                    <div class="itemImgBox">
                        <div class="itemImg">
                            <a href="shop_cont?state=cont&item_no=${n.item_no}&page=${page}">
                                <img src="/resources/photo_upload${n.item_img}" alt="imgTest">
                            </a>
                        </div>
                    </div>
                    
                    <div class="itemNameRow">
                        <span><a href="shop_cont?state=cont&item_no=${n.item_no}&page=${page}" class="itemName">${n.item_name}</a></span>
                    </div>
                    <div class="itemPriceRow">
                        <span class="itemPrice">
                        	\ <fmt:formatNumber pattern="###,###,###" value="${n.item_price}" />
                        </span>
                    </div>
                    <div class="itemRecoRow">
                        <c:if test="${checkCount == 1}">
                        	<span class="bestNum">상품 재고 : <fmt:formatNumber pattern="###,###,###" value="${n.item_stockCount}" /> 개</span>
                        	<br/>
                        </c:if>
                        <%-- <span class="bestNum">이 제품이 좋아요 ! : ${n.item_likeCount}</span> --%>
                    </div>
                </div>
				
				</c:forEach>
				</c:if>
				<%--//db에 상품 정보가 있을 때 --%>
				
				<%-- db에 상품 정보가 없을 때 --%>
				<c:if test="${empty slist}">
				<div class="NoneItemContainer">
					<span>상품목록이 없습니다! 관리자에게 문의해주세요.</span>
                </div>
				</c:if>
				<%--//db에 상품 정보가 없을 때 --%>
            </div>
            <!--//일반 상품 목록 -->
        </div>
        <%--//검색 결과 목록 --%>

        <!-- 페이징 wrapper -->
        <div class="paging_wrapper">
        <%-- 검색전 페이징 --%>
        	<c:if test="${(empty find_field) && (empty find_name)}">
        		<%-- page가 1페이지 이하일때 --%>
        		<c:if test="${page <=1}"></c:if>
        		<%--//page가 1페이지 이하일때 --%>
				<c:if test="${page >1}">
					<a href="total_shop?page=${page-1}">
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
							<a href="total_shop?page=${n}">${n}</a>
						</c:if>
					</c:forEach>

					<c:if test="${page>=maxpage}"></c:if>
					<c:if test="${page<maxpage}">
						<a href="total_shop?page=${page+1}"> <i
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
					<a href="total_shop?page=${page-1}&find_field=${find_field}&find_name=${find_name}">
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
							<a href="total_shop?page=${n}&find_field=${find_field}&find_name=${find_name}">${n}</a>
						</c:if>
					</c:forEach>

					<c:if test="${page>=maxpage}"></c:if>
					<c:if test="${page<maxpage}">
						<a href="total_shop?page=${page+1}&find_field=${find_field}&find_name=${find_name}"> 
						<i class="fa fa-angle-right" aria-hidden="true"></i> 
						</a>
						<span class="ir_su">next</span>
					</c:if>
					<%--//쪽번호 출력부분 --%>
				</c:if>
        	<%--//page가 1페이지 이하일때 --%>
        <%--//검색후 페이징 --%>
        </div>
        <!--//페이징 wrapper -->
        
        <!-- 검색 바 -->
        <div class="searchBar">
            <!-- 검색폼 -->
                <div class="searchRow">
                    <select name="find_field" class="search_box">
                        <option value="item_name" 
                        <c:if test="${find_field == 'item_name' }">
                        ${'selected'}</c:if>>상품명</option>
                        <option value="item_price" 
                        <c:if test="${find_field == 'item_price' }">
                        ${'selected'}</c:if>>가격</option>
                    </select>
                    
                    <input name="find_name" id="find_name" 
                    style="width: 250px;" placeholder="검색어를 입력해주세요."
                    class="search_word" value="${find_name}">
                    <button class="search_btn" id="search_btn" title="검색">
                        <i class="fa fa-search" aria-hidden="true"></i>
                    </button>
                </div>
                </div>
            </form>
        <!--//검색 바 -->
    </div>

<jsp:include page="../include/footer.jsp" />