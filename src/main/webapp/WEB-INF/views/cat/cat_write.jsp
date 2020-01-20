<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/cat_board/cat.css">

<!-- js -->
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/resources/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script src="/js/cat_board/cat.js"></script>

<script type="text/javascript">

	$(function(){
		var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "cat_cont",
			sSkinURI: "/resources/smartEditor/SmartEditor2Skin.html",
			fCreator: "createSEditor2",
			htParams: {
				bUseToolbar : true, //툴바 사용 여부
				bUseVerticalResizer : true, //입력창 크기 조절바 사용 여부
				bUseModeChanger : true //모드 탭 사용 여부	
			}
		});
		
		$('#save').click(function () {
			oEditors.getById['cat_cont'].exec('UPDATE_CONTENTS_FIELD',[]);
			var cont1= $("#cat_cont").val();
			
			/** 스마트 에디터 입력부분 유효성 검증 **/
			if( cont1 == ""  || cont1 == null || cont1 == '&nbsp;' || cont1 == '<p>&nbsp;</p>')  {
	             alert("내용을을 입력해주세요.");
	             oEditors.getById["cat_cont"].exec("FOCUS"); //포커싱
	             return;
	        }
			$('#frm').submit();
		});
	});
</script>

<!-- 본문 내용 -->
<main>
<section id="contents">
			<article class="column col1">
				<div class="main">
					<form method="post" action="/cat_write_ok" id="frm" onsubmit="return write_check();"
					enctype="multipart/form-data">
					
					<table id="cat_write" >
						<tr>
							<td><input name="cat_title" id="cat_title" size="20" autocomplete="off" placeholder="고양이 종류" /> </td>
						</tr>
						<tr>
							<td><textarea name="cat_cont" id="cat_cont" cols="170" rows="30"></textarea></td>
						</tr>
						<tr>
    						<td><input type="file" name="cat_file" id="cat_file" /></td>
						</tr>
						<tr>
							<th align="left">
								<input type="button" class="size" id="save" value="저장" />
								<input type="button" class="size" value="취소" onclick="history.back();" />
							</th>
						</tr>
					</table>
					
					</form>
				</div>
			</article>
		</section>
<!-- contents --> 
</main>



<jsp:include page="../include/footer.jsp" />

