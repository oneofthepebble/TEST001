//수정 버튼 눌렀을 때 확인 버튼 display none
function updateEmail() {
	var Email = document.getElementById('Member_Email');
	var Email_update = document.getElementById('Update_Email_Area');

	// Email.style.display = 'none';
	if (!changeDisPlay(Email, Email_update)) {
		return false;
	}
}

//확인 버튼 눌렀을 때 확인 버튼 display block
function updateEmail_ok() {
	//	display 영역 ID값
	var Email_info_DP = document.getElementById('Member_Email');
	var Email_input_DP = document.getElementById('Update_Email_Area');
	//	email 유효성 검증 패턴값
	var emailcode = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일이 적합한지 검사할 정규식

	//	아작스 처리 할 user_id값 + 수정할 email input값
	var user_id = $('.MemberValue').text();
	var update_email = document.getElementById('Update_Email');
	//	ajax로 변경값 출력해줄 String 값
	var ajaxEmail = "<p class='OneLine'>E-Mail : <span>";
	var ajaxEmail1 = "</span></p> <input id='updateBtn_PN' type='button' value='수정' onclick='updateEmail();'>";

	//	ajax로 보낼 매핑 주소값
	var EmailURL = '/UpdateUserEmail';

	//유효성 검증후 ajax 처리
	if (!check(emailcode, user_id, update_email, Email_input_DP, Email_info_DP, '지원하지 않는 E-mail 형식입니다.')) {
		return false;
	}
	UpdateMemberInfo(EmailURL, user_id, update_email, Email_info_DP, Email_input_DP, ajaxEmail, ajaxEmail1);
}

//수정 버튼 눌렀을 때 확인 버튼 display none
function updatePN() {
	var PhoneNumber = document.getElementById('Member_PhoneNumber');
	var PhoneNumber_update = document.getElementById('Update_PN_Area');

	if (!changeDisPlay(PhoneNumber, PhoneNumber_update)) {
		return false;
	}
}

//확인 버튼 눌렀을 때 확인 버튼 display block
function updatePN_ok() {
	//display none block처리 할 id 값
	var PhoneNumber_info_DP = document.getElementById('Member_PhoneNumber');
	var PhoneNumber_input_DP = document.getElementById('Update_PN_Area');

	//유효성 검증 패턴 값
	var PhoneNumberCode = /^[0-9]{10,11}$/;

	//ajax 처리시 db에서 id값을 기준으로 회원정보 변경 할 id값
	var user_id = $('.MemberValue').text();
	//핸드폰 번호 수정 할 input 값
	var PnUpdateInfo = document.getElementById('Update_PN');

	//ajax값 출력할 div 만들어줄 innerHTML
	var ajaxMessage = "<p class='OneLine'>Phone : <span>";
	var ajaxMessage1 = "</span></p><input id='updateBtn_PN' type='button' value='수정'onclick='updatePN();'>";

	//비동기식 url 주소로 보낼 매핑주소
	var PhoneURL = '/updateUserPN';

	//유효성 검증
	// if (
	// 	!check(PhoneNumberCode, user_id, PnUpdateInfo, PhoneNumber_input_DP, PhoneNumber_info_DP, '지원하지 않는 핸드폰 번호 입니다.')
	// ) {
	// 	return false;
	// }
	if (!no_check(user_id, PnUpdateInfo, PhoneNumber_input_DP, PhoneNumber_info_DP, '지원하지 않는 핸드폰 번호 입니다.')) {
		return false;
	}
	//ajax 처리(비동기)
	UpdateMemberInfo(
		PhoneURL,
		user_id,
		PnUpdateInfo,
		PhoneNumber_info_DP,
		PhoneNumber_input_DP,
		ajaxMessage,
		ajaxMessage1
	);
}

//수정 버튼 눌렀을 때 확인 버튼 display none
function updatePwd() {
	if (!changeDisPlay(Member_password, Update_Pwd_Area)) {
		return false;
	}
}

