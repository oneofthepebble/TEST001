<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>

    <!-- Style -->
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/shop/shop_cont.css">
    <link rel="stylesheet" href="/css/main/font-awesome.css">
    
    <!-- JavaScript Libraries -->
    <script src="/js/shop/zoom.js"></script>
    <script src="/js/jquery.js"></script>
    <script src="/js/shop/shop.js"></script>
    
<script>

	/* 총가격 계산 후 span에 출력 */
	function buyPrice(){
		var select = document.getElementById("basket_count");
		var selectedCount = parseInt(select.options[select.selectedIndex].value);
		var price = ${s.item_price};
		var sum = price * selectedCount;
		
		$('.itemPriceSum').html('총 합계금액 : ￦ '+sum);
	};
</script>


	<div class="container">
        <!-- 상품 상세 영역 -->
        <div class="item_main">
            <div class="itemImg">
                <img src="/resources/photo_upload${s.item_img}" class="target" data-zoom="2" />
                <!-- data-zoom 으로 돋보기 크기 조절 -->
            </div>

            <div class="itemContBox">
                <div class="itemContName">
                    <span class="contName">${s.item_name}</span>
                </div>

                <div class="itemContInfo">
                    <span class="contInfo">${s.item_sub}</span>
                </div>

                <div class="itemContPrice">
                    <span class="contPrice">
                    	가격 : \ <fmt:formatNumber pattern="###,###,###" value="${s.item_price}" />
                    </span>
                </div>
                
                <div class="itemContReco">
                	<c:if test="${checkCount == 1}">
                       	<span class="contPrice">상품 재고 : <fmt:formatNumber pattern="###,###,###" value="${s.item_stockCount}" /> 개</span>
                       
                    </c:if>
                    <%-- <span class="contReco">이 상품이 좋아요! : ${s.item_likeCount}</span> --%>
                </div>
                <form method="post" name="itemBuy">
                <input type="hidden" name="product_no" value="${s.item_no}" />
                <input type="hidden" name="stockCount" value="${s.item_stockCount}"/>
                <input type="hidden" name="page" value="${page}" />
                
                <div class="itemCount">
                	<span class="contCount" >수량 :  </span>
                    <select name="basket_count" id="basket_count"
                    	onchange="buyPrice()">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                    <c:if test="${s.item_stockCount == '0'}">
                    	<span class="stockZero">이 상품의 재고가 없습니다. 문의해주세요!</span>
                    </c:if>
                    <c:if test="${s.item_stockCount != '0'}">
                    	<div class="itemPriceSum"></div>
	                    <div class="itemBuy">
	                    
	                    	<%-- 하나의 form에서 action을 2개로 나눔. 장바구니쪽은 ajax 처리  --%>
	                    	<c:if test="${checkCount == 1 || checkCount ==2}">
			                    <button type="button" id="basket_button">장바구니에 담기</button>
	                    	</c:if>
		                    <button id="buy_button" 
		                    onclick="javascript: form.action='pay_direct_go';">구매</button>
	                    </div>
	                    <div class="basketAsk">
	                    	<span>장바구니에 상품이 담겼습니다.</span>
	                    	<button type="button" id="basketCancel"><i class="fa fa-window-close-o" aria-hidden="true"></i></button>
	                    	<button id="basket_list_button" 
	                    	onclick="javascript: form.action='basket_list_go';">장바구니로 가기</button>
	                    </div>
                    </c:if>
               	</div>
               	</form>
                
                <div class="itemBuyAdminBox">
                    <div class="adminEditDel">
                    	<c:if test="${checkCount == 1}">
		                    <button type="button" 
		                    onclick="location='shop_cont?state=edit&item_no=${s.item_no}&page=${page}';">
		                    	수정하기</button>
                    	</c:if>
	                    	
	                    <form method="get" action="/shop_del"
	                    	 onsubmit="return shop_del_check();" class="del_form">
						<%-- 히든 값 --%>
						<input type="hidden" name="item_no" value="${s.item_no}" /> 
						<input type="hidden" name="page" value="${page}" />
						<input type="hidden" name="item_img" value="${s.item_img}" />
						
						<c:if test="${checkCount == 1}">
							<button id="del_button">삭제</button>
						</c:if>
	                    </form>
                    </div>
                </div>
                </div>
            </div>
        <!--//상품 상세 영역 -->
        
        <!-- 상품 상세 설명 영역 -->
            <div class="item_Cont">
           		${s.item_cont}
            </div>
        </div>
        <!--//상품 상세 설명 영역 -->
<script>

/* 장바구니 테이블에 저장(ajax) */
$('#basket_button').on('click',function(){
	var product_no = ${s.item_no}; //상품번호
	var select = document.getElementById("basket_count");//select값
	var selectedCount = parseInt(select.options[select.selectedIndex].value);
	//select 값 숫자로 변환. 선택한 상품수량
	var stockCount = ${s.item_stockCount};//재고수량
	var page = ${page};//해당 상품 page
	
	
	//재고수량 확인(재고보다 적으면 출력)
	if(selectedCount > stockCount) {
		alert('재고가 부족합니다. 보다 적은 수량을 선택해주세요.');
		$("#basket_count").val("1").prop("selected", true);//select 영역 변경
		buyPrice();//가격 계산 함수 재호출
		return false;
	}

	$.ajax({//jQuery ajax
		type : 'post',
		url : '/shop/basket_add',//매핑주소
		headers : {
			"Content-type" : "application/json",
			"X-HTTP-Method-Override" : "POST" //HTTP코드 맨머리 앞에 
			//추가적인 정보지정
		},
		dataType : 'text',//문자열
		data : JSON.stringify({//내용이 json
			product_no : product_no,//상품 번호
			basket_count : selectedCount,//선택수량
			basket_page : page//해당 상품 페이지 값
		}),
		success : function(data){//장바구니저장 성공시 
			//SUCCESS 문자열 반환
			if(data == 'SUCCESS') {
				$('.basketAsk').show();
				setTimeout(function(){// 초 동안만 알림창을 유지
					$('.basketAsk').hide();
				},5000);
			}//if
		}//function(data)
	});
});
/* 바로 구매 버튼 눌렀을 때 */
$('#buy_button').on('click',function(){
	var select = document.getElementById("basket_count");//select값
	var selectedCount = parseInt(select.options[select.selectedIndex].value);
	//select 값 숫자로 변환. 선택한 상품수량
	var stockCount = ${s.item_stockCount};//재고수량
	
	//재고수량 확인(재고보다 적으면 출력)
	if(selectedCount > stockCount) {
		alert('재고가 부족합니다. 보다 적은 수량을 선택해주세요.');
		$("#basket_count").val("1").prop("selected", true);//select 영역 변경
		buyPrice();//가격 계산 함수 재호출
		return false;
	}
});

/* 장바구니 알림창 닫기 */
$('#basketCancel').on('click',function(){
	$('.basketAsk').hide();
});

</script>

<jsp:include page="../include/footer.jsp" />