//지도 api 실행

$(document).ready(function() {
	var container = document.getElementById('MapArea'); //지도 출력 div
	var options = {
		center: new kakao.maps.LatLng(37.538068, 127.123429), //지도의 중심 좌표
		level: 4 //지도 확대
	};

	var map = new kakao.maps.Map(container, options); //지도 생성
});

//display전환과 스크롤,opacity 컨트롤
function admin_add_CompanyMap() {
	var addCompanyDP = document.getElementById('addCompany');
	var keyEventStop = document.getElementById('CompanyName');

	//display 보이고 감추기 --id접근 바로 가능한듯?;
	addCompany.classList.toggle('addCompany');
	// addCompanyDP.style.display == 'block'
	// 	? (addCompanyDP.style.display = 'none')
	// 	: (addCompanyDP.style.display = 'block');

	//버튼활성화 될때 마다 keyEvent영역 잠금
	if (!DisPlayEvent(keyEventStop)) return false;
	// addCompanyDP.style.display == 'block'
	// 	? (CompanyName.style.pointerEvents = 'none')
	// 	: (CompanyName.style.pointerEvents = 'auto');
}

// keyEvent 잠그기(마우스 이벤트)
function DisPlayEvent(keyEventStop) {
	var opacityBlock = document.getElementById('opacityBlock');
	var scrollLock = document.getElementById('scrollLock');
	opacityBlock.classList.toggle('DPbackground');
	scrollLock.classList.toggle('not_scroll');
	keyEventStop.style.pointerEvents == 'none'
		? (keyEventStop.style.pointerEvents = 'auto')
		: (keyEventStop.style.pointerEvents = 'none');
	header.style.pointerEvents == 'none'
		? (header.style.pointerEvents = 'auto')
		: (header.style.pointerEvents = 'none');
	title.style.pointerEvents == 'none' ? (title.style.pointerEvents = 'auto') : (title.style.pointerEvents = 'none');
	MapArea.style.pointerEvents == 'none'
		? (MapArea.style.pointerEvents = 'auto')
		: (MapArea.style.pointerEvents = 'none');
	MapArea.classList.toggle('overlayOpacity');
	header.classList.toggle('overlayOpacity');
	title.classList.toggle('overlayOpacity');
}
// multipart 사용시 contentType = encoding 변환해주는 부분
function fn_check() {
	// 패턴값
	var CompanyName_pattern = /^[가-힣a-zA-Z]+$/;
	var Latitude = /^[0-9]+[.]+[0-9]*$/;
	var Hardness = /^[0-9]+[.]+[0-9]*$/;
	var address = /^[가-힣0-9]+\s?[-\s]?[가-힣0-9\s]*$/;
	// 사용자가 입력한 값
	var company_name = document.getElementById('company_name');
	var company_x = document.getElementById('company_x');
	var company_y = document.getElementById('company_y');
	var company_addr = document.getElementById('company_addr');

	// var company_x = company_x.value;
	// var company_y = company_y.value;
	// var company_addr = company_y.value;
	if (!CompanyInfo_check(CompanyName_pattern, company_name, '한글이나 영어로 입력해주세요')) {
		return false;
	}
	if (!CompanyInfo_check(Latitude, company_x, '정확한 위도값(숫자)을 입력해주세요')) {
		return false;
	}
	if (!CompanyInfo_check(Hardness, company_y, '정확한 경도값(숫자)을 입력해주세요')) {
		return false;
	}
	if (!CompanyInfo_check(address, company_addr, '주소를 입력해주세요')) {
		return false;
	}

	frm.action = '/newCompany';
	frm.submit();
}

//비동기식
function MapInfo(company_name) {
	var container = document.getElementById('MapArea'); //지도 출력 div
	var options = {
		center: new kakao.maps.LatLng(37.537915, 127.123304), //지도의 중심 좌표
		level: 2 //지도 확대
	};
	var map = new kakao.maps.Map(container, options); //지도 생성

	$.ajax({
		async: true,
		type: 'POST',
		url: '/MapOverlay',
		data: { company_name: company_name },
		datatype: 'json',
		success: function(result) {
			// var container = document.getElementById('MapArea'); //지도 출력 div
			// var options = {
			// 	center: new kakao.maps.LatLng(37.537915, 127.123304), //지도의 중심 좌표
			// 	level: 2 //지도 확대
			// };

			// var map = new kakao.maps.Map(container, options); //지도 생성
			var content =
				'<div class="overlayBOX">' +
				'<div class="companyImage">' +
				'<img src="' +
				result.company_image +
				'" alt="NO Image">' +
				'</div>' +
				'<div class="Company_name">' +
				result.company_name +
				'</div>' +
				'<div class="Company_addr">' +
				result.company_addr +
				'</div>' +
				'</div>';

			var position = new kakao.maps.LatLng(result.company_x, result.company_y);

			// 커스텀 오버레이를 생성합니다
			var customOverlay = new kakao.maps.CustomOverlay({
				position: position,
				content: content,
				xAnchor: 0.3,
				yAnchor: 0.91
			});
			customOverlay.setMap(map);
			map.setCenter(position);
		},
		error: function() {
			alert('에러');
		}
	});
}

function CompanyInfo_check(pattern, IdValue, message) {
	if (pattern.test(IdValue.value)) {
		return true;
	}
	IdValue.focus();
	alert(pattern + IdValue.value + message);
	//return false;
}
