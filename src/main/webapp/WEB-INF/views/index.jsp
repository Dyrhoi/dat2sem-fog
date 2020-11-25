<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 25-11-2020
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form method="post" action="${pageContext.request.contextPath}" id="order_form">
    <h1>Dimensioner</h1>
    <label for="width">Carport bredde</label>
    <input type="range" id="width" value="480" step="30" min="240" max="750">
    <label for="length">Carport længde</label>
    <input type="range" id="length" value="480" step="30" min="240" max="780">

    <h1>Overdække</h1>
    <label for="flat">Carport med fladt tag</label>
    <input type="radio" id="flat" name="roof" checked="checked">
    <label for="angle">Carport med rejsning</label>
    <input type="radio" id="angle" name="roof">

</form>
