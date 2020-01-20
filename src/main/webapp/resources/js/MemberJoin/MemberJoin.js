$(document).ready(function() {
	var IDcode = /^[a-zA-Z0-9]{6,12}$/; // 아이디가 적합한지 검사할 정규식
	var PWcode = /^[a-zA-Z0-9]{8,15}$/; // 패스워드가 적합한지 검사할 정규식
	var emailcode = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일이 적합한지 검사할 정규식
	var YY = /^[0-9]{4}$/;
	var DD = /^[0-9]{2}$/;
	var PhoneNumberCode = /^[0-9]{10,11}$/;

	//유효성 검증할 id값 가져오기
	var id = document.getElementById('id');
	var labelID = document.getElementById('labelID');

	var pw = document.getElementById('pw');
	var labelPW = document.getElementById('labelPW');

	var checkpw = document.getElementById('checkpw');
	var labelCheckPW = document.getElementById('labelCheckPW');

	var name = document.getElementById('name');
	var labelName = document.getElementById('labelName');

	var birthdayYY = document.getElementById('birthdayYY');
	var birthdayMM = document.getElementById('birthdayMM');
	var birthdayDD = document.getElementById('birthdayDD');
	var label_birthday = document.getElementById('label_birthday');

	var gender = document.getElementById('gender');
	var label_gender = document.getElementById('label_gender');

	var email = document.getElementById('email');
	var labelEmail = document.getElementById('labelEmail');

	var phoneNumber = document.getElementById('phoneNumber');
	var label_Phone_number = document.getElementById('label_Phone_number');

	var SMSCode = document.getElementById('SMSCode');
	var label_SMSCode = document.getElementById('label_SMSCode');

	//유효성 검증 check 함수 실행
	$('#id').on('keyup', function() {
		if (!check(IDcode, id, labelID, '아이디는 6~12자의 영문 대소문자와 숫자로만 입력')) {
			return false;
		}

		var words = $('#id').val();
		$.ajax({
			async: true,
			type: 'POST',
			url: '/checkID',
			data: { checkwords: words },
			success: function(result) {
				if (result == words) {
					// alert(message);
					//위에 가져온 id값을 기준으로 style 접근
					labelID.style.display = 'block';
					labelID.style.color = 'red';
					// innerHTML으로 message를 바꾼다.
					labelID.innerHTML = '중복 아이디 입니다.';
					//return false;
				} else {
					labelID.style.display = 'block';
					labelID.style.color = 'green';
					// innerHTML으로 message를 바꾼다.
					labelID.innerHTML = '사용 가능한 아이디 입니다.';
				}
			},
			error: function() {
				alert('에러');
			}
		});
	});

	//비번 패턴 길이 체크
	$('#pw').on('keyup', function() {
		if (!check(PWcode, pw, labelPW, '패스워드는 8~15자의 영문 대소문자와 숫자로만 입력')) {
			return false;
		}
	});

	//비번 체크
	$('#checkpw').on('keyup', function() {
		if (pw.value != checkpw.value) {
			// alert("비밀번호가 다릅니다. 다시 확인해 주세요.");
			labelCheckPW.style.display = 'block';
			labelCheckPW.innerHTML = '비밀번호가 다릅니다. 다시 확인해 주세요.';
			return false;
		} else {
			labelCheckPW.style.display = 'none';
		}
	});

	//이름 체크
	$('#name').on('keyup', function() {
		if (!check1(name, labelName, '이름을 입력해 주세요')) {
			return false;
		}
	});

	//생일 년도
	$('#birthdayYY').on('keyup', function() {
		if (!check1(birthdayYY, label_birthday, '태어난 년도를 입력해주세요')) {
			return false;
		}
		if (!check(YY, birthdayYY, label_birthday, '4자리 숫자로 입력해주세요')) {
			return false;
		}
	});

	//생일 월
	$('#birthdayMM').blur(function() {
		if (birthdayMM.options[birthdayMM.selectedIndex].value == '0') {
			label_birthday.style.display = 'block';
			label_birthday.innerHTML = '태어난 달을 입력해주세요';
			birthdayMM.value = '0';
			return false;
		} else {
			label_birthday.style.display = 'none';
		}
	});

	//생일 일
	$('#birthdayDD').on('keyup', function() {
		if (!check1(birthdayDD, label_birthday, '태어난 일을 입력해주세요')) {
			return false;
		}
		if (!check(DD, birthdayDD, label_birthday, '2자리 숫자로 입력해주세요')) {
			return false;
		}
	});

	//성별
	$('#gender').on('keyup', function() {
		if (gender.options[gender.selectedIndex].value == 'G') {
			label_gender.style.display = 'block';
			label_gender.innerHTML = '성별을 입력해주세요';
			gender.value = 'G';
			return false;
		} else {
			label_gender.style.display = 'none';
		}
	});

	//이메일
	$('#email').on('keyup', function() {
		if (!check1(email, labelEmail, 'E-mail을 입력해 주세요')) {
			return false;
		}

		if (!check(emailcode, email, labelEmail, '적합하지 않은 이메일 형식입니다.')) {
			return false;
		}
	});

	//핸드폰 번호
	//	$('#phoneNumber').on('keyup', function() {
	//		if (!check1(phoneNumber, label_Phone_number, '핸드폰 번호를 입력해주세요')) {
	//			return false;
	//		}
	//
	//		if (!check(PhoneNumberCode, phoneNumber, label_Phone_number, '-를 제외한 숫자로만 입력해주세요')) {
	//			return false;
	//		}
	//	});

	$('#SmsCodeSend').on('click', function() {
		//문자발송
		var phoneNumber = $('#phoneNumber').val();
		console.log(phoneNumber);

		if (phoneNumber != '') {
			$.ajax({
				type: 'POST',
				url: 'codesend',
				data: { user_phone: phoneNumber },
				datatype: 'String',
				success: function(data) {
					if (data == '') {
						alert('인증번호 발송이 실패하였습니다');
						return false;
					} else {
						$('#SMSCode').attr('disabled', false);

						alert('인증번호가 발송 되었습니다.');
						console.log(data);

						$('#codeEquals').val(data);
						console.log('smsequals: ' + $('#codeEquals').val());
						return false;
					}
				},
				error: function() {
					alert('data error');
					// location='find_id'
				}
			}); //$.ajax
		}
	});

	$('#SMSCode').on('keyup', function() {
		if (!check1(SMSCode, label_SMSCode, '인증번호를 입력해주세요')) {
			return false;
		}
	});
});

