<%--
  Created by IntelliJ IDEA.
  User: BlackJack21
  Date: 2019-12-23
  Time: 오후 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="../include/header.jsp"%>
<html>
<head>
    <%--css--%>
    <link rel="stylesheet" href="/resources/css/reset.css">
    <link rel="stylesheet" href="/resources/css/find_pwd/find_pwd.css">

    <%--js--%>
    <script src="/resources/js/jquery.js"></script>
    <script src="/resources/js/find_pwd/find_pwd.js"></script>

    <title>비밀번호 찾기</title>
</head>
<body>
<form action="find_pwd_check" method="post">
    <div id="container">
    <div class="titlefindPwd">
    	<p>비밀번호 찾기</p>
    </div>
        <div>
            <input class="user_id" id="user_id" name="user_id" type="text" placeholder="아이디" onkeyup="patternid();">
            <br><span class="idlabel" id="idlabel">아이디를 입력해주세요!</span>
        </div>
        <div>
            <input class="user_name" id="user_name" name="user_name" type="text" placeholder="이 름" onkeyup="patterkorean();">
            <br><span class="namelabel" id="namelabel">이름을 입력해주세요!</span>
        </div>
        <div>
            <input class="user_phone" id="user_phone" name="user_phone" type="phone" placeholder="00*-000*-0000" onKeyup="inputPhoneNumber(this);" maxlength="13">
            <input type="hidden" id="phone" name="phone">
            <br><span class="phonelabel" id="phonelabel">연락처를 입력해주세요!</span>
            <br><input class="codesend" id="codesend" type="button" value="전 송" onclick="return phonecodesend();">
        </div>
        <div>
            <input class="user_phone_code" id="user_phone_code" name="user_phone_code"  type="text" placeholder="인증번호" disabled>
            <input type="hidden" id="code">
            <br><span class="codelabel" id="codelabel">코드를 입력해주세요!!</span>
            <br><input class="hiddencode" type="button" value="인 증" onclick="code_check();" disabled>
        </div>
        <div class="loginbutton_div">
            <input class="loginbutton" type="submit" onclick="return find_p();" value="비밀번호 찾기">
            <br><span><a href="login">로그인</a></span>&nbsp;
            <span><a href="find_id">아이디 찾기</a></span>&nbsp;
            <span><a href="/memberjoin">회원가입</a></span>
        </div>
    </div>
</form>
</body>
</html>
