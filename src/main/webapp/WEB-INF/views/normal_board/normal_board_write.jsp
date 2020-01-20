<%--
  Created by IntelliJ IDEA.
  User: BlackJack21
  Date: 2019-12-30
  Time: 오후 4:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp"%>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/normal_board/normal_write.css">
    <link rel="stylesheet" href="/resources/css/reset.css">

    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="/resources/JayEditor_dont_touch/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>

    <title>Normal Board</title>
</head>
<body>
<div class="normal_container">
    <div class="bbs-tit">
        <span class="braket">[</span><strong>일반게시판</strong><span class="braket">]</span>
    </div>
    <form action="normal_list_write_ok" id="frm" method="post">
        <input type="hidden" name="normal_id" value="${user_id}">
        <div class="normal_title">
            <input type="text" placeholder="제 목" name="normal_title">
        </div>
        <div class="normal_cont">
            <textarea rows="20" cols="123" id="normal_cont" name="normal_cont"></textarea>
        </div>

        <div class="normal_button">
            <input type="button" id="save" value="글쓰기">
            <input type="reset" value="취 소" onclick="location='history.back();';">
        </div>
    </form>
</div>



<script type="text/javascript">
    $(function(){
        var oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "normal_cont",
            sSkinURI: "/resources/JayEditor_dont_touch/smartEditor/SmartEditor2Skin.html",
            fCreator: "createSEditor2",
            htParams: {
                bUseToolbar : true, //툴바 사용 여부
                bUseVerticalResizer : true, //입력창 크기 조절바 사용 여부
                bUseModeChanger : true //모드 탭 사용 여부
            }
        });

        $('#save').click(function () {
            oEditors.getById['normal_cont'].exec('UPDATE_CONTENTS_FIELD',[]);
            var cont1= $("#normal_cont").val();

            //재명이가 추가한거임
            var title=$('#normal_title').val();

            if(title==''){
                alert('제목을 입력해주세요');
                $('#normal_title').focus();
                return false;
            }
            //재명이가 추가한거임

            /** 스마트 에디터 입력부분 유효성 검증 **/
            if( cont1 == ""  || cont1 == null || cont1 == '&nbsp;' || cont1 == '<p>&nbsp;</p>')  {
                alert("내용을을 입력해주세요.");
                oEditors.getById["normal_cont"].exec("FOCUS"); //포커싱
                return;
            }
            $('#frm').submit();
        });
    });
</script>

</body>
</html>
<%@ include file="../include/footer.jsp"%>