`<%--
  Created by IntelliJ IDEA.
  User: BlackJack21
  Date: 2020-01-01
  Time: 오후 4:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>일반 게시판 내용</title>

    <script type="text/javascript" src="/resources/js/normal_board_cont/normal_board_cont.js"></script>

    <link rel="stylesheet" href="/resources/css/normal_board/normal_cont.css">

</head>
<body>
<div class="bbs-table-view">
    <div class="bbs-tit">
        <span class="braket">[</span><strong>일반게시판</strong><span class="braket">]</span>
    </div>
    <table summary="게시글 보기">
        <caption>게시글 보기</caption>
        <thead>
        <tr>
            <th><div class="tb-center">${n.normal_title}<span class="reply_status reply_NONE"></span></div></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="line">
                <div class="cont-sub-des">
                    <div>
                        <span><em>Date :</em> ${n.normal_date}</span>
                    </div>
                    <div>
                        <span><em>I D :</em> ${n.normal_id}</span>
                    </div>
                    <div class="hit"><span><em>Hits :</em> ${n.normal_hit}</span></div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="data-bd-cont">
                    <div id="MS_WritenBySEB">${n.normal_cont}</div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <c:if test="${!empty nlist}">
        <c:forEach var="nl" items="${nlist}">
            <div id="comment_list_0">
                <table summary="코멘트 목록" class="comment-box">
                    <caption>코멘트 목록</caption>
                    <colgroup>
                        <col width="120">
                        <col>
                        <col width="150">
                    </colgroup>
                    <tbody>
                    <tr>

                        <td class="tb-right"><div class="com-name">${nl.normal_id}  번호:${nl.normal_no}</div></td>
                        <td><div>${nl.normal_cont}</div></td>
                        <td class="tb-right">
                            <div class="bbs-link">
                        <span>${nl.normal_date}<br>
                            <c:if test="${nl.normal_id==user_id}">
                                <a href="reply_del_ok?normal_id=${nl.normal_id}&normal_no=${nl.normal_no}&no=${n.normal_no}" onclick="return del_ok();" class="delete">삭제</a>
                            </c:if>
                        </span>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty nlist}">
        <div>목록이없습니다</div>
    </c:if>
    <form>
        <fieldset>
            <legend>코멘트 쓰기</legend>
            <table summary="코멘트 쓰기" class="comment-box">
                <caption>코멘트 쓰기</caption>
                <colgroup>
                    <col width="120">
                    <col>
                    <col width="150">
                </colgroup>
                <tbody>
                <tr>
                    <td colspan="3" class="com-wrt-box">
                        <div>
                            <div class="wrt">
                                <c:if test="${!empty user_id}">
                                    <input type="hidden" id="normal_no" name="normal_no" value="${n.normal_no}">
                                    <input type="hidden" name="normal_title" value="${n.normal_title}">
                                    <input type="hidden" name="normal_date" value="${n.normal_date}">
                                    <input type="hidden" name="normal_hit" value="${n.normal_hit}">
                                    <input type="hidden" name="normal_id2" value="${n.normal_id}">
                                    <input type="hidden" name="normal_cont2" value="${n.normal_cont}">
                                    <label>I D</label><span><input type="text" id="normal_id" name="normal_id" class="MS_input_txt input-style input-style2"  value="${user_id}" readonly></span>
                                    <%--                        <label>PASSWORD</label><span><input type="password" name="cpass" class="MS_input_txt input-style input-style2" onclick="CheckLogin()" onkeypress="CheckLogin()" placeholder="패스워드"> </span>--%>
                                </c:if>
                            </div>
                            <c:if test="${!empty user_id}">
                                <div class="wrt"><textarea id="normal_cont" name="normal_cont"  placeholder="내용"></textarea><button onclick="return reply_ok();"> <img src="http://www.astonwood.co.kr/images/d3/modern_simple/btn/btn_h57_write.gif" alt="글쓰기" ></button></div>
                            </c:if>

                            <c:if test="${empty user_id}">
                                <div class="wrt"><a href="login">로그인 후 이용해주세요</a></div>
                            </c:if>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

        </fieldset>
    </form>
    <div class="view-link">
        <dl class="bbs-link">
            <dt></dt>
            <dl class="bbs-link con-link">
                <dt></dt>
                <dd>
                    <c:if test="${n.normal_id==user_id || user_id=='admin'}">
                    <a href="board_cont_update?normal_no=${n.normal_no}" class="none">수정</a>
                    <a class="write" href="normal_list_write_del_ok?normal_ref=${n.normal_no}" onclick="return del_ok();">삭제</a>
                    </c:if>
            </dl>
            <dd>
                <c:if test="${n.normal_id==user_id || user_id=='admin'}">
                    <a class="write" href="normal_list_write">글쓰기</a>
                </c:if>
                    <a href="normal_board_list?page=${page}">목록보기</a>
            </dd>
        </dl>


    </div>
</div>
</body>
</html>
<%@include file="../include/footer.jsp"%>`