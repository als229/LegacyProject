<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공공데이터 활용하기</title>
</head>
<body>

	<jsp:include page="../include/header.jsp"/>
	
	<button class="btn btn-sm btn-info" onclick="callHospital();">병원 정보 보기</button>
	<div id="result-div" style="width : 1000px; min-height : 600px; margin: auto;">
		<table class="table">
			<thead>
				<tr>
					<th width="120">병원명(INST_NM)</th>
					<th width="300">주소지(ADDR)</th>
					<th width="300">오시는길(ESNS_RGHMP)</th>
					<th width="150">전화번호(RPRS_TLHN_1)</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
		
	</div>
	<script>
		function callHospital(){
			$.ajax({
				url : 'hospitals',
				type : 'get',
				success :result => {
					const hospitals = result.body;
					const resultEl = hospitals.map(e =>
					`
						<tr>
							<td>\${e.INST_NM}</td>
							<td>\${e.ADDR}</td>
							<td>\${e.ESNS_RGHMP}</td>
							<td>\${e.RPRS_TLHN_1}</td>
						</tr>
					`
					).join('');
					
					document.querySelector('tbody').innerHTML = resultEl;
				}
			});
		}
	</script>
	

	<jsp:include page="../include/footer.jsp"/>
</body>
</html>