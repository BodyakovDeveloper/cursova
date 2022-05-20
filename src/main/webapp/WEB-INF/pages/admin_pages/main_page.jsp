<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 09.02.22
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <style><%@ include file="/WEB-INF/styles/admin_page.css"%></style>
    <title>Admin page</title>
</head>
<body>
    <h2>Admin ${userName}(<a href="/logout" onclick="return confirm('Are you sure?')">Logout</a>) </h2>
    <div class="add_new_user"><a href="/admin/add_user">Add new user</a> </div>

    <table class="container">
        <thead>
            <tr>
                <td>login</td>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Email</td>
                <td>Birthday</td>
                <td>Role</td>
                <td>Action</td>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.getLogin()}</td>
                    <td>${user.getFirstName()}</td>
                    <td>${user.getLastName()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getBirthday()}</td>
                    <td>${user.getRoleEntity().getName()}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/admin/edit_user">
                            <button type="submit" value="${user.getLogin()}" name="login" class="btn-edit">edit</button>
                        </form>
                        <form method="get" action="${pageContext.request.contextPath}/admin/delete_user">
                                <button type="submit" value="${user.getLogin()}" onclick="return confirm('Are you sure?')" name="login"
                                <c:if test="${user.getRoleEntity().getName() == 'ADMIN'}">disabled</c:if> class="btn-delete">delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>