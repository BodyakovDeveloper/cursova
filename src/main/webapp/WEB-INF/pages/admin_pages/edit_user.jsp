<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 10.02.22
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <style><%@ include file="/WEB-INF/styles/add_edit_user.css"%></style>
    <script>
        let checkPassword = function() {
            const message = document.getElementById('message');
            if (document.getElementById('password').value === document.getElementById('confirm_password').value) {

                message.style.color = 'green';
                message.innerHTML = 'matching';
                return true;
            }
            else {
                message.style.color = 'red';
                message.innerHTML = 'not matching';
                return false;
            }
        };

        let checkInput = function() {
            const button = document.getElementById('okButton');
            button.disabled = !(checkPassword()
                && !(document.getElementById('login').value === ""
                    || document.getElementById('lastName').value === ""
                    || document.getElementById('firstName').value === ""
                    || document.getElementById('email').value === ""
                    || document.getElementById('select').value === ""));
        }
    </script>
</head>
<body onload="checkInput()">
<form method="post" action="${pageContext.request.contextPath}/admin/edit_user" modelAttribute="userToEdit">
    <div class="title">Add User</div>
    <div class="subtitle">Let's create new user!</div>

    <c:if test="${errorMessage ne null}">
        <div class="alert-error" id="error">${errorMessage}</div>
    </c:if>

    <div class="input-container ic1">
        <input id="login" class="input" type="text" value="${userToEdit.login}" name="login" readonly/>
        <div class="cut"><div class="cutLogin"></div></div>
        <label for="login" class="placeholder">Login</label>
    </div>

    <div class="input-container ic2">
        <input id="password" class="input" type="password" placeholder=" " name="password" onkeyup='checkInput();'/>
        <div class="cut"><div class="cutPassword"></div></div>
        <label for="password" class="placeholder">Password</label>
    </div>

    <div class="input-container ic2">
        <input id="confirm_password" class="input"  type="password" name="confirm_password"
               onkeyup='checkInput();' placeholder=" " />
        <div class="cut"><div class="cutConfirmPassword"></div></div>
        <label for="password" class="placeholder">Confirm password</label>
        <span id='message' style="float:right;"></span>
    </div>

    <div class="input-container ic2">
        <input id="email" class="input" type="email" placeholder=" " value="${userToEdit.email}"
               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2, 4}$" name="email" onkeyup='checkInput();' required/>
        <div class="cut"><div class="cutEmail"></div></div>
        <label for="email" class="placeholder">Email</label>
    </div>

    <div class="input-container ic2">
        <input id="firstName" class="input" type="text" placeholder=" " value="${userToEdit.firstName}"
               pattern="[a-zA-Z]{2,15}" title="Only letters between 2 and 15" name="firstName" onkeyup='checkInput();' required/>
        <div class="cut"><div class="cutFirstName"></div></div>
        <label for="firstname" class="placeholder">First name</label>
    </div>

    <div class="input-container ic2">
        <input id="lastName" name="lastName" class="input" type="text" placeholder=" " value="${userToEdit.lastName}"
               pattern="[a-zA-Z]{2,15}" title="Only letters between 2 and 15" onkeyup='checkInput();' required/>
        <div class="cut"><div class="cutLastName"></div></div>
        <label for="lastname" class="placeholder">Last name</label>
    </div>

    <div class="input-container ic2">
        <input id="birthday" name="birthday" class="input" type="date" placeholder=" " value="${userToEdit.birthday}"
               min="1930-01-01" max="2022-12-31" required/>
    </div>

    <div class="input-container ic2">
        <select name="roleName" class="input" id="select" onChange="checkInput();">
            <option value="">Select role</option>
            <c:forEach items="${roles}" var="roleEntity">
                <option value="${roleEntity.name}">${roleEntity.name}</option>
            </c:forEach>
        </select>
    </div>

    <input type="submit" value="ok" class="submit" id="okButton"/>
    <input type="button" value="cancel" class="cancel" onclick="document.location.href='/admin/main_page'">
</form>
</body>
</html>