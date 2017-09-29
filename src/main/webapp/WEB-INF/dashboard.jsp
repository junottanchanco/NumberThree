<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
</head>
<body style="font-family:arial">
	<a href="/"><button>My Profile</button></a>
	<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form><br>
	<h1>Users you may want to connect with:</h1>
	<table border="1">
		<tr>
			<th>Name</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${friends}" var="friend">
		<c:if test="${friend.id != currentUser.id}">
		<tr>
			<td><a href="/info/${friend.id}"><c:out value="${friend.name}"/></a></td>
			<td><a href="/connect/${currentUser.id}/${friend.id}">Connect</a></td>
		</tr>
		</c:if>
		</c:forEach>
	</table>
</body>
</html>