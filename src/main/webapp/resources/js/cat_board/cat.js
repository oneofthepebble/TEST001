/**
 * cat.js
 */
//고양이 분양 헤더 이미지 변경
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/cat/cat_wallpaper05.jpg); background-position:center; background-size:cover;');
      
  });

//고양이 분양 게시판 삭제 관련
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

//고양이 분양 게시판 수정 관련
function edit_check() {		
	
	if($.trim($("#cat_title").val())==""){
		alert("고양이 종류를 입력하세요");
		$("#cat_title").val("").focus();
		return false;
	}
	if($.trim($("#cat_file").val())==""){
		alert("파일을 첨부해 주세요");
		$("#cat_file").val("").focus();
		return false;
	}		
	alert("수정 되었습니다.");		
};

//고양이 분양 게시판 글쓰기 관련
function write_check() {		
	
	if($.trim($("#cat_title").val())==""){
		alert("고양이 종류를 입력하세요");
		$("#cat_title").val("").focus();
		return false;
	}
	if($.trim($("#cat_file").val())==""){
		alert("파일을 첨부해 주세요");
		$("#cat_file").val("").focus();
		return false;
	}		
	alert("저장되었습니다.");		
};

$(document).ready(function () {

    $('ul.tabs li').click(function () {

        $('ul.tabs li').removeClass('current');

        $(this).addClass('current');
    })

});