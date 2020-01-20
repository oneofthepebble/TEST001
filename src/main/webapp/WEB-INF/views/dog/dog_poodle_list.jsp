<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>

<!-- style -->
<link rel="stylesheet" href="/css/reset.css">
<link rel="stylesheet" href="/css/dog_board/dog.css">

<!-- jquery -->
<script src="/js/dog_board/dog.js"></script>

<!-- 본문 내용 -->
<main>
    <form method="get" action="total_dog">
        <section id="contents">
                    <article class="column col1">
                        <div class="main">

                            <div class="board_list">
                            <ul class="tabs">
                            	<li><a href="/dog/total_dog?page=1">전체</a></li>
                            	<li class="current"><a href="/dog/dog_poodle_list?page=1">푸들</a></li>
                            	<li><a href="/dog/dog_shih_list?page=1">시츄</a></li>
                            	<li><a href="/dog/dog_mal_list?page=1">말티즈</a></li>
                            </ul>
                            
                                <div class="dog_list">
                                    <c:if test="${!empty dlist }">
                                        <c:forEach var="d" items="${dlist }">
                                            <div class="item">
                                                <div class="img">
                                                    <a href="dog_cont?dog_no=${d.dog_no}&state=cont&page=${page}">
                                                        <img src="/resources/photo_upload${d.dog_file }" width="316"
                                                            height="360" />
                                                    </a>
                                                </div>
                                                <div class="name">
                                                    <a href="dog_cont?dog_no=${d.dog_no}&state=cont&page=${page}">${d.dog_title}</a>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${empty dlist }">
                                        	<p>목록이 없습니다. 관리자에게 문의해 주세요.<p>
                                    </c:if>

                                </div>


                                <%--페이징(쪽나누기)--%>
                                <div id="cList_paging">
                                    <%--검색전 페이징 --%>
                                    <c:if test="${(empty find_field)&&(empty find_name)}">
                                        <c:if test="${page <=1}">
                                            &lt;&nbsp;
                                        </c:if>
                                        <c:if test="${page >1}">
                                            <a href="/dog/dog_poodle_list?page=${page-1}">&lt;</a>&nbsp;
                                        </c:if>

                                        <%--쪽번호 출력부분 --%>
                                        <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                                            <c:if test="${a == page}"><span style="color: blue;">${a}&nbsp;</span>
                                            </c:if>

                                            <c:if test="${a != page}">
                                                <a href="/dog/dog_poodle_list?page=${a}">${a}</a>&nbsp;
                                            </c:if>
                                        </c:forEach>

                                        <c:if test="${page>=maxpage}">&gt;</c:if>
                                        <c:if test="${page<maxpage}">
                                            <a href="/dog/dog_poodle_list?page=${page+1}">&gt;</a>
                                        </c:if>
                                    </c:if>

                                    <%--검색후 페이징 --%>
                                    <c:if test="${(!empty find_field) || (!empty find_name)}">
                                        <c:if test="${page <=1}">
                                            &lt;&nbsp;
                                        </c:if>
                                        <c:if test="${page >1}">
                                            <a
                                                href="/dog/dog_poodle_list?page=${page-1}&find_field=${find_field}&find_name=${find_name}">
                                                &lt;</a>&nbsp;
                                        </c:if>

                                        <%--쪽번호 출력부분 --%>
                                        <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
                                            <c:if test="${a == page}">
                                                <span style="color: blue;">${a}&nbsp;</span>
                                            </c:if>

                                            <c:if test="${a != page}">
                                                <a
                                                    href="/dog/dog_poodle_list?page=${a}&find_field=${find_field}&find_name=${find_name}">
                                                    ${a}</a>&nbsp;
                                            </c:if>
                                        </c:forEach>

                                        <c:if test="${page>=maxpage}">&gt;
                                        </c:if>
                                        <c:if test="${page<maxpage}">
                                            <a
                                                href="/dog/dog_poodle_list?page=${page+1}&find_field=${find_field}&find_name=${find_name}">
                                                &gt;</a>
                                        </c:if>
                                    </c:if>
                                </div>


                                <%--검색폼 --%>
                                <div id="cFind_wrap">
                                    <select name="find_field" class="select">
                                        <option value="dog_title" <c:if test="${find_field == 'dog_title'}">
                                            ${'selected'}</c:if>>제목</option>
                                        <option value="dog_cont" <c:if test="${find_field == 'dog_cont'}">
                                            ${'selected'}</c:if>>내용</option>
                                    </select>
                                    <input name="find_name" id="find_name" size="30" value="${find_name}" />
                                    <input type="submit" value="검색" />
                                    
                                    <c:if test="${user_id=='admin'}">
                                    <input type="button" class="cWrite" value="글쓰기" onclick="location='dog_write?'" />
                                	</c:if>
                                </div>

                            </div>
                        </div>
                    </article>
                </section>
        <!-- contents -->
    </form>
</main>

<jsp:include page="../include/footer.jsp" />