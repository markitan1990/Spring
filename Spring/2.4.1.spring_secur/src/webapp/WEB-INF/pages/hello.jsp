<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<style>
    #logout{
        position: absolute;
        top: 0;
        right: 20%;
        background: red;
        color: #fff;
        padding: 15px;
        text-decoration: none;
        transition: .4s;
    }
    #logout:hover{
        background: palevioletred;
    }
</style>
<c:forEach var="msq" items="${messages}">
    <h1>${msq}</h1>
</c:forEach>
<a href="/logout" id="logout">Logout</a>
</body>
</html>