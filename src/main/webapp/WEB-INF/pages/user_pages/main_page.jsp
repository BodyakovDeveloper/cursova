<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 04.02.22
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <style><%@ include file="/WEB-INF/styles/user_page.css"%></style>
    <title>User page</title>
</head>
<body>

<h1>Hello, ${userName}!</h1>

<div class="logout">
    You can <a href="<c:url value="/logout"/>" onclick="return confirm('Are you sure?')">logout</a> =)
</div>
</body>
</html>