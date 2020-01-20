/**
 * 메뉴 플로팅
 */
//메뉴 플로팅
$(document).ready(function () { //문서가 준비되면 함수 실행        
	var menu=$('#menu').offset();
	$(window).scroll(function () { //윈도우가 스크롤될때 함수를 실행
		if($(document).scrollTop() > menu.top) {//menu의 top이 스크롤의 top보다 작을때 함수 실행
			$('#menu').addClass('fixed'); //div #menu에 fixed 라는 클래스를 추가
		} else {
			$('#menu').removeClass('fixed');//fixed 클래스 제거
		}            
	});
}); 



