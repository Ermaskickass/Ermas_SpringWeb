<!doctype html>
<!-- p.358 [리스트 13.8] 로그인 성공 화면 수정 -->
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>글 삭제</title>
</head>
<body>
	<p>${sessionScope.MEMBER.name }님, 글을 삭제하였습니다.</p>
	<p>
		<a href="./app/article/list">게시으로 가기</a>
	</p>
</body>
<body>판