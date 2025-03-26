<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>안녕 나는 쇼핑페이지야....</title>
<style>
	#items{
		padding: 40px;
		width : 1000px;
		margin : auto;
		display : flex;
		flex-wrap : wrap;
		gap : 15px;
	}
	
</style>
</head>
<body>

	<jsp:include page="../include/header.jsp" />

	<h1>상품을 검색해주세유</h1>
	
	<input type="text" id="query">
	
	<button onclick="search();">뭐가궁금함?</button>

	<div id="items">
		
	</div>

	<script>
		function search(){
			
			const query = document.querySelector("#query").value;
			
			$.ajax({
				url : `naver-shopping?query=\${query}`,
				type : 'get',
				success : result =>{
					console.log(result);
					
					const items = result.items;
					
					const item = items.map( e => `
						<div style="width : 280px; height : 450px; padding : 10px; display:inline-block">
							<h5>\${e.brand}</h5>
							<br/>
							<p>\${e.title}</p>
							<img src="\${e.image}" style="width : 200px; height : 150px" /> <br/>
							<a href="\${e.link}" target="_blank">보러가기....</a>
						</div>
					`).join('');
					
					document.querySelector('#items').innerHTML = item;
				}
			});
		}
		
	</script>
	<jsp:include page="../include/footer.jsp" />

</body>
</html>