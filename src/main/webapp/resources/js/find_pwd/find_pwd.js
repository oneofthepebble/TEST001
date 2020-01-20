//비밀번호 찾기 헤더 이미지 변경
$(function (){
    // .attr()은 속성값(property)을 설정할 수 있다.
      $('#header').attr('style', 'background: url(/resources/img/login_wallpaper.jpg); background-position:top; background-size:cover;');
});

function patterkorean() {
    var name=$('#user_name').val();
    var checkpattern=/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/

    if (checkpattern.test(name)==false){
        $('#user_name').css("border","2px solid red");
        $('#namelabel').text('한글 2~3 글자로 입력해주세요!');
        $('#namelabel').show();
    }else{
        $('#user_name').css("border","1px solid grey");
        $('#namelabel').hide();
    }

    if(name==''){
        $('#user_name').css("border","1px solid grey");
        $('#namelabel').hide();
    }
}

function patternid() {
    var name=$('#user_id').val();
    var checkpattern=/[a-z|A-Z|]{8}[0-9]{4}/
    if(checkpattern.test(name)==false){
        $('#idlabel').css("color",'red');
        $('#idlabel').text('영문 8자 숫자 4자 12자이상으로 입력해주세요');
        $('#user_id').css("border","2px solid red");
        $('#idlabel').show();
    }else{
        $('#idlabel').hide();
        $('#user_id').css("border","1px solid grey");
    }

    if(name==''){
        $('#idlabel').hide();
        $('#user_id').css("border","1px solid grey");
    }
}

//휴대폰 입력시 - 붙히기
function inputPhoneNumber(obj) {
    var number = obj.value.replace(/[^0-9]/g, "");
    var phone = "";



    if(number.length < 4) {
        return number;
    } else if(number.length < 7) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3);
    } else if(number.length < 11) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 3);
        phone += "-";
        phone += number.substr(6);
    } else {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 4);
        phone += "-";
        phone += number.substr(7);
    }
    obj.value = phone;
}



//문자발송
function phonecodesend() {
    $user_phone=$('#user_phone').val();
    $('#phone').val($user_phone)

    if ($user_phone==''){
        alert('전화번호를 입력해주세요');
        $('#user_phone').focus();
        return false;
    }
    console.log($user_phone);
    $.ajax({
        type:"POST",
        url:"codesend",
        data: {"user_phone":$user_phone},
        datatype:"String",
        success: function (data) {
            if(data==''){
                $('#phonelabel').css("color","red");
                $('#phonelabel').text('등록되지않는 연락처입니다!');
                $('#phonelabel').show();
                return false;
            }else{
                $('#phonelabel').css("color","green");
                $('#phonelabel').text('인증번호를 전송하였습니다');
                $('#phonelabel').show();

                $('#user_phone').attr('disabled',true);

                $('#user_phone_code').removeAttr("disabled");
                $('.hiddencode').removeAttr("disabled")

                $('#code').val(data);
                console.log($('#code').val())

                return false;
            }
        },
        error:function(){
            alert("data error");
            // location='find_id'
        }
    });//$.ajax
}

//코드 확인
function code_check() {
    var code=$('#code').val();
    var codecheck=$('#user_phone_code').val();

    if(code==codecheck){
        $('.codelabel').css('color','green');
        $('.codelabel').text('인증되었습니다');
        $('.codelabel').show();

        $('#codesend').attr("disabled",true);

        $('#user_phone_code').attr('disabled',true);
        $('.hiddencode').attr('disabled',true);
    }else{
        $('.codelabel').css('color','red');
        $('.codelabel').text('인증번호가 일치하지 않습니다');
        $('.codelabel').show();

    }
}


function find_p() {
    if ($('.hiddencode').attr("disabled")=='undefined'){
        alert('인증부터 해주세요!');
        return false;
    }

    if ($('#user_phone_code').val()==''){
        alert('인증번호를 입력해주세요');
        return false;
    }

    if($('#user_phone_code').attr("disabled")!='disabled'){
        alert('인증 해주세요');
        return false;
    }

}