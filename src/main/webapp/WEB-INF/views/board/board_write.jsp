<%--
  Created by IntelliJ IDEA.
  User: BlackJack21
  Date: 2019-12-24
  Time: 오후 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp"%>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/board_write/board_write.css">
    <link rel="stylesheet" href="/resources/css/reset.css">
    
    <script src="/js/board_cont/board_cont.js"></script>

    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="/resources/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>
    <title>Title</title>
</head>
<body>

<div id="container">
    <form action="board_list_write_ok" enctype="multipart/form-data" method="post" id="frm">
        <div class="cont_div">
            <span><input name="back_end_list_id" value="${user_id}" disabled></span>
            <br>
            <br>
            <input type="text" placeholder="제 목" name="back_end_list_title" id="back_end_list_title">
            <br>
            <textarea rows="20" cols="123" id="back_end_list_cont" name="back_end_list_cont"></textarea>
        </div>

        <div>
            <input type="file" name="back_end_list_img" id="back_end_list_img">
            <br>
            <br>
            <input type="button" id="save" value="저 장">
        </div>
    </form>
</div>

<script type="text/javascript">
    $(function(){
        var oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "back_end_list_cont",
            sSkinURI: "/resources/smartEditor/SmartEditor2Skin.html",
            fCreator: "createSEditor2",
            htParams: {
                bUseToolbar : true, //툴바 사용 여부
                bUseVerticalResizer : true, //입력창 크기 조절바 사용 여부
                bUseModeChanger : true //모드 탭 사용 여부
            }
        });

        $('#save').click(function () {
            oEditors.getById['back_end_list_cont'].exec('UPDATE_CONTENTS_FIELD',[]);
            var cont1= $("#back_end_list_cont").val();

            //재명이가 추가한거임
            var title=$('#back_end_list_title').val();

            if(title==''){
                alert('제목을 입력해주세요');
                $('#back_end_list_title').focus();
                return false;
            }

            /** 스마트 에디터 입력부분 유효성 검증 **/
            if( cont1 == ""  || cont1 == null || cont1 == '&nbsp;' || cont1 == '<p>&nbsp;</p>')  {
                alert("내용을을 입력해주세요.");
                oEditors.getById["back_end_list_cont"].exec("FOCUS"); //포커싱
                return;
            }
            $('#frm').submit();
        });
    });
</script>
</body>
</html>