//확인 버튼 눌렀을 때 확인 버튼 display block
function updatePwd_ok() {
	//	display 영역 ID값
	// var Email_info_DP = document.getElementById('Member_Email');
	// var Email_input_DP = document.getElementById('Update_Email_Area');
	//	email 유효성 검증 패턴값
	var PWcode = /^[a-zA-Z0-9]{8,15}$/;

	//	아작스 처리 할 user_id값 + 수정할 email input값
	var user_id = $('.MemberValue').text();
	var update_email = document.getElementById('Update_Email');
	//	ajax로 변경값 출력해줄 String 값
	var ajaxEmail = "<p class='OneLine'>E-Mail : <span>";
	var ajaxEmail1 = "</span></p> <input id='updateBtn_PN' type='button' value='수정' onclick='updateEmail();'>";

	//	ajax로 보낼 매핑 주소값
	var PwdURL = '/UpdateUserPwd';

	//유효성 검증후 ajax 처리
	if (
		!check(PWcode, user_id, Update_Pwd, Update_Pwd_Area, Member_password, '8자 이상 15자 이하의 영어 대(소)문자, 숫자만 입력 가능합니다.')
	) {
		return false;
	}

	UpdatePassword(PwdURL, user_id, Update_Pwd, Member_password, Update_Pwd_Area, ajaxEmail, ajaxEmail1);
}

//휴대폰 입력시 - 붙히기
function inputPhoneNumber(obj) {
	var number = obj.value.replace(/[^0-9]/g, '');
	var phone = '';

	if (number.length < 4) {
		return number;
	} else if (number.length < 7) {
		phone += number.substr(0, 3);
		phone += '-';
		phone += number.substr(3);
	} else if (number.length < 11) {
		phone += number.substr(0, 3);
		phone += '-';
		phone += number.substr(3, 3);
		phone += '-';
		phone += number.substr(6);
	} else {
		phone += number.substr(0, 3);
		phone += '-';
		phone += number.substr(3, 4);
		phone += '-';
		phone += number.substr(7);
	}
	obj.value = phone;
}

//disPlay 변환 함수
function changeDisPlay(noneDP, blockDP) {
	if ((noneDP.style.display = 'block')) {
		noneDP.style.display = 'none';
		blockDP.style.display = 'block';
		return true;
	}
	alert(console.log(buttonValue.style.display));
}

//정보 수정 할 아작스 함수
function UpdateMemberInfo(mappingURL, user_id, user_updateValue, blockDP, noneDP, ajaxMessage, ajaxMessage1) {
	var user_update = user_updateValue.value;

	$.ajax({
		async: true,
		type: 'POST',
		url: mappingURL,
		data: { user_id, user_update },
		success: function(result) {
			if (result != -1) {
				var ajaxView = ajaxMessage + result + ajaxMessage1;
				blockDP.style.display = 'block';
				blockDP.innerHTML = ajaxView;
				user_updateValue.value = '';
				noneDP.style.display = 'none';

				alert('정보가 수정 되었습니다.');
			} else {
				alert('정보 수정에 실패했습니다.' + result);
			}
		},
		error: function(result) {
			alert('에러');
		}
	});
}

//정보 수정 할 아작스 함수
function UpdatePassword(mappingURL, user_id, user_updateValue, blockDP, noneDP) {
	var user_update = user_updateValue.value;

	$.ajax({
		async: true,
		type: 'POST',
		url: mappingURL,
		data: { user_id, user_update },
		success: function(result) {
			if (result != -1) {
				blockDP.style.display = 'block';

				user_updateValue.value = '';
				noneDP.style.display = 'none';

				alert('정보가 수정 되었습니다.');
			} else {
				alert('정보 수정에 실패했습니다.' + result);
			}
		},
		error: function(result) {
			alert('에러');
		}
	});
}

//패턴 체크 함수
function check(pattern, user_id, user_update, noneDP, blockDP, message) {
	if (pattern.test(user_update.value)) {
		return true;
	}
	noneDP.style.display = 'none';
	blockDP.style.display = 'block';

	user_update.value = '';
	alert(message);
}

function no_check(user_id, user_update, noneDP, blockDP, message) {
	if (user_update.value) {
		return true;
	}
	noneDP.style.display = 'none';
	blockDP.style.display = 'block';

	user_update.value = '';
	alert(message);
}
