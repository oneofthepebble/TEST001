<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cats&Dogs</title>
<!-- style -->
<link rel="stylesheet" href="/css/reset.css">
<link rel="stylesheet" href="/css/include/header.css">

<!-- jquery -->
<script src="/resources/js/jquery.js"></script>
<script src="/js/include/menu_floting.js"></script>

</head>
<body>

	<header id="header" style="background: url(/resources/img/login_wallpaper.jpg);">
		
			<div class="header" >
				<div class="header_menu">
					<c:if test="${!empty user_id}">
						<a id="mypage_cont" href="/mypage">MyPage</a>
						<div id="mypage_hover">
							<p><a href="/shop/basket_list?page=1">장바구니</a></p>						
						</div>
					</c:if>
					
					
					
					<c:if test="${empty user_id}">
					 <a href="/login">Login</a>
					 </c:if>
					 <c:if test="${!empty user_id}">
					 <a href="/logout">LogOut</a>
					 </c:if>
					 <c:if test="${user_id == null }">
					 <a href="/memberjoin">Register</a>
					 </c:if>
				</div>
				<!-- //header_menu -->
				<div class="header_title">
					<a href="/"><img class="title_img" src="/img/title.png" /></a>
				</div>
				<!-- //header_title -->
			</div>
		
	</header>
	<!-- //header -->

	<article id="title">
			<div class="title" id="menu">
				<ul class="category">
					<li><div>
							<a href="/cat/total_cat?page=1" >고양이 분양</a>
						</div>
						<ul class="sub_category">
							<li><a href="/cat/cat_mun_list?page=1">먼치킨</a></li>
							<li><a href="/cat/cat_shiam_list?page=1">샴</a></li>
							<li><a href="/cat/cat_fold_list?page=1">폴드</a></li>
							<li><a href="/cat/cat_persian_list?page=1">페르시안</a></li>
						</ul></li>
					<li><div>
							<a href="/dog/total_dog?page=1" >강아지 분양</a>
						</div>
						<ul class="sub_category">
							<li><a href="/dog/dog_shih_list?page=1">시츄</a></li>
							<li><a href="/dog/dog_mal_list?page=1">말티즈</a></li>
							<li><a href="/dog/dog_poodle_list?page=1">푸들</a></li>
						</ul></li>
					<li><div>
							<a href="/shop/total_shop?find_field=item_name&find_name=">SHOP</a>
						</div>
						<ul class="sub_category">
						</ul></li>
					<li><div>
							<a href="/user_board_list?page=1">고객센터</a>
						</div>
						<ul class="sub_category">
							<li><a href="/user_board_list?page=1">분양 게시판</a></li>
							<li><a href="/normal_board_list">일반 게시판</a></li>
							<li><a href="/QandA/QandA_list?page=1">Q&A</a></li>
						</ul></li>
				</ul>
			</div>
			
	</article>
	
	
	<a id="move_top_btn" href="#"><img class="top_btn" src="/img/top_btn01.png" /></a>
	
	<a id="store" href="/Company"><img class="company_img" src="https://cdn.imweb.me/upload/S201807025b39d1981b0b0/b14a1347eb1ce.png"></a>
