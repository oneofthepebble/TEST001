<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/css/shop/shop_write.css" />
<link rel="stylesheet" href="/css/reset.css" />

<!-- js -->
<script src="/js/jquery.js"></script>
<script src="/resources/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script src="/js/shop/shop.js"></script>

<!-- java script -->
<script type="text/javascript">

	$(function(){
		var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "item_cont",
			sSkinURI: "/resources/smartEditor/SmartEditor2Skin.html",
			fCreator: "createSEditor2",
			htParams: {
				bUseToolbar : true, //툴바 사용 여부
				bUseVerticalResizer : true, //입력창 크기 조절바 사용 여부
				bUseModeChanger : true //모드 탭 사용 여부 -> 나중에 false처리	
			}
		});
		
		$('#save').click(function () {
			oEditors.getById['item_cont'].exec('UPDATE_CONTENTS_FIELD',[]);
			var cont1= $("#item_cont").val();
			
			/** 스마트 에디터 입력부분 유효성 검증 **/
			if( cont1 == ""  || cont1 == null || cont1 == '&nbsp;' || cont1 == '<p>&nbsp;</p>')  {
	             alert("상품설명을 반드시 입력해주세요.");
	             oEditors.getById["item_cont"].exec("FOCUS"); //포커싱
	             return;
	        }
			$('#frm').submit();
		});
	});
</script>

<div class="write_main">
	<div class="write_text">
		<span>관리자 Shop 컨텐츠 수정</span>
		
		<form method="post" action="/shop_edit_ok" id="frm" 
		onsubmit="return shop_edit_check();" enctype="multipart/form-data">
			<%-- 히든 값 --%>
		  <input type="hidden" name="item_no" value="${s.item_no}" />
		  <input type="hidden" name="page" value="${page}" />
		
			<table>
				<tr>
					<th>상품명</th>
					<td>
					<input name="item_name" id="item_name" value="${s.item_name}"/>
					</td>
				</tr>
				<tr>
					<th>세부설명</th>
					<td>
					<input name="item_sub" id="item_sub" value="${s.item_sub}"/>
					</td>
				</tr>
				<tr>
					<th>가격</th>
					<td>
					<input name="item_price" id="item_price" 
					onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' 
					 style='ime-mode:disabled;' value="${s.item_price}" />
					</td>
				</tr>
				<tr>
					<th>상품 재고</th>
					<td>
						<input type="text" name="item_stockCount" id="item_stockCount"
					 onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' 
					 style='ime-mode:disabled;' value="${s.item_stockCount}"/>
					 </td>
				</tr>
				<tr>
					<th>상품 설명</th>
					<td>
						<textarea name="item_cont" id="item_cont" cols="100" rows="20">
						${s.item_cont}
						</textarea>
					</td>
				</tr>
				<tr>
					<th>상품 이미지</th>
					<td>
						<input type="file" name="item_img" id="item_img" readonly/>
					</td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2">
						<input type="button" id="save" class="btn" value="수정" />
						<!-- submit 두번돌지 않게 유의 -->
						<input type="reset" value="취소" class="btn"
						onclick="history.back();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />




