<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>수정하기!</title>
<style>
 #updateForm>table {width:100%;}
 #updateForm>table * {margin:5px;}
</style>
</head>
<body>
	<jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 수정하기</h2>
            <br>

            <form id="updateForm" method="post" action="" enctype="multipart/form-data">
            	<input type="hidden" name="boardNo" value="100">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" value="게시글제목" name="" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="게시글내용" name="" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td>
                            <input type="file" id="upfile" class="form-control-file border" name="">
                            
                            
                            	현재 업로드된 파일 : 
                            <a href="" download="">이미지.jpg</a>
                       
                            
                           
                        </td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="" required>내용입니다</textarea></td>
                    </tr>
                </table>
                <br>

                <div align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="reset" class="btn btn-danger">이전으로</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />

</body>
</html>