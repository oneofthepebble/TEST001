<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/QandA/QandA.css">

<!-- js -->
<script src="/js/QandA/QandA.js"></script>

<script>
	
</script>

<!-- 본문 내용 -->
<main>
<form method="get" action="QandA_list" >
    <section id="contents">
        <div class="container">
            <section id="cont">
                <article class="column col1">
                    <div class="main">
                        <div class="qboard_name">
							[Q&A]
						</div>

						<div class="qList_count">
						총 게시물 수: ${totalcount}
						</div>
                            <table id="qList_t">
                                <tr>
                                    <th width="6%" height="26">No.</th>
                                    <th width="40%">제목</th>
                                    <th width="20%">작성자</th>
                                    <th width="20%">작성일</th>
                                    <th width="20%">조회수</th>
                                </tr>

                                <c:if test="${!empty qlist}">
                                    <c:forEach var="q" items="${qlist}">
                                        <tr>
                                            <td align="center">
                                                <c:if test="${q.q_step ==0}"><%--원본글일때만 실행 --%>
                                                    ${q.q_ref}
                                                </c:if>
                                            </td>
                                            <td class="title_css">
                                                <c:if test="${q.q_step != 0}"><%--답변글일때 실행 --%>
                                                    <c:forEach begin="1" end="${q.q_step}" step="1">
                                                        &nbsp;<%--답변글 들여쓰기 --%>
                                                    </c:forEach>
                                                    <div class="OneLine">
                                                    <img class="reply" src="/resources/img/QandA/reply2.png" />
                                                    <%--답변글 이미지 --%>
                                                </c:if>
                                                <a  href="QandA_cont?q_no=${q.q_no}&state=cont&page=${page}">${q.q_title}</a>
                                            </div>
                                            </td>
                                            <td align="center">${q.q_id }</td>
                                            <td align="center">${q.q_date}</td>
                                            <td align="center">${q.q_hit}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${empty qlist}">
                                    <tr>
                                        <th colspan="5">자료실 목록이 없습니다.</th>
                                    </tr>
                                </c:if>
                            </table>

                            <%--페이징(쪽나누기)--%>
                            <div id="qList_paging">
                                <%--검색전 페이징 --%>
                                <c:if test="${(empty find_field)&&(empty find_name)}">
                                    <c:if test="${page <=1}">
                                        &lt;&nbsp;
                                    </c:if>
                                    <c:if test="${page >1}">
                                        <a href="QandA_list?page=${page-1}">&lt;</a>&nbsp;
                                    </c:if>

                                    <%--쪽번호 출력부분 --%>
                                    <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                                        <c:if test="${a == page}"><span style="color: blue;">${a}&nbsp;</span>
                                        </c:if>

                                        <c:if test="${a != page}">
                                            <a href="QandA_list?page=${a}">${a}</a>&nbsp;
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${page>=maxpage}">&gt;</c:if>
                                    <c:if test="${page<maxpage}">
                                        <a href="QandA_list?page=${page+1}">&gt;</a>
                                    </c:if>
                                </c:if>

                                <%--검색후 페이징 --%>
                                <c:if test="${(!empty find_field) || (!empty find_name)}">
                                    <c:if test="${page <=1}">
                                        &lt;&nbsp;
                                    </c:if>
                                    <c:if test="${page >1}">
                                        <a
                                            href="QandA_list?page=${page-1}&find_field=${find_field}&find_name=${find_name}">
                                            &lt;</a>&nbsp;
                                    </c:if>

                                    <%--쪽번호 출력부분 --%>
                                    <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                                        <c:if test="${a == page}">
                                            <span style="color: blue;">${a}&nbsp;</span>
                                        </c:if>

                                        <c:if test="${a != page}">
                                            <a
                                                href="QandA_list?page=${a}&find_field=${find_field}&find_name=${find_name}">
                                                ${a}</a>&nbsp;
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${page>=maxpage}">&gt;
                                    </c:if>
                                    <c:if test="${page<maxpage}">
                                        <a
                                            href="QandA_list?page=${page+1}&find_field=${find_field}&find_name=${find_name}">
                                            &gt;</a>
                                    </c:if>
                                </c:if>
                            </div>

                            <%--검색폼 --%>
                            <div id="qFind_wrap">
                                <select name="find_field" class="select">
                                    <option value="q_title" <c:if test="${find_field == 'q_title'}">
                                        ${'selected'}</c:if>>제목</option>
                                    <option value="cat_cont" <c:if test="${find_field == 'q_cont'}">
                                        ${'selected'}</c:if>>내용</option>
                                </select>
                                <input name="find_name" id="find_name" id="find_name" size="30" value="${find_name}" />
                                <input type="submit" value="검색" />
                                <c:if test="${user_id != null}">
	                                <input type="button" class="qWrite" value="글쓰기" onclick="location='QandA_write'" />
                                </c:if>
                            </div>

                        
                    </div>
                </article>
            </section>
        </div>
    </section>
    <!-- contents -->
    </form>
</main>



<jsp:include page="../include/footer.jsp" />