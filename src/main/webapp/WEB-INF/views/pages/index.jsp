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
    <div class="form-horizontal col-md-12 col-xs-12">
        <div id="carportDimensions" class="from-group row">
            <div class="col">
                <div class="d-flex">
                    <label class="col-form-label" for="width">Carport bredde</label>
                    <span class="ml-auto col-form-label range-label" data-range="carport-width"><span>480</span>cm</span>
                </div>
                <input class="custom-range" name="carport-width" type="range" id="width" value="480" step="30" min="240" max="750">
            </div>
            <div class="col">
                <div class="d-flex">
                    <label class="col-form-label" for="length">Carport længde</label>
                    <span class="ml-auto col-form-label range-label" data-range="carport-length"><span>480</span>cm</span>
                </div>
                <input class="custom-range" name="carport-length" type="range" id="length" value="480" step="30" min="240" max="780">
            </div>
        </div>

        <div id="rooftype">
            <h1>Overdække</h1>
            <div class="form-group row">
                <div class="col">
                    <input type="radio" hidden checked required id="car-type-flat" name="roof-type" value="flat">
                    <div class="image-radio" data-radio="flat">
                        <div class="image-radio--image" style="background-image: none"></div>
                    </div>
                    <label for="car-type-flat">
                        Go flat
                        <span class="d-block">Dette er et flat tag</span>
                    </label>
                </div>
                <div class="col">
                    <input type="radio" hidden required id="car-type-angled" name="roof-type" value="angled">
                    <div class="image-radio" data-radio="angled">
                        <div class="image-radio--image" style="background-image: none"></div>
                    </div>
                    <label for="car-type-angled">
                        Go angled
                        <span class="d-block">Dette er et angled tag</span>
                    </label>
                </div>
            </div>
        </div>
        <div id="toolshed">
                <label class="col-form-label" for="shedCheckbox">Med redskabsskur?</label>
                <input class="col-form-label" type="checkbox" id="shedCheckbox">
                <div id="toolshedDimensions" class="form-group row">
                    <div class="col">
                        <div class="d-flex">
                            <label class="col-form-label" for="shed-width">Redskabsrum bredde</label>
                            <span class="ml-auto col-form-label range-label" data-range="shed-width"><span>210</span>cm</span>
                        </div>
                        <input class="custom-range" name="shed-width" type="range" id="shed-width" value="210" step="30" min="210" max="780">
                    </div>
                    <div class="form-group col">
                        <div class="d-flex">
                            <label class="col-form-label" for="shed-length">Redskabsrum længde</label>
                            <span class="ml-auto col-form-label range-label" data-range="shed-length"><span>150</span>cm</span>
                        </div>
                        <input class="custom-range" name="shed-length" type="range" id="shed-length" value="150" step="30" min="150" max="690">
                    </div>
                </div>
        </div>
        <div id="userInfo">
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
    </div>
</form>