//버튼 눌렀을경우 유효성 검증
function validate() {
	var IDcode = /^[a-zA-Z0-9]{6,12}$/; // 아이디가 적합한지 검사할 정규식
	var PWcode = /^[a-zA-Z0-9]{8,15}$/; // 패스워드가 적합한지 검사할 정규식
	var emailcode = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일이 적합한지 검사할 정규식
	var YY = /^[0-9]{4}$/;
	var DD = /^[0-9]{2}$/;
	var PhoneNumberCode = /^[0-9]{10,11}$/;

	//유효성 검증할 id값 가져오기
	var id = document.getElementById('id');
	var labelID = document.getElementById('labelID');

	var pw = document.getElementById('pw');
	var labelPW = document.getElementById('labelPW');

	var checkpw = document.getElementById('checkpw');
	var labelCheckPW = document.getElementById('labelCheckPW');

	var name = document.getElementById('name');
	var labelName = document.getElementById('labelName');

	var birthdayYY = document.getElementById('birthdayYY');
	var birthdayMM = document.getElementById('birthdayMM');
	var birthdayDD = document.getElementById('birthdayDD');
	var label_birthday = document.getElementById('label_birthday');

	var gender = document.getElementById('gender');
	var label_gender = document.getElementById('label_gender');

	var email = document.getElementById('email');
	var labelEmail = document.getElementById('labelEmail');

	var phoneNumber = document.getElementById('phoneNumber');
	var label_Phone_number = document.getElementById('label_Phone_number');

	var SMSCode = document.getElementById('SMSCode');
	var label_SMSCode = document.getElementById('label_SMSCode');

	if (!check2(IDcode, id, labelID, '아이디는 6~12자의 영문 대소문자와 숫자로만 입력')) {
		return false;
	}

	if (!check2(PWcode, pw, labelPW, '패스워드는 8~15자의 영문 대소문자와 숫자로만 입력')) {
		return false;
	}

	//비번 체크

	if (pw.value != checkpw.value) {
		// alert("비밀번호가 다릅니다. 다시 확인해 주세요.");
		labelCheckPW.style.display = 'block';
		labelCheckPW.innerHTML = '비밀번호가 다릅니다. 다시 확인해 주세요.';
		checkpw.value = '';
		checkpw.focus();
		return false;
	} else {
		labelCheckPW.style.display = 'none';
	}

	//이름 체크

	if (!check3(name, labelName, '이름을 입력해 주세요')) {
		return false;
	}

	//생일 년도

	if (!check3(birthdayYY, label_birthday, '태어난 년도를 입력해주세요')) {
		return false;
	}
	if (!check2(YY, birthdayYY, label_birthday, '4자리 숫자로 입력해주세요')) {
		return false;
	}

	//생일 월

	if (birthdayMM.options[birthdayMM.selectedIndex].value == '0') {
		label_birthday.style.display = 'block';
		label_birthday.innerHTML = '태어난 달을 입력해주세요';
		birthdayMM.focus();
		birthdayMM.value = '0';
		return false;
	} else {
		label_birthday.style.display = 'none';
	}

	//생일 일

	if (!check3(birthdayDD, label_birthday, '태어난 일을 입력해주세요')) {
		return false;
	}
	if (!check2(DD, birthdayDD, label_birthday, '2자리 숫자로 입력해주세요')) {
		return false;
	}

	//성별

	if (gender.options[gender.selectedIndex].value == 'G') {
		label_gender.style.display = 'block';
		label_gender.innerHTML = '성별을 입력해주세요';
		gender.focus();
		gender.value = 'G';
		return false;
	} else {
		label_gender.style.display = 'none';
	}

	//이메일

	if (!check3(email, labelEmail, 'E-mail을 입력해 주세요')) {
		return false;
	}

	if (!check2(emailcode, email, labelEmail, '적합하지 않은 이메일 형식입니다.')) {
		return false;
	}

	//핸드폰 번호

	// if (!check3(phoneNumber, label_Phone_number, '핸드폰 번호를 입력해주세요')) {
	// 	return false;
	// }

	// if (!check2(PhoneNumberCode, phoneNumber, label_Phone_number, '-를 제외한 숫자로만 입력해주세요')) {
	// 	return false;
	// }

	//인증번호

	if (!testEquals(SMSCode, label_SMSCode, '인증번호를 입력해주세요')) {
		return false;
	}

	// if (!smscheck3(SMSCode, SmsEquals, label_SMSCode, '인증번호를 잘못 입력하셨습니다.')) {
	// 	return false;
	// }

	//	if (SMSCode.val() == $('#codeEquals').val()) {
	//		alert('회원가입이 완료되었습니다.');
	//		return false;
	//	} else {
	//		console.log($('#codeEquals').val() + '회원가입버튼');
	//		SMSCode.style.display = 'block';
	//		SMSCode.style.color = 'red';
	//		SMSCode.innerHTML = '인증번호를 잘못 입력하셨습니다.';
	//		SMSCode.value = '';
	//		SMSCode.focus();
	//		return false;
	//	}
}

