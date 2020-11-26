<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 25-11-2020
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form method="post" action="${pageContext.request.contextPath}" id="order_form" class="bg-light">
    <h1>Dimensioner</h1>
    <div id="carportDimensions" class="from-group row pl-2">
        <div class="col">
            <label class="col-form-label" for="width">Carport bredde</label>
            <br>
            <input class="col-11" type="range" id="width" value="480" step="30" min="240" max="750">
        </div>
        <div class="col">
            <label class="col-form-label" for="length">Carport længde</label>
            <br>
            <input class="col-11" type="range" id="length" value="480" step="30" min="240" max="780">
        </div>
    </div>

    <div id="rooftype">
        <h1>Overdække</h1>
        <label for="flat">Carport med fladt tag</label>
        <input type="radio" id="flat" name="roof" checked="checked">
        <label for="angle">Carport med rejsning</label>
        <input type="radio" id="angle" name="roof">
    </div>
    <div id="toolshed">
        <div>
            <label class="col-form-label" for="shedCheckbox">Med redskabsskur?</label>
            <input class="col-form-label" type="checkbox" id="shedCheckbox">
            <div id="toolshedDimensions">
                <div class="form-group-row">
                    <label class="col-form-label" for="shedWidth">Redskabsrum bredde</label>
                    <br>
                    <input type="range" id="shedWidth" value="210" step="30" min="210" max="780">
                </div>
                <div class="form-group row">
                    <label class="col-form-label" for="shedLength">Redskabsrum længde</label>
                    <br>
                    <input type="range" id="shedLength" value="150" step="30" min="150" max="690">
                </div>
            </div>
        </div>
    </div>
    <div id="userInfo" class="form-horizontal col-md-12 col-xs-12">
        <div>
            <label class="" for="name">Navn</label>
            <input class="form-control" type="text" id="name">
        </div>
        <div>
            <label for="address">Adresse</label>
            <input class="form-control" type="text" id="address">
        </div>
        <div>
            <label for="city">By</label>
            <input class="form-control" type="text" id="city">
        </div>
        <div>
            <label for="postalcode">Postnummer</label>
            <input class="form-control" type="text" id="postalcode">
        </div>
        <div>
            <label for="phonenumber">Telefon</label>
            <input class="form-control" type="text" id="phonenumber">
        </div>
        <div>
            <label for="email">E-mail adresse</label>
            <input class="form-control" type="text" id="email">
        </div>
        <div>
            <label for="comment">Evt. bemærkninger</label>
            <textarea class="form-control" id="comment"></textarea>
        </div>
    </div>
        <input type="submit" value="Send forespørgsel"/>
</form>