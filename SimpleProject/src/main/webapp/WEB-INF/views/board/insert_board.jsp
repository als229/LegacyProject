<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시글 입력 페이지지</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245); 
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #enrollForm>table {width:100%;}
        #enrollForm>table * {margin:5px;}
        #img-area{
            width : 100%;
            margin : auto;
            text-align: center;
        }
        #img-area > img{
            width : 80%;
        }
    </style>
</head>
<body>
        
    <jsp:include page="../include/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 작성하기</h2>
            <br>

            <form id="enrollForm" method="post" action="boards" enctype="multipart/form-data">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${ sessionScope.loginMember.memberId }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td><input onchange="changeImg(this);" type="file" id="upfile" class="form-control-file border" name="upfile"></td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required></textarea></td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <div id="img-area">
                                <img src="https://t1.kakaocdn.net/friends/www/talk/kakaofriends_talk_2018.png" alt="">
                            </div>
                        </th>
                    </tr>
                </table>
                <br>
    
			    <script>
			    	function changeImg(file){
			    		/* console.log(file.files[0]); */
			    		/* files : 업로드된 파일의 정보가 들어있는 객체 */
			    		/* file.files.length = 1 : 파일 선택됨 */
			    		/* file.files.length = 0 : 파일 선택취소함 */
			    		/* file.files[0] 하면 첫번째 객체가 담김 => 거기에 파일 정보들이 담겨 있음 */

			    		/* falsy한 값 = 0, 0.0 , undefined , NaN */
			    		const imgEl = document.querySelector('#img-area>img');
			    		if(file.files.length){
			    			const reader = new FileReader();
			    			reader.readAsDataURL(file.files[0]);
			    			
			    			reader.onload = function(e){
			    				const url = e.target.result;
			    				
			    				imgEl.src = url;
			    			}
			    		}else{
			    			imgEl.src = "";
			    		}
			    	}
			    </script>
                <div align="center">
                    <button type="submit" class="btn btn-primary">등록하기</button>
                    <button type="reset" class="btn btn-danger">취소하기</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>

    
    <jsp:include page="../include/footer.jsp" />
    
</body>
</html>