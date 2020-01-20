<%--
  Created by IntelliJ IDEA.
  User: BlackJack21
  Date: 2019-12-23
  Time: 오후 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../include/header.jsp"%>
<html>
<head>
    <title>백앤드 상세페이지</title>
</head>
<link href="/resources/css/reset.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/board_list/back_end_list.css">

<script src="/js/board_cont/board_cont.js"></script>

<body>
<div id="container">
    <div class="board_title"><strong>[ 분  양 ]</strong></div>
    <form method="get" action="user_board_list">

        <%--리스트가 있으면--%>
        <c:if test="${!empty blist}">
            <c:forEach var="b" items="${blist}">
                <div class="wrap">
                    <div class="back_end_cont">
                        <a href="cont?id=${b.back_end_list_id}&title=${b.back_end_list_title}&no=${b.back_end_list_no}&page=${page}">
                            <h3>

                                <c:if test="${fn:length(b.back_end_list_title)>10}">
                                    <%-- 제목 문자열 길이가 16자를 초과하면 실행 --%>
                                    ${fn:substring(b.back_end_list_title,0,10)}...
                                    <%--제목을 16자까지만 출력하고 나머지는 ... 표시 --%>
                                </c:if>
                                <c:if  test="${fn:length(b.back_end_list_title)<10}">
                                    ${b.back_end_list_title}
                                </c:if>
                            </h3>

                            <br>
                            <span>${b.back_end_list_id}</span>&nbsp;&nbsp;<span>댓글</span>&nbsp;&nbsp;<span>날짜:${b.back_end_list_date}</span>
                        </a>
                    </div>
                    <div class="back_end_cont_image">
                        <a href="cont?id=${b.back_end_list_id}&title=${b.back_end_list_title}&no=${b.back_end_list_no}&page=${page}">
                            <img class="image" src="/resources/photo_upload/${b.back_end_list_img}">
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:if>

        <%--리스트가 없으면--%>
        <c:if test="${empty blist}">
            <tr>
                <th colspan="5"><h1>목록이 없습니다!</h1></th>
            </tr>
        </c:if>

        <%--페이징 즉 쪽나누기 추가 --%>
        <div id="bList_paging">
            <%-- 검색전 페이징 --%>
            <c:if test="${(empty back_end_field) && (empty back_end_title)}">
                <c:if test="${page<=1}">
                    [이전]&nbsp;
                </c:if>
                <c:if test="${page>1}">
                    <a href="user_board_list?page=${page-1}">[이전]</a>&nbsp;
                </c:if>

                <%--현재 쪽번호 출력--%>
                <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                    <c:if test="${a == page}">
                        <%--현재 페이지가 선택되었다면--%>
                        <${a}>
                    </c:if>
                    <c:if test="${a != page}">
                        <%--현재 페이지가 선택되지 않았다면 --%>
                        <a href="user_board_list?page=${a}">[${a}]</a>&nbsp;
                    </c:if>
                </c:forEach>

                <c:if test="${page >= maxpage}">
                    [다음]
                </c:if>
                <c:if test="${page<maxpage}">
                    <a href="user_board_list?page=${page+1}">[다음]</a>
                </c:if>
            </c:if>
            <%-- 검색전 페이징 --%>

            <%-- 검색후 페이징 --%>
            <c:if test="${(!empty back_end_field) || (!empty back_end_title)}">
                <c:if test="${page<=1}">
                    [이전]&nbsp;
                </c:if>
                <c:if test="${page>1}">
                    <a href="user_board_list?page=${page-1}&back_end_title=${back_end_title}&back_end_field=${back_end_field}">[이전]</a>&nbsp;
                </c:if>

                <%--현재 쪽번호 출력--%>
                <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                    <c:if test="${a == page}">
                        <%--현재 페이지가 선택되었다면--%>
                        <${a}>
                    </c:if>
                    <c:if test="${a != page}">
                        <%--현재 페이지가 선택되지 않았다면 --%>
                        <a href="user_board_list?page=${a}&back_end_title=${back_end_title}&back_end_field=${back_end_field}">
                            [${a}]
                        </a>&nbsp;
                    </c:if>
                </c:forEach>

                <c:if test="${page >= maxpage}">
                    [다음]
                </c:if>

                <c:if test="${page<maxpage}">
                    <a href="user_board_list?page=${page+1}&back_end_title=${back_end_title}&back_end_field=${back_end_field}">
                        [다음]
                    </a>
                </c:if>

            </c:if>
        </div>

        <%-- 검색후 페이징 --%>
       
        <div class="search_div">
        <c:if test="${user_id!=null }">
            <input type="button" value="글쓰기" onclick="location='board_list_write'">
            </c:if>
            <select name="back_end_title">
                <option value="board_title"
                        <c:if test="${back_end_title=='board_title'}">
                            ${'selected'}</c:if>>제목</option>
                <option value="board_cont" <c:if test="${back_end_title=='board_cont'}">
                    ${'selected'}</c:if>>내용</option>
            </select>
            <input name="back_end_field" id="find_name" size="14" value="${back_end_field}">
            <input type="submit" value="검색"/>
        </div>

    </form>
</div>
