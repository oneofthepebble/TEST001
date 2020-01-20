//분양게시판 헤더 이미지 변경
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/QandA/board_wallpaper02.jpg); background-position:bottom; background-size:cover;');
  });


function reply_ok() {
    var id=$('#member_id').val();
    var cont=$('#reply').val();
    var no=$('#ref_no').val();

    console.log(id);
    console.log(cont);
    console.log(no);


    $.ajax({
        type:"POST",
        url:"reply_ok",
        data: {"id":id,"cont":cont,"no":no},
        datatype:"int",
        success: function (data) {
            if(data!=1){
                alert('댓글 저장 실패!')
                return false;
            }else{
                // $('#reply_list_div').load(window.location.href+"#reply_list_div");
                location.reload();
            }
        },
        error:function(){
            alert("data error");
            // location='find_id'
        }
    });//$.ajax
}


function text_area_length() {
    var length=$('#reply').val();

    console.log(length);
    if(length.length>312){
        alert('최대300글자입니다!');
    }
}

function del_ok() {
    var con_F=confirm('정말 삭제하시겠습니까?');
    if(con_F==true){
        return true;
    }else{
        return false;
    }
}

function reply_up() {
    href_a();
    $('#reply_update').attr('readonly',false);
    $('#reply_update').css('background','white');

    $('#update').css("display",'none');
    $('#delete').css("display",'none');
    $('#check').css("display",'inline');
    $('#cancel').css("display",'inline');



}

function href_a(){
    $('a[href="#"]').click(function(e) {
        e.preventDefault();
    });
}

function cancel() {
    href_a();
    $('#reply_update').attr('readonly',true);
    $('#reply_update').css('background','#faf8f8');

    $('#update').css("display",'inline');
    $('#delete').css("display",'inline');
    $('#check').css("display",'none');
    $('#cancel').css("display",'none');



}