<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
</head>
<body>
	<h1>AJAX</h1>
	
	<jsp:include page="../include/header.jsp" />
	
	<pre>
		Asynchronous Javascript And XML
		서버로부터 데이터를 응답 받아서 전체 페이지를 새롭게 렌더링하지 않고,
		일부만 새롭게 갱신할 수 있는 기법!
		
		참고로 기존에 우리가 a 태그 / location.href / form 태그를 이요한 요청 == 동기식 요청
		=> 응답 페이지 전체가 돌아와야만 화면을 새롭게 만들어낼 수 있음
		
		* 동기식 / 비동기식 요청 차이
		
		- 동기식 : 요청 처리 후 응답 HTML 데이터를 받아 화면에 렌더링 한 뒤에만 작업이 가능
				   만약에 서버에서 응답데이터를 돌려주는 시간이 지연되면 무작정 기다림
				   전체 화면이 새로고침됨 => 페이지가 깜빡
		
		- 비동기식 : 현재 페이지는 그대로 유지하면서 중간중간 추가적인 요청을 보낼 수 있음
		 			 응답이 돌아온다고 해서 다른 페이지로 넘어가지 않음(페이지가 유지)
		 			 요청을 보내놓고 응답이 올 때 까지 다른 작업을 할 수 있음
		
		사용 예시) 아이디 중복체크 기능, 검색어 자동완성, 댓글
		
		** 추가로 **
		모든 요청 및 응답 처리를 AJAX로 구현
		
	</pre>
	
	<pre>
		* 실전 압축 jQuery 사용법	
		
		1. 요서 선택
		document.querySelector('CSS 선택자');ㅣ

		===
		
		$('CSS 선택자');	
	
		2. 이벤트	
		.addEventListner('click', () => {
			이벤트 발생 시 수행할 코드
		})
		
		===
		
		.click(() => {
			이벤트 발생 시 수행할 코드
		})
		
		
	</pre>
	
	<h3 id="h3">접근을 해보자</h3>
	
	<script>
	/*		window.onload = function(){
  			var h3El = document.querySelector('#h3'); 
			
			console.log(h3El);
			h3El.addEventListener('click' , () =>{
				alert('호호');
			})
			
			h3El.innerHTML = '<h1> 멍멍 </h1>';
			
			/* 위는 하던거 === 밑은 jquery */
/* 				
			var h3El = $('#h3');
			h3El.click(() => {
				
				alert('호호');
			}); */
	/* 		
			h3El.html('<h1>멍멍</h1>');
			h3El.css('background', 'red'); */
			
			/* 이걸 메서드 체인으로 붙일 수 있음 */
			
/* 						let h3El = $('#h3');
			h3El.click(() => {
				
				alert('호호');
			}).html('<h1>멍멍</h1>').css('background', 'red');
			console.log(h3El);
			 
		} */
	
	</script>
	
	<pre>
		* jQuery 를 사용한 AJAX 통신
		
		[ 표현법 ]
		$.ajax({
			속성명 : 값;
			속성명 : 값;
			속성명 : 값;
			...
		});
		
		이거 4개는 알고 있자!
		
		* 주요 속성
		
		- type : 요청 전성 방식(GET/POST ...PUT, DLELTE)
				GET 방식 	: 조회 요청(SELECT)
				POST 방식 	: 데이터 생성 요청(INSERT)
				PUT 방식 	: 데이터 갱신 요청(UPDATE)
				DELETE 방식 : 데이터 삭제 요청(DELETE) 
		- url : 요청할  URL(필수 작성) => form 태그로 따지면 action 속성
		- success : AJAX 통신 성공 시 실행할 함수를 정의
		- data : 요청 시 전달값({ 키 : 밸류 }) => form 태그의 input 요소 입력 값
	</pre>
	
	<hr>
	
	<h2>jQuery 방식으로 AJAX 로 요청을 보내고 응답을 받아서 화면상에 출력</h2>
	
	<h3>1. 버튼을 클릭 했을 때 GET 방식으로 서버에 데이터를 전송하고 응답을 받아보기</h3>
	
	<div class="form-group">
		<div class="form-control">
			입력 : <input type="text" id="ajax-input">
		</div>
		<div class=="form-control">
			<button class="btn btn-sm btn-primary" id="ajax-btn">AJAX로 요청 보내기!</button>
		</div>
	</div>
	
	응답 : <label id="result">현재 응답 없음</label>
	
	
	<jsp:include page="../include/footer.jsp" />
	
	<!-- 
		우리의 계획 :
		사용자가 입력이라고 써 있는 input 요소에 값을 입력해서
		AJAX로 요청보내기 버튼을 누르면
	
		AJAX 요청을 보내서
		
		요청을 받아 처리하는 RequestHandler가 값을 받아서 응답을 해주고
		라벨 요소의 Content 영역에 응답받은 데이터를 출력할 것
	 -->
	 
	 <script>
	 	$('#ajax-btn').click(() => {
	 		
	 		const inputValue = $('#ajax-input').val(); 
	 		
	 		$.ajax({
	 			url : `test?input=\${inputValue}`,
	 			type : 'get',
	 			success : function(result){
	 				
	 				$('#result').text(result);
						 				
	 			}
	 			
	 		});
	 		
	 		
	 	});
	 </script>
	 
	 <hr>
	 
	 <h3>VO 단일 객체를 조회해서 출력해 보기</h3>
	 
	 <div>
	 	댓글 번호 : <p id="title"></p>
	 	댓글 작성자 : <p id="writer"></p>
	 	댓글 내용 : <p id="content"></p>
	 	댓글 작성일 : <p id="date"></p>
	 </div>
	 
	 댓글 번호 :  <input type="text" id="replyNo">
	 <button onclick="selectReply()">댓글 보여주세용~</button>
	 
	
</body>
</html>