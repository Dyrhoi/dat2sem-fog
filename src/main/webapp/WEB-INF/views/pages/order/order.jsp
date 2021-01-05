<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 25-11-2020
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="domain.carport.Shed" %>
<%@ page import="domain.carport.Carport" %>
<div>
    <div class="container">
        <section>
            <div>
                <h1>Byg selv carport med Fog</h1>
                <p class="lead">Kan du ikke finde den helt rigtige carport? Med byg selv, kan du selv justere dine mål,
                    så sender vi dig et tilbud.</p>
            </div>
        </section>
        <section>
            <div class="row landing-items">
                <div class="col-md text-center">
                    <div class="">
                        <div class="rounded-circle mb-2">
                            <span class="material-icons-round">
                                crop_free
                            </span>
                        </div>
                        <h4>Dine egne mål</h4>
                        <p class="mb-0">Få din carport præcis som du ønsker den.</p>
                    </div>
                </div>
                <div class="col-md text-center">
                    <div class="">
                        <div class="rounded-circle mb-2">
                            <span class="material-icons-round">
                                local_offer
                            </span>
                        </div>
                        <h4>Specielt tilbud</h4>
                        <p class="mb-0">Få et specielt tilbud skræddersydet efter dine behov.</p>
                    </div>
                </div>
                <div class="col-md text-center">
                    <div class="">
                        <div class="rounded-circle mb-2">
                            <span class="material-icons-round">
                                sentiment_satisfied_alt
                            </span>
                        </div>
                        <h4>Tilfredshedsgaranti</h4>
                        <p class="mb-0">Med professionelle montører er du sikker på 100% tilfredshedsgaranti.</p>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <section>
        <form method="post" action="${pageContext.request.contextPath}/" id="order_form">
            <div class="container">
                <section>
                    <h2>Dimensioner</h2>
                    <div id="carportDimensions" class="form-group row">
                        <div class="col-sm">
                            <div class="d-flex align-items-center mb-2">
                                <label class="" for="length">Carport længde</label>
                                <span class="ml-auto range-label" data-range="carport-length"><span>480</span>cm</span>
                            </div>
                            <input class="custom-range" name="carport-length" type="range" id="length"
                                   value="480" step="30" min="${Carport.minLength}"
                                   max="${Carport.maxLength}">
                        </div>
                        <div class="col-sm">
                            <div class="d-flex align-items-center mb-2">
                                <label class="" for="width">Carport bredde</label>
                                <span class="ml-auto range-label" data-range="carport-width"><span>480</span>cm</span>
                            </div>
                            <input class="custom-range" name="carport-width" type="range" id="width" value="480"
                                   step="30" min="${Carport.minWidth}" max="${Carport.maxWidth}">
                        </div>
                    </div>
                </section>

                <section>
                    <h2>Overdække</h2>
                    <div class="row">
                        <div class="col-sm image-radio--wrapper">
                            <div class="form-group">
                                <label for="car-type-flat">
                                    Fladt tag
                                    <span class="d-block font-weight-normal">Et simpelt men effektivt tag.</span>
                                </label>
                                <input type="radio" class="image-radio--radio" hidden checked required
                                       id="car-type-flat" name="roof-type" value="flat">
                                <div class="image-radio" data-radio="flat">
                                    <div class="image-radio--image"
                                         style="background-image: url('https://preview.free3d.com/img/2019/05/2154879260106425428/ih7duys9-900.jpg')"></div>
                                </div>
                            </div>
                            <div class="options">
                                <div class="form-group">
                                    <label for="roof_flat_material">Tagmateriale</label>
                                    <select class="form-control" id="roof_flat_material" name="roof_flat_material">
                                        <c:forEach var="item" items="${requestScope.roofMaterials}"
                                                   varStatus="loop">
                                            <c:if test="${item.type == 'FLAT'}">
                                                <option value="${item.id}"><c:out value="${item.name}"/></option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm image-radio--wrapper">
                            <div class="form-group">
                                <label for="car-type-angled">
                                    Tag med rejsning
                                    <span class="d-block font-weight-normal">Et sejere men dyrere tag.</span>
                                </label>
                                <input type="radio" class="image-radio--radio" hidden required
                                       id="car-type-angled" name="roof-type" value="angled">
                                <div class="image-radio" data-radio="angled">
                                    <div class="image-radio--image"
                                         style="background-image: url('https://preview.free3d.com/img/2020/06/2337161513644590386/d9yfqpbm-900.jpg')"></div>
                                </div>
                            </div>
                            <div class="options">
                                <div class="form-group">
                                    <label for="roof_angled_material">Tagmateriale</label>
                                    <select class="form-control" id="roof_angled_material" name="roof_angled_material">
                                        <c:forEach var="item" items="${requestScope.roofMaterials}"
                                                   varStatus="loop">
                                            <c:if test="${item.type == 'ANGLED'}">
                                                <option value="${item.id}"><c:out value="${item.name}"/></option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group mb-0">
                                    <div class="d-flex align-items-center mb-2">
                                        <label class="" for="angle">Tag hældning</label>
                                        <span class="ml-auto range-label"
                                              data-range="roof-angle"><span></span> grader</span>
                                    </div>
                                    <input class="custom-range" name="roof-angle" type="range" id="angle"
                                           value="25" step="5" min="${Carport.minAngle}"
                                           max="${Carport.maxAngle}">
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section>
                    <h2>Redskabsskur</h2>
                    <p>NB! Der skal beregnes 15 cm tagudhæng på hver side af redskabsrummet</p>
                    <input type="checkbox" name="shed-checkbox"  class="css-checkbox" id="shed_checkbox">
                    <label for="shed_checkbox" class="css-label">Tilbygning af redskabsskur</label>
                    <div id="shed_dimensions" class="row">
                        <div class="form-group col-sm">
                            <div class="d-flex align-items-center mb-2">
                                <label class="col-form-label" for="shed-length">Redskabsrum længde</label>
                                <span class="ml-auto col-form-label range-label"
                                      data-range="shed-length"><span>150</span>cm</span>
                            </div>
                            <input class="custom-range" name="shed-length" type="range" id="shed-length"
                                   value="150" step="30"
                                   min="${Shed.minLength}" max="${Shed.maxLength}">
                        </div>
                        <div class="col-sm form-group">
                            <div class="d-flex align-items-center mb-2">
                                <label class="col-form-label" for="shed-width">Redskabsrum bredde</label>
                                <span class="ml-auto col-form-label range-label"
                                      data-range="shed-width"><span>210</span>cm</span>
                            </div>
                            <input class="custom-range" name="shed-width" type="range" id="shed-width"
                                   value="210" step="30"
                                   min="${Shed.minWidth}" max="${Shed.maxWidth}">
                        </div>
                    </div>
                </section>
            </div>

            <section class="bg-primary text-light" id="your_carport">
                <div class="container">
                    <div class="row">
                        <div class="col d-flex align-items-center">
                            <div>
                                <h2>Din carport</h2>
                                <p class="mb-0">Her kan du se din genereret carport plan-tegning.</p>
                            </div>
                        </div>
                        <div class="col">
                            <div id="svg-request-updater">
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <div class="container">
                <section id="user_section">
                    <h2>Kunde</h2>
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
                            <input class="form-control" type="text" id="address" name="address">
                        </div>
                        <div class="col-md-5 form-group">
                            <label for="city">By</label>
                            <input class="form-control" type="text" id="city" name="city">
                        </div>
                        <div class="col-md-2 form-group">
                            <label for="postalcode">Postnummer</label>
                            <input class="form-control" type="text" id="postalcode" name="postal-code">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-md form-group">
                            <label for="email">E-mail adresse</label>
                            <input class="form-control" type="text" id="email" name="email">
                        </div>
                        <div class="col-md form-group">
                            <label for="phonenumber">Telefon</label>
                            <input class="form-control" type="text" id="phonenumber" name="phone-number">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="note">Evt. bemærkninger</label>
                        <textarea class="form-control" id="note" name="note"></textarea>
                    </div>
                </section>
                <section class="d-flex justify-content-end align-items-center">
                    <button type="submit" class="btn btn-primary">Indsend forespørgsel</button>
                </section>
            </div>
        </form>
    </section>
</div>