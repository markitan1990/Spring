<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>ListUsers</title>
</head>
<style>
    form {
        display: flex;
        align-items: center;
    }

    input {
        margin: 5px;
    }

    .Users {
        display: flex;
        flex-direction: column;
        width: 80%;
        margin: 0 auto;
        align-items: center;
        justify-content: center;
    }

    .row {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        height: 70px;
    }

    .col {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        width: 20%;
        height: 60px;
        border: 1px black solid;
        text-align: center;
    }

    .col button {
        width: 70%;
        margin: 3px;
    }

    #modal {
        position: absolute;
        width: 100%;
        height: 100%;
        opacity: .5;
        top: 0;
        left: 0;
        background: rgba(0, 0, 0, 0);
        pointer-events: none;
        transition: 0.5s;
    }

    #modal.on {
        background: rgba(0, 0, 0, 0.5);
        transition: 0.5s;
        pointer-events: auto;
    }

    #close {
        position: absolute;
        z-index: 3;
        top: -100%;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        width: 30%;
        background: white;
        border: 1px solid;
        box-shadow: 8px 7px rgba(0, 0, 0, 0.4);
        height: 40%;
        transition: 0.5s;
        margin: 0 auto;
        left: 0;
        right: 0;
    }

    .col form {
        width: 100%;
        display: flex;
        justify-content: center;
        margin-bottom: 0px;
        align-items: center;
    }

    #close.on {
        top: 20%;
        transition: 0.5s;

    }

    #id {
        opacity: 0;
    }

    #close form {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background: #fff;
    }

    #logout {
        position: absolute;
        top: 0;
        right: 20%;
        background: red;
        color: #fff;
        padding: 15px;
        text-decoration: none;
        transition: .4s;
    }

    #logout:hover {
        background: palevioletred;
    }

    #invisibleForm {
        width: 0;
        height: 0;
        opacity: 0;
        pointer-events: none;
    }

    #close form .inp {
        width: 50%;
        margin: 15px;
    }

    .combobox {
        margin: 4px 7px;

    }

    .formUser div {
        width: 160px;
    }

    .rolescl div:nth-child(3) {
        width: 0px;
        opacity: 0;
        pointer-events: none;
    }

    .rolescl {
        display: flex;
        align-items: center;
    }
</style>
<script>
    var ar;

    function editFunction(val) {
        ar = val.value;

        var el = document.getElementById("modal");
        var el2 = document.getElementsByClassName(ar)[0];
        el.classList.toggle("on");
        el2.classList.toggle("on");

    }

    function modalOff() {
        var el = document.getElementById("modal");
        var el2 = document.getElementsByClassName(ar)[0];
        el.classList.toggle("on");
        el2.classList.toggle("on");
    }

    function f(e) {
        var a = e.id;
        var f = document.getElementById('id');
        f.setAttribute('value', a);
    }

</script>
<body>
<form:form method="POST" modelAttribute="newUser" action="/admin/addUser" cssClass="formUser">
    <form:hidden path="id"/>
    <form:label path="name">Name</form:label>
    <form:input path="name"/>
    <form:label path="password">Password</form:label>
    <form:input path="password"/>
    <div class="rolescl">
        <form:checkboxes items="${userRole}" path="userRoles" element="div"/>
    </div>
    <input type="submit" value="Save"/>
</form:form>
<div class="Users">
    <div class="row">
        <div class="col">id</div>
        <div class="col">name</div>
        <div class="col">password</div>
        <div class="col">role</div>
        <div class="col">
            buttons
        </div>
    </div>

    <c:forEach var="user" items="${users}">
        <div class="row">
            <div class="col"><c:out value="${user.id}"/></div>
            <div class="col"><c:out value="${user.name}"/></div>
            <div class="col"><c:out value="${user.password}"/></div>
            <div class="col">
                <c:forEach var="grantedAuthorities" items="${user.userRoles}">
                    <c:out value="${grantedAuthorities.name}"/><br>
                </c:forEach>
            </div>
            <div class="col">
                <button id=${user.id} name="edit" value="${user.id}" onclick="editFunction(this); javascript:f(this);">
                    Edit
                </button>
                <form action="/admin/deleteUser" method="get">
                    <button name="Delete" type="submit" value=${user.id}>Delete</button>
                </form>
            </div>
            <div id="close" class="${user.id}">
                <form:form method="POST" modelAttribute="newUser" action="/admin/editUser" cssClass="formUser">
                    <form:hidden path="id" value="${user.id}" id="id" name="id"/>
                    <form:label path="name">Name</form:label>
                    <form:input path="name" type="text" class="inp" name="name" value="${user.name}"/>
                    <form:label path="password">Password</form:label>
                    <form:input path="password" type="text" class="inp" name="password" value="${user.password}"/>
                    <div class="rolescl">
                        <form:checkboxes items="${userRole}" path="userRoles" element="div"/>
                    </div>
                    <input type="submit" value="Edit" onclick="modalOff()" class="inp" name="Edit"/>
                </form:form>
            </div>
        </div>
    </c:forEach>

    <div id="modal" onclick="modalOff()">

    </div>
    <a href="/logout" id="logout">Logout</a>
</div>
</body>
</html>