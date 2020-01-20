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
    <link rel="stylesheet" href="/resources/css/reset.css">
    <link rel="stylesheet" href="/resources/css/login/login.css">
    
<script>
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/login_wallpaper.jpg); background-position:top; background-size:cover;');
});
</script>   
 
    <title>로그인</title>
</head>
<body>
<form action="login_ok" method="post">
    <div id="container">
    <div class="titleLogin">
    	<p>Login</p>
    </div>
        <div>
            <input type="text" placeholder="아이디" name="user_id">
            <br><span class="idlabel">아이디가 없습니다!</span>
        </div>
        <div>
            <input type="password" placeholder="비밀번호" name="user_pwd">
            <br><span class="pwdlabel">아이디가 없습니다!</span>
        </div>
        <div class="loginbutton_div">
            <input class="loginbutton" type="submit" value="로그인">
            <br><span><a href="find_id">아이디 찾기</a></span>&nbsp;
            <span><a href="find_pwd">비밀번호 찾기</a></span>&nbsp;
            <span><a href="/memberjoin">회원가입</a></span>
        </div>
    </div>
</form>
</body>
</html>
