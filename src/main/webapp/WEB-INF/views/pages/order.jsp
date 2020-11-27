<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 25-11-2020
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form method="post" action="${pageContext.request.contextPath}" id="order_form" class="">
    <section>
        <h2>Dimensioner</h2>
        <div id="carportDimensions" class="from-group row">
            <div class="col-sm">
                <div class="d-flex align-items-center">
                    <label class="" for="width">Carport bredde</label>
                    <span class="ml-auto range-label" data-range="carport-width"><span>480</span>cm</span>
                </div>
                <input class="custom-range" name="carport-width" type="range" id="width" value="480" step="30" min="240"
                       max="750">
            </div>
            <div class="col-sm">
                <div class="d-flex align-items-center">
                    <label class="" for="length">Carport længde</label>
                    <span class="ml-auto range-label" data-range="carport-length"><span>480</span>cm</span>
                </div>
                <input class="custom-range" name="carport-length" type="range" id="length" value="480" step="30" min="240"
                       max="780">
            </div>
        </div>
    </section>

    <section>
        <h2>Overdække</h2>
        <div class="form-group row">
            <div class="col-sm">
                <label for="car-type-flat">
                    Go flat
                    <span class="d-block font-weight-normal">Dette er et flat tag</span>
                </label>
                <input type="radio" hidden checked required id="car-type-flat" name="roof-type" value="flat">
                <div class="image-radio" data-radio="flat">
                    <div class="image-radio--image" style="background-image: none"></div>
                </div>
            </div>
            <div class="col-sm">
                <label for="car-type-angled">
                    Go angled
                    <span class="d-block font-weight-normal">Dette er et angled tag</span>
                </label>
                <input type="radio" hidden required id="car-type-angled" name="roof-type" value="angled">
                <div class="image-radio" data-radio="angled">
                    <div class="image-radio--image" style="background-image: none"></div>
                </div>
            </div>
        </div>
    </section>

    <section>
        <label class="col-form-label" for="shed_checkbox">Med redskabsskur?</label>
        <input class="col-form-label" type="checkbox" id="shed_checkbox">
        <div id="shed_dimensions" class="form-group row">
            <div class="col-sm">
                <div class="d-flex">
                    <label class="col-form-label" for="shed-width">Redskabsrum bredde</label>
                    <span class="ml-auto col-form-label range-label" data-range="shed-width"><span>210</span>cm</span>
                </div>
                <input class="custom-range" name="shed-width" type="range" id="shed-width" value="210" step="30"
                       min="210" max="780">
            </div>
            <div class="form-group col-sm">
                <div class="d-flex">
                    <label class="col-form-label" for="shed-length">Redskabsrum længde</label>
                    <span class="ml-auto col-form-label range-label" data-range="shed-length"><span>150</span>cm</span>
                </div>
                <input class="custom-range" name="shed-length" type="range" id="shed-length" value="150" step="30"
                       min="150" max="690">
            </div>
        </div>
    </section>

    <section>
        <div class="form-row">
            <div class="col-md form-group">
                <label class="" for="first_name">Fornavn</label>
                <input class="form-control" type="text" id="first_name" name="first-name">
            </div>
            <div class="col-md form-group">
                <label for="last_name">Efternavn</label>
                <input class="form-control" type="text" id="last_name" name="last-name">
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-5 form-group">
                <label for="address">Adresse</label>
                <input class="form-control" type="text" id="address">
            </div>
            <div class="col-md-5 form-group">
                <label for="city">By</label>
                <input class="form-control" type="text" id="city">
            </div>
            <div class="col-md-2 form-group">
                <label for="postalcode">Postnummer</label>
                <input class="form-control" type="text" id="postalcode">
            </div>
        </div>
        <div class="form-row">
            <div class="col-md form-group">
                <label for="email">E-mail adresse</label>
                <input class="form-control" type="text" id="email">
            </div>
            <div class="col-md form-group">
                <label for="phonenumber">Telefon</label>
                <input class="form-control" type="text" id="phonenumber">
            </div>
        </div>
        <div class="form-group">
            <label for="comment">Evt. bemærkninger</label>
            <textarea class="form-control" id="comment"></textarea>
        </div>
        <button type="button" class="btn btn-primary">Indsend forespørgsel</button>
    </section>
</form>