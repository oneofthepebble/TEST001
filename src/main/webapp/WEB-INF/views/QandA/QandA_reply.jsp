<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/QandA/QandA.css">

<!-- js -->
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/resources/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script src="/js/QandA/QandA.js"></script>


<script type="text/javascript">

	$(function(){
		var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "q_cont",
			sSkinURI: "/resources/smartEditor/SmartEditor2Skin.html",
			fCreator: "createSEditor2",
			htParams: {
				bUseToolbar : true, //툴바 사용 여부
				bUseVerticalResizer : true, //입력창 크기 조절바 사용 여부
				bUseModeChanger : true //모드 탭 사용 여부	
			}
		});
		
		$('#save').click(function () {
			oEditors.getById['q_cont'].exec('UPDATE_CONTENTS_FIELD',[]);
			var cont1= $("#q_cont").val();
			
			/** 스마트 에디터 입력부분 유효성 검증 **/
			if( cont1 == ""  || cont1 == null || cont1 == '&nbsp;' || cont1 == '<p>&nbsp;</p>')  {
	             alert("내용을을 입력해주세요.");
	             oEditors.getById["q_cont"].exec("FOCUS"); //포커싱
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
					<form method="post" action="/QandA_reply_ok" id="frm" onsubmit="return QandA_write_ok();">
					
					<%-- 답변글 히든값 --%>
					<input type="hidden" name="q_ref" value="${q.q_ref }" /><%-- 글그룹 번호 --%>
					<%--원본글과 답변글을 구분하는 번호값이면서 몇번째 답변글인가를 알려줌--%>
					<input type="hidden" name="q_step" value="${q.q_step }" />
					<%--답변글 정렬 순서 --%>
					<input type="hidden" name="q_level" value="${q.q_level }" />
					<%--페이징 히든값 => 책갈피 기능 --%>
					<input type="hidden" name="page" value="${page }" />
					<input type="hidden" name="q_id" value="${user_id }" />
					
					<table id="QandA_write" >
						<tr>
							<td><input name="q_title" id="q_title" size="20" autocomplete="off" 
									value="Re: ${q.q_title }" /> </td>
						</tr>						
						<tr>
							<td><textarea name="q_cont" id="q_cont" cols="150" rows="30"></textarea></td>
						</tr>
						<tr>
							<td align="left">
								<input type="button" class="size" id="save" value="답변" />
								<input type="button" class="size" value="취소" onclick="history.back();" />
							</td>
						</tr>
					</table>
					
					</form>
				</div>
			</article>
		</section>
	</div>
</section>
<!-- contents --> 
</main>



<jsp:include page="../include/footer.jsp" />

