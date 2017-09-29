<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Professional Profile</title>
</head>
<body style="font-family:arial">
	<a href="/dash"><button>All Users</button></a>
	<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <h1>Hi <u style="color:blue"><a href="/info/${currentUser.id}"><c:out value="${currentUser.name}"/></a></u>!</h1>
    <fieldset style="width: 350px">
    <legend>Here is your profile description:</legend>
    	<p>${currentUser.description}</p>
    </fieldset><br>
    
    <fieldset style="width: 350px">
    <legend>Your Professional Network:</legend>
    	<c:forEach items="${added}" var="a">
    		<p><a href="/info/${a.id}"><c:out value="${a.name}"/></a></p>
    	</c:forEach>
    </fieldset><br>
    
    <h3>The following people asked to be their Network:</h3>
    <table border="1">
    <tr>
    	<th>Name</th>
    	<th>Actions</th>
    </tr>
    <c:forEach items="${requested}" var="r">
    <tr>
    	<td><a href="/info/${r.id}"><c:out value="${r.name}"/></a></td>
    	<td><a href="/add/${currentUser.id}/${r.id}">Accept Invite</a> | <a href="/ignore/${currentUser.id}/${r.id}">Ignore</a></td>
    </tr>
    </c:forEach>
    </table>
    
</body>
</html>