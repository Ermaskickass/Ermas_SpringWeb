<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>게시판</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>글 보기</h2>
	<p>
		<a href="./app/letter/delete?letterId=${letter.letterId}">편지 삭제</a>
	</p>
	<hr />
	<p>
		<span>${letter.letterId }</span> | <span style="font-weight: bold;">${letter.title }</span>
	</p>
	<p>
		<span>${letter.cdate }</span> | 
		<span>${letter.senderName }, ${letter.senderId }</span>
		<span>${letter.receiverName }, ${letter.receiverId }</span>
	</p>
	<hr />
	<p>${letter.contentHtml}</p>
	<hr />
</body>
</html>