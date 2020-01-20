<%--
  Created by IntelliJ IDEA.
  User: BlackJack21
  Date: 2019-12-30
  Time: 오후 1:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp"%>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/normal_board/normal_board.css">
    
    <script type="text/javascript" src="/resources/js/normal_board_cont/normal_board_cont.js"></script>
    <title></title>
</head>
<body>
<div class="page-body">
    <div class="bbs-tit">
        <span class="braket">[</span>
        <strong>일반게시판</strong>
        <span class="braket">]</span>
    </div>
    <dl class="bbs-link bbs-link-top">
        <dt class=""></dt>
        <dd>
            <a class="write" href="normal_list_write">글쓰기</a>
        </dd>
    </dl>
    <div class="bbs-sch">
        <form action="normal_board_list" name="normal_form">
            <!-- .검색 폼시작 -->
            <fieldset>
                <legend>게시판 검색 폼</legend>
                <label>
                    <input type="radio" name="title" value="board_id" class="MS_input_checkbox">                                    이름
                </label>
                <label>
                    <input type="radio" name="title" value="board_title" checked="checked" class="MS_input_checkbox">                                    제목
                </label>
                <label>
                    <input type="radio" name="title" value="board_cont" class="MS_input_checkbox">                                    내용
                </label>
                <span class="key-wrap">
                                    <input type="text" name="field" value="" class="MS_input_txt"><a href="javascript:document.normal_form.submit();"><img src="http://www.astonwood.co.kr/images/d3/modern_simple/btn/btn_bbs_sch.gif" alt="검색" title="검색"></a>
                                </span>
            </fieldset>
        </form><!-- .검색 폼 끝 -->
    </div><!-- .bbs-sch -->
    <div class="bbs-table-list">
        <table summary="No, content,Name,Data,Hits">
            <caption>일반게시판 게시글</caption>
            <colgroup>
                <col width="50">
                <col width="30">
                <col width="*">
                <col width="110">
                <col width="90">
                <col width="60">
            </colgroup>
            <thead>
            <tr>
                <th scope="col"><div class="tb-center">No.</div></th>
                <th scope="col"><div class="tb-center">&nbsp;</div></th>
                <th scope="col"><div class="tb-center">Content</div></th>
                <th scope="col"><div class="tb-center">ID</div></th>
                <th scope="col"><div class="tb-center">Date</div></th>
                <th scope="col"><div class="tb-center">Hits</div></th>
            </tr>
            </thead>

            <tbody>
            <!--. 공지사항 리스트 끝! -->

            <!--. 게시글 리스트 시작(한페이지당 게시글 수) -->
            <c:if test="${!empty nlist}">
                <c:forEach var="b" items="${nlist}">
                    <tr>

                        <td><div class="tb-center">${b.normal_no}</div></td>
                        <td><div class="tb-left"></div></td>
                        <!-- .product image 관련 -->
                        <td>
                            <div class="tb-left">

                                <!--. 스팸글 처리 -->
                                <a href="normal_board_list_cont?normal_no=${b.normal_no}">${b.normal_title}</a>
                            </div>
                        </td>
                        <td>
                            <div class="tb-center">
                                    ${b.normal_id}
                            </div>
                        </td>
                        <td><div class="tb-center">${b.normal_date}</div></td>
                        <td><div class="tb-center">${b.normal_hit}</div></td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty nlist}">
                <tr>
                    <th colspan="6"><strong> 목록이없습니다 </strong></th>
                </tr>
            </c:if>
            <!--. 게시글 리스트 끝 -->

            </tbody>


        </table>
    </div>
    <dl class="bbs-link bbs-link-btm">
        <dd>
            <a class="write" href="normal_list_write">글쓰기</a>
        </dd>
    </dl>
    <div class="paging">
        <ol class="paging">
            <li><strong> <%--페이징 즉 쪽나누기 추가 --%>
        <div id="bList_paging">
            <%-- 검색전 페이징 --%>
            <c:if test="${(empty field) && (empty title)}">
                <c:if test="${page<=1}">
                    [이전]&nbsp;
                </c:if>
                <c:if test="${page>1}">
                    <a href="normal_board_list?page=${page-1}">[이전]</a>&nbsp;
                </c:if>

                <%--현재 쪽번호 출력--%>
                <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                    <c:if test="${a == page}">
                        <%--현재 페이지가 선택되었다면--%>
                        <${a}>
                    </c:if>
                    <c:if test="${a != page}">
                        <%--현재 페이지가 선택되지 않았다면 --%>
                        <a href="normal_board_list?page=${a}">[${a}]</a>&nbsp;
                    </c:if>
                </c:forEach>

                <c:if test="${page >= maxpage}">
                    [다음]
                </c:if>
                <c:if test="${page<maxpage}">
                    <a href="normal_board_list?page=${page+1}">[다음]</a>
                </c:if>
            </c:if>
            <%-- 검색전 페이징 --%>

            <%-- 검색후 페이징 --%>
            <c:if test="${(!empty field) || (!empty title)}">
                <c:if test="${page<=1}">
                    [이전]&nbsp;
                </c:if>
                <c:if test="${page>1}">
                    <a
                            href="normal_board_list?page=${page-1}&title=${title}&field=${field}">[이전]</a>&nbsp;
                </c:if>

                <%--현재 쪽번호 출력--%>
                <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                    <c:if test="${a == page}">
                        <%--현재 페이지가 선택되었다면--%>
                        <${a}>
                    </c:if>
                    <c:if test="${a != page}">
                        <%--현재 페이지가 선택되지 않았다면 --%>
                        <a href="normal_board_list?page=${a}&title=${title}&field=${field}">
                            [${a}]
                        </a>&nbsp;
                    </c:if>
                </c:forEach>

                <c:if test="${page >= maxpage}">
                    [다음]
                </c:if>

                <c:if test="${page<maxpage}">
                    <a href="normal_board_list?page=${page+1}&title=${title}&field=${field}">
                        [다음]
                    </a>
                </c:if>

            </c:if></div>
            </strong>
            </li>
        </ol>
    </div>
</div>
</body>
</html>
<%@include file="../include/footer.jsp"%>