<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/dog_board/dog.css">

<!-- js -->
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="/resources/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script src="/js/dog_board/dog.js"></script>

<script type="text/javascript">

$(function(){
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "dog_cont",
		sSkinURI: "/resources/smartEditor/SmartEditor2Skin.html",
		fCreator: "createSEditor2",
		htParams: {
			bUseToolbar : true, //툴바 사용 여부
			bUseVerticalResizer : true, //입력창 크기 조절바 사용 여부
			bUseModeChanger : true //모드 탭 사용 여부	
		}
	});
	
	$('#save').click(function () {
		oEditors.getById['dog_cont'].exec('UPDATE_CONTENTS_FIELD',[]);
		var cont1= $("#dog_cont").val();
		
		/** 스마트 에디터 입력부분 유효성 검증 **/
		if( cont1 == ""  || cont1 == null || cont1 == '&nbsp;' || cont1 == '<p>&nbsp;</p>')  {
             alert("내용을을 입력해주세요.");
             oEditors.getById["dog_cont"].exec("FOCUS"); //포커싱
             return;
        }
		$('#frm').submit();
	});
});
</script>

<!-- 본문 내용 -->
<main>
<section id="contents">
	<div class="container">
		<section id="cont">
			<article class="column col1">
				<div class="main">
					<form method="post" action="/dog_edit_ok" id="frm" onsubmit="return edit_check();" 
					enctype="multipart/form-data">
					
					<%-- 히든값 --%>
					<input type="hidden" name="dog_no" value="${d.dog_no }" />
					<input type="hidden" name="page" value="${page }" />
					
					<table id="dog_write" width="100%">
						<tr>
							<td><input name="dog_title" id="dog_title" size="20" value="${d.dog_title }" /> </td>
						</tr>
						<tr>
							<td><textarea name="dog_cont" id="dog_cont" cols="170" rows="30">
								${d.dog_cont }</textarea>
							</td>
						</tr>
						<tr>
    						<td><input type="file" name="dog_file" id="dog_file" />
    						<br/>${d.dog_file }
    						</td>
						</tr>
					</table>
					
					<div id="dog_menu">
						<input type="button" id="save" value="수정" />
						<input type="button" value="취소" onclick="history.back();" />
					</div>
					
					</form>
				</div>
			</article>
		</section>
	</div>
</section>
<!-- contents --> 
</main>


<jsp:include page="../include/footer.jsp" />

