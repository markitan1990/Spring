<%--
  Created by IntelliJ IDEA.
  User: 100nout.by
  Date: 17.05.2020
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of cars</title>
</head>
<style>
    .carTable{
        width: 80%;
        margin: 20px auto;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
    }
    .row:nth-child(odd){
        background: beige;
    }
    .row:first-child{
        background: #fff;
    }
    .row{
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .row div{
        width: 34%;
        border: 1px solid black;
        padding: 10px;
    }
     .head{
      background: antiquewhite;
    }
    .head div b {
        font-size: 20px;
    }

</style>
<body>
<div class="carTable">
    <div class="row">
        <h2>${head}</h2>
    </div>
    <div class="row head">
        <div><b>${brand}</b></div>
        <div><b>${model}</b></div>
        <div><b>${series}</b></div>
    </div>
    <c:forEach var="cars" items="${cars}">
        <div class="row">
            <div>${cars.brand}</div>
            <div>${cars.model}</div>
            <div>${cars.series}</div>
        </div>
    </c:forEach>
</div>
</body>
</html>
