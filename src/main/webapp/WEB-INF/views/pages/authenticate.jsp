<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 25-11-2020
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="api.Util" %>
<div>
    <div class="container">
        <section>
            <h1>Log ind</h1>
            <form method="post">
                <div class="row">
                    <div class="form-group col">
                        <label for="email">E-mail</label>
                        <input class="form-control" name="email" id="email" type="email">
                    </div>
                    <div class="form-group col">
                        <label for="password">Password</label>
                        <input class="form-control" name="password" id="password" type="password">
                    </div>
                </div>
                <input type="submit" class="btn btn-primary" value="Log ind">
            </form>
        </section>
    </div>
</div>