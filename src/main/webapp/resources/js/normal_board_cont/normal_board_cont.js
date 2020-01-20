//일반게시판 분양 헤더 이미지 변경
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/QandA/board_wallpaper02.jpg); background-position:bottom; background-size:cover;');
  });

function reply_ok() {
    var id=$('#normal_id').val();
    var cont=$('#normal_cont').val();
    var no=$('#normal_no').val();

    console.log(id);
    console.log(cont);
    console.log(no);


     $.ajax({
        type:"POST",
        url:"normal_board_list_cont_reply_ok",
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

function reply_del_ok(a,b) {
    var conF=confirm('정말 삭제하시겠습니가?')
    if(conF==true){
    var id=a;
    var no=b;

    console.log(id);
    console.log(no);


     $.ajax({
        type:"POST",
        url:"reply_del_ok",
        data: {"normal_id":a,"normal_no":b},
        datatype:"int",
        success: function (data) {
            if(data!=1){
                alert('댓글 삭제 실패!')
                return false;
            }else{
                // $('#reply_list_div').load(window.location.href+"#reply_list_div");
                location.reload();
            }
        },
        error:function(){
            alert("data error");
            return false;
            // location='find_id'
        }
    });//$.ajax
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