//check 함수 정의 값을 전달 받아서 실행
//패턴이 안맞았을 경우
function check(pattern, IdValue, labelValue, message) {
	if (pattern.test(IdValue.value)) {
		labelValue.style.display = 'none';
		return true;
	}
	// alert(message);
	//위에 가져온 id값을 기준으로 style 접근
	labelValue.style.display = 'block';
	labelValue.style.color = 'red';
	// innerHTML으로 message를 바꾼다.
	labelValue.innerHTML = message;
	//return false;
}
//패턴은 맞지만 다른게 다를 경우
function check1(IdValue, labelValue, message) {
	if (IdValue.value) {
		labelValue.style.display = 'none';

		return true;
	}
	labelValue.style.display = 'block';
	labelValue.style.color = 'red';
	labelValue.innerHTML = message;
}

//validate button을 눌렀을 경우 유효성 검증할 함수
function check2(re, what, labelID, message) {
	if (re.test(what.value)) {
		labelID.style.display = 'none';
		return true;
	}
	// alert(message);
	//위에 가져온 id값을 기준으로 style 접근
	labelID.style.display = 'block';
	labelID.style.color = 'red';
	// innerHTML으로 message를 바꾼다.
	labelID.innerHTML = message;
	what.value = '';
	what.focus();
	//return false;
}

function check3(what, labelID, message) {
	if (what.value) {
		labelID.style.display = 'none';
		if (what == phoneNumber) {
			SMSCode.disabled = false;
		}
		return true;
	}
	labelID.style.display = 'block';
	labelID.style.color = 'red';
	labelID.innerHTML = message;
	what.value = '';
	what.focus();
}

function testEquals(what, labelID, message) {
	if (what.value) {
		MemberForm.action = '/memberjoin_ok';
		MemberForm.submit();
		return true;
	}
	labelID.style.display = 'block';
	labelID.style.color = 'red';
	labelID.innerHTML = message;
	what.value = '';
	what.focus();
}

function smscheck3(SMSid, equalsValue, smslabel, message) {
	alert(SMSid.value + '///' + equalsValue);
	if (SMSid == equalsValue) {
		alert('회원가입이 완료되었습니다.');
		return true;
	}
	console.log('smscheck3 == ' + SMSid.value + '==' + equalsValue);
	smslabel.style.display = 'block';
	smslabel.style.color = 'red';
	smslabel.innerHTML = message;
	SMSid.value = '';
	SMSid.focus();
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
