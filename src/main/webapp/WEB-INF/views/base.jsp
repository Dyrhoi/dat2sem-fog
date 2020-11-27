<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 26/10/2020
  Time: 13.24
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title>${requestScope.get("title")}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons+Round" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/style.css?fc=<%= Math.random() %>">
</head>

<body>
<div class="container">
    <!--Header-->

    <!--Nav Bar-->

    <!--content-->
    <main>
        <jsp:include page="/WEB-INF/views/pages/${requestScope.content}.jsp" />
    </main>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
<script>let contextPath = "${pageContext.request.contextPath}"</script>

<script src="${pageContext.request.contextPath}/assets/javascript/app.js?fc=<%= Math.random() %>"></script>
</body>

</html>
