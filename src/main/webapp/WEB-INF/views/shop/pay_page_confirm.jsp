<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>
<!-- Style -->
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/shop/pay_page_confirm.css">
   

	<div class="container">
		<div class="pay_ok">
			<div class="message">
				결제가 완료 되었습니다. <br/>
				구매해주셔서 감사합니다.
			</div>
			<div class="pay_ok_btnBox">
				<button type="button" id="itemList" onclick="location.href='total_shop?page=${page}&find_field=item_name&find_name=';">상품 목록</button>
				<button type="button" id="payList" onclick="location.href='pay_list_go?page=${page}';">주문 내역</button>
			</div>
		</div>
	</div>
	
<jsp:include page="../include/footer.jsp" />








