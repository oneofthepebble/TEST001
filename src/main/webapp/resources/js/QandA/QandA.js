/**
 * QandA.js
 */
//고양이 분양 헤더 이미지 변경
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/QandA/board_wallpaper02.jpg); background-position:bottom; background-size:cover;');
  });

//고양이 분양 게시판 삭제 관련
$(document).ready(function QandA_del_check() {
	$('#del').click(function() {
		var result = confirm('삭제 하시겠습니까?');
		
		if(result) {
			alert("삭제 되었습니다");
		}else {
			history.back();
			return false;
		}
	});
});

//수정관련
function QandA_edit_ok() {		
	
	if($.trim($("#q_title").val())==""){
		alert("제목을 입력하세요");
		$("#q_title").val("").focus();
		return false;
	}		
	alert("수정 되었습니다.");		
};	

//고양이 분양 게시판 글쓰기 관련
function QandA_write_ok() {		
	
	if($.trim($("#q_title").val())==""){
		alert("제목을 입력하세요");
		$("#q_title").val("").focus();
		return false;
	}		
	alert("저장 되었습니다.");		
};	

//로그인 전 제목 클릭시 cont_check
/*$(document).ready(function cont_check() {
	$('#login_check').click(function() {
		var result = alert('로그인을 해주세요');
		
		if(result) {
			alert("삭제 되었습니다");
		}else {
			history.back();
			return false;
		}
	});
});*/