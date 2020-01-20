<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<!-- 헤더 사진 -->
<script>
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/login_wallpaper.jpg); background-position:top; background-size:cover;');
});
</script>

<link rel="stylesheet" href="<c:url value="/resources/css/MemberJoin/MemberJoin.css" />" />

    <section>
        <form action="#" id="MemberForm" method="post" autocomplete="off">
            <div id="JoinContainer"> <!-- max size -->
                <div class="content"><!--min size -->
                <div class="titlefindPwd">
			    	<p>회원가입</p>
			    </div>
                    <div class="divSize">
                        <h2>아이디</h2>
                        <span class="s-span-Size">
                            <input class="input-size" name="user_id" id="id" type="text" maxlength="12" />
                            <!-- <span class="s-span-Size1">@naver.com</span> -->
                        </span>
                    </div>
                    <div class="divLabel box-transition">
                        <label id="labelID">아이디를 입력하세요</label>
                    </div>

                    <div class="divSize">
                        <h2>비밀번호</h2>
                        <span class="s-span-Size">
                            <input class="input-size" name="user_pwd" id="pw" type="password" maxlength="15">
                        </span>
                    </div>
                    <div class="divLabel">
                        <label id="labelPW">비밀번호를 입력하세요</label>
                    </div>

                    <div class="divSize">
                        <h2>비밀번호 재확인</h2>
                        <span class="s-span-Size">
                            <input class="input-size" name="user_checkpw" id="checkpw" type="password" maxlength="15">
                        </span>
                    </div>
                    <div class="divLabel">
                        <label id="labelCheckPW">비밀번호가 다릅니다.</label>
                    </div>

                    <div class="divSize">
                        <h2>이름</h2>
                        <span class="s-span-Size">
                            <input class="input-size" name="user_name" type="text" id="name">
                        </span>
                    </div>
                    <div class="divLabel">
                        <label id="labelName">이름을 입력하세요</label>
                    </div>

                    <div class="divSize">
                        <h2>생년월일</h2>
                        <div class="birth-day">
                            <div class="birth-yy">
                                <span>
                                    <input id='birthdayYY' name="user_birthdayYY" class="birth-day-Size" type="text" maxlength="4" placeholder="년(4자)">
                                </span>
                            </div>
                        
                            <div class="birth-mm">
                                <span>
                                    <select name="user_birthdayMM" id='birthdayMM' class="birth-day-Size">
                                        <option value="0">월</option>
                                        <option value="1">1월</option>
                                        <option value="2">2월</option>
                                        <option value="3">3월</option>
                                        <option value="4">4월</option>
                                        <option value="5">5월</option>
                                        <option value="6">6월</option>
                                        <option value="7">7월</option>
                                        <option value="8">8월</option>
                                        <option value="9">9월</option>
                                        <option value="10">10월</option>
                                        <option value="11">11월</option>
                                        <option value="12">12월</option>
                                    </select>
                                </span>
                            </div>

                            <div class="birth-dd">
                                <span>
                                    <input name="user_birthdayDD" id='birthdayDD' class="birth-day-Size" type="text" maxlength="2">
                                    <p class='birthdayDD_text'>일</p>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="divLabel">
                        <label id='label_birthday'>생년월일을 입력하세요</label>
                    </div>

                    <div class="divSize">
                        <h2>성별</h2>
                        <span class="s-span-Size">
                            <!-- <input class="input-size" type="text"> -->
                            <select name="user_gender" class="input-size" id="gender">
                                <option value="G">성별</option>
                                <option value="M">남자</option>
                                <option value="F">여자</option>
                            </select>
                        </span>
                    </div>
                    <div class="divLabel">
                        <label id='label_gender'>성별을 입력하세요</label>
                    </div>

                    <div class="divSize">
                        <h2>본인 확인 이메일</h2>
                        <span class="s-span-Size">
                            <input name="user_email" id="email" class="input-size" type="text" placeholder="E-mail을 입력하세요">
                        </span>
                    </div>
                    <div class="divLabel">
                        <label id="labelEmail">E-mail을 입력하세요</label>
                    </div>

                    <div class="PhonedivSize">
                        <h2>휴대전화</h2>
                        <span class="PSpanSize">
                            <input name="user_phone" id='phoneNumber' class="PInputSize" type="text" placeholder="00*-000*-0000" onKeyup="inputPhoneNumber(this);" maxlength="13">
                        </span>
                        <span>
       <%--코드 발송 처리 해야함 --%>
                            <input id="SmsCodeSend" class="buttonSize" type="button" value="인증번호 받기">
                        </span>
                    </div>
                    <div class="divLabel">
                        <label id='label_Phone_number'>전화번호를 입력하세요</label>
                    </div>

                    <div class="CodedivSize">
                        <span class="PSpanSizeCB">
                            <input name="user_SMSCode" id='SMSCode' type="text" class="input-size" placeholder="인증번호를 입력하세요" disabled>
                        </span>
                        <input type="hidden" id="codeEquals"/>
                    </div>
                    <div class="divLabel">
                        <label id='label_SMSCode'>인증번호를 입력하세요</label>
                    </div>

                    <div class="MemberJoin">
                        <input class="MemberButton" type="button" value="가입하기" onclick="validate();" onkeydown="return keydown();">
                    </div>
                </div> <!-- content -->
            </div><!-- container-->
        </form>
    </section>
    <script src="<c:url value="/js/MemberJoin/MemberJoin.js" />"></script>
<jsp:include page="../include/footer.jsp" />


