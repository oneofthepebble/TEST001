/**
 * dog.js
 */
//강아지 분양 헤더 이미지 변경
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
    $('#header').attr('style', 'background: url(/resources/img/dog/dog_wallpaper.jpg); background-position:center; background-size:cover;');

  });

//강아지 분양 게시판 삭제 관련
$(document).ready(function del_check() {
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

//강아지 분양 게시판 수정 관련
function edit_check() {		
	
	if($.trim($("#dog_title").val())==""){
		alert("강아지 종류를 입력하세요");
		$("#dog_title").val("").focus();
		return false;
	}
	if($.trim($("#dog_file").val())==""){
		alert("파일을 첨부해 주세요");
		$("#dog_file").val("").focus();
		return false;
	}		
	alert("수정 되었습니다.");		
};

//강아지 분양 게시판 글쓰기 관련
function write_check() {		
	
	if($.trim($("#dog_title").val())==""){
		alert("강아지 종류를 입력하세요");
		$("#dog_title").val("").focus();
		return false;
	}
	if($.trim($("#dog_file").val())==""){
		alert("파일을 첨부해 주세요");
		$("#dog_file").val("").focus();
		return false;
	}		
	alert("저장되었습니다.");		
}