<%--
  Created by IntelliJ IDEA.
  User: BodyakoV
  Date: 04.02.2022
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Authorization</title>
    <style><%@ include file="/WEB-INF/styles/index.css"%></style>
    <script>
        let checkInput = function () {
            const submitButton = document.getElementById("submit")
            submitButton.disabled = document.getElementById("login").value === ""
                || document.getElementById("password").value === "";
        }
    </script>
</head>
<body onload="checkInput()">
    <form action="login" method="post">

        <div class="title">Login</div>
        <div style="color:red;" class="message"><b>${param.message}</b></div>

        <div class="input-container ic1">
            <input type="text" placeholder="" class="input" id="login" name="username"
                   pattern="[a-z]{1,15}"
                   title="Username should only contain lowercase letters." onkeyup="checkInput()" required/>
            <div class="cut1"></div>
            <label for="login" class="placeholder">Login</label>
        </div>

        <div class="input-container ic2">
            <input type="password" placeholder="" class="input" id="password" name="password" onkeyup="checkInput()" required/>
            <div class="cut2"></div>
            <label for="password" class="placeholder">Password</label>
        </div>

        <input id="submit" class="submit" type="submit" value="Sign In">
        <button class="signup-btn" onclick="window.location.href='${pageContext.request.contextPath}/signup'">Sign up</button>
    </form>
</body>
</html>