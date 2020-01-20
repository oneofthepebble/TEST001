<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>

	<!-- Style -->
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/shop/basket_list.css">
    
    <!-- JavaScript Libraries -->
    <script src="/js/jquery.js"></script>


	<div class="container">
			<div class="basket_title">
				<span>${basket_id}</span>님의 장바구니 목록 
			</div>
			
          	<form method="post" onsubmit="return pay_check();">
          	<input type="hidden" name="page" value="${page}" />
			<div class="basket_list">
				<!-- 장바구니 목록 테이블 부분-->
                <div class="basket_table">
                	<p class="basket_head">
                		<span style="width: 7%;">상품 번호</span>
                		<span style="width: 15%;"></span>
                		<span style="width: 30%;">상품명</span>
                		<span style="width: 15%;">단가</span>
                		<span style="width: 5%;">수량</span>
                		<span style="width: 15%;">금액</span>
                		<span style="width: 5%;"></span>
                	</p>
                	<%-- 장바구니 테이블 시작 --%>
                	<c:choose>
                		<c:when test="${map.count == 0}"><%-- 장바구니 정보가 없을 때 --%>
                			<div class="basket_body">
								<div class="column table_none">
									장바구니에 상품이 없습니다.
								</div>
							</div>
						</c:when>

						<c:otherwise>
	                    <c:forEach var="cart" items="${map.list}">
						<div class="basket_body">
		                    	<div class="column table_no">${cart.product_no}</div>
		                    	<div class="column table_img">
		                    		<a href="shop_cont?state=cont&item_no=${cart.product_no}&page=${cart.basket_page}">
		                    			<img src="/resources/photo_upload${cart.product_img}"/>
		                    		</a>
	                    		</div>
		                    	<div class="column table_title">
		                    		<a href="shop_cont?state=cont&item_no=${cart.product_no}&page=${cart.basket_page}">
		                    			${cart.product_name}
	                    			</a>
		                    	</div>
		                    	<div class="column table_name">
		                    		\ <fmt:formatNumber pattern="###,###,###" value="${cart.price}" />
		                    	</div>
		                    	<div class="column table_reco">
		                    	<select name="basket_count" class="basket_count"
		               			onchange="editBasket(${cart.product_no},${cart.stockCount},this.options[this.selectedIndex].text)">
				                   <option value="1" <c:if test="${cart.basket_count == 1}">selected</c:if>>1</option>
				                   <option value="2" <c:if test="${cart.basket_count == 2}">selected</c:if>>2</option>
				                   <option value="3" <c:if test="${cart.basket_count == 3}">selected</c:if>>3</option>
				                   <option value="4" <c:if test="${cart.basket_count == 4}">selected</c:if>>4</option>
				                   <option value="5" <c:if test="${cart.basket_count == 5}">selected</c:if>>5</option>
		               			</select>
			                    </div>
			                    <div class="column table_view">
			                    	\ <fmt:formatNumber pattern="###,###,###" value="${cart.sumPrice}" />
		                    	</div> 
			                    <div class="column table_del">
			                    	<button class="basket_del" onclick="javascript: form.action='basket_del?basket_no=${cart.basket_no}';">삭제</button>
			                    </div>
		                    </div>
		                    </c:forEach>
	                    </c:otherwise>   
	                </c:choose>
                <%--//장바구니 테이블 시작 --%>
	                </div>
                <!--//장바구니 목록 테이블 부분-->
               	</div><%-- basket list --%>
               	
				<div class="basket_info">
					<div class="item_price">
						장바구니 금액 합계 : \ <fmt:formatNumber pattern="###,###,###" value="${map.sumMoney}" />
					</div>
					<div class="deli_price">
						배송료 :  \ <fmt:formatNumber pattern="###,###,###" value="${map.fee}" />
					</div>
					<div class="info_line"></div>
					<div class="all_price">
						전체 주문 금액 :  \ <fmt:formatNumber pattern="###,###,###" value="${map.allSum}" />
					</div>
				</div>
				<div class="basket_buy">
					
					<button id="basketBuy_btn" onclick="javascript: form.action='pay?page=${page}';" >구매</button>
					<button type="button" id="basketList_btn" onclick="location.href='total_shop?page=${page}&find_field=item_name&find_name=';">상품목록</button>
					<%-- 상품목록 버튼의 ${page}값은 바로 이전 상품을 담고 오는 것이기 때문에 문제가 없다. 이전 상품이 있던 페이지로 돌아가는 것이기 때문. --%>
				</div>
               	
			</form>
            </div><%-- container --%>
    <script>
	//장바구니 테이블 목록 구현(취소)
    	//var basket_id="<c:out value='${basket_id}'/>";//유저 아이디
    	//javascript 로 변수 선언할때 변수값을 다이렉트로 지정해주면 is not defined 에러발생.
    	/** select 박스 선택에 따른 상품 정보 수정 **/
    	function editBasket(productNo,stockCount,basket_count) {//3개의 값을 가져옴.
    		//1. 상품 번호 , 2. 선택한 상품 수량
    		
    		//var select = document.getElementsByName("basket_count");
    		//var selectedCount = parseInt(select.options[select.selectedIndex].value);
    		
    		var basket_count = parseInt(basket_count);
    		var stockCount = parseInt(stockCount);
    		
    		//재고수량 확인(재고보다 적으면 출력)
    		if(basket_count > stockCount) {
    			alert('재고가 부족합니다. 보다 적은 수량을 선택해주세요.');
    			basket_count = 1;
    			editBasket();
    		}

    		$.ajax({
				type : 'put',
				url : '/shop/editBasket/',
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "PUT"
				},
				data : JSON.stringify({
					product_no : productNo,
					basket_count : basket_count
				}),
				dataType : "text",
				success : function(data) {
					if(data == 'SUCCESS') {
						location.reload();
					}//if
				}
			});
		}//editBasket()
		
		function pay_check() {
			var count = ${map.count};
			
			if(count == 0) {
				alert('장바구니가 비어있습니다. 장바구니에 상품을 담아주세요.');
				return false;
			}
		}//pay_check()
    </script>
    
<jsp:include page="../include/footer.jsp" />














