/**
 *  shop.js
 */

/** shop 관리자 글쓰기 유효성 검증 **/

//shop 헤더 이미지 변경
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/shop/shop01.jpg); background-position:center; background-size:cover;');
      
  });

/* 게시글 저장 */
function shop_check(){
	if($.trim($("#item_name").val())==""){
		alert("상품명을 입력해주세요.");
		$("#item_name").val("").focus();
		return false;
	}
	if($.trim($("#item_sub").val())==""){
		alert("상품세부설명을 입력해주세요.");
		$("#item_sub").val("").focus();
		return false;
	}
	if($.trim($("#item_price").val())==""){
		alert("상품가격을 입력해주세요.");
		$("#item_price").val("").focus();
		return false;
	}
	if($.trim($("#item_stockCount").val())==""){
		alert("상품재고를 입력해주세요.");
		$("#item_stockCount").val("").focus();
		return false;
	}
	if($.trim($("#item_img").val())==""){
		alert("상품이미지를 추가해주세요.");
		$("#item_img").val("").focus();
		return false;
	}
	alert('상품이 등록되었습니다.');
}

/* 게시글 수정 */
function shop_edit_check(){
	if($.trim($("#item_name").val())==""){
		alert("상품명을 입력해주세요.");
		$("#item_name").val("").focus();
		return false;
	}
	if($.trim($("#item_sub").val())==""){
		alert("상품세부설명을 입력해주세요.");
		$("#item_sub").val("").focus();
		return false;
	}
	if($.trim($("#item_price").val())==""){
		alert("상품가격을 입력해주세요.");
		$("#item_price").val("").focus();
		return false;
	}
	if($.trim($("#item_stockCount").val())==""){
		alert("상품재고를 입력해주세요.");
		$("#item_stockCount").val("").focus();
		return false;
	}
	if($.trim($("#item_img").val())==""){
		alert("상품이미지를 추가해주세요.");
		$("#item_img").val("").focus();
		return false;
	}
	alert('상품이 수정되었습니다.');
};

/* 게시글 삭제 */
$(document).ready(function shop_del_check(){
	$('#del_button').click(function(){
		var result = confirm('상품을 삭제 하시겠습니까?');

		if(result) {
			alert("상품이 삭제 되었습니다.");
		}else {
			return false;
		}
	});
});


/** shop 관리자 글쓰기 상품 가격 부분 숫자만 나오게 처리 **/
function onlyNumber(event){
  event = event || window.event;
  var keyID = (event.which) ? event.which : event.keyCode;
  if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 )
    return;
  else
    return false;
};

function removeChar(event) {
  event = event || window.event;
  var keyID = (event.which) ? event.which : event.keyCode;
  if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 )
    return;
  else
    event.target.value = event.target.value.replace(/[^0-9]/g, "");
};























