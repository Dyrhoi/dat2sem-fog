//When page loads...
let svgRequest = null;
$(".custom-range").each(function () {
   updateRangeLabels(this);
})

$(".image-radio--radio").each(function () {
   updateRadioDisable(this);
   if($(this).prop("checked")) return false;
});

if($("#editor").length) {
   //Run this if editor is on page.
   let toolbarOptions = [
      ['bold', 'italic', 'underline'],
      [{ 'list': 'ordered'}, { 'list': 'bullet' }]
   ]

   let quill = new Quill('#editor', {
      theme: 'snow',
      placeholder: 'Skriv en besked.',
      modules: {
         toolbar: toolbarOptions
      }
   });
}


$('.custom-range').rangeslider({
   polyfill : false
});

refreshSVG();

//Events
$("#order_form section:not(#user_section) :input").on("change", function() {
   refreshSVG();
});

$(".image-radio--image").click(function() {
   let targetToUpdate = $(this).parent().data("radio");
   $(`input[value='${targetToUpdate}'`).click();
});

$(".image-radio--radio").change(function () {
   updateRadioDisable(this);
});

$(".custom-range").on("input", function () {
   updateRangeLabels(this);
});

$("#shed_checkbox").change(function () {
   $("#shed_dimensions .custom-range").rangeslider("update", true);
});

$(".ql-editor").on('input paste', function() {
   updateTicketValue();
})

$(".ql-toolbar button").on('click', function() {
   updateTicketValue();
})

function refreshSVG() {
   if(!$("#order_form").length)
      return;
   if(svgRequest != null)
      svgRequest.abort();

   svgRequest = $.get(contextPath + "/api/carport-svg-drawing", $("#order_form section:not(#user_section) :input").serialize(), function (data) {
      $("#svg-request-updater").html(data);
      svgRequest = null;
   });
}

function updateTicketValue() {
   let value = $(".ql-editor").html();
   $("input[name='content']").val(value);
   console.log($("input[name='content']").val());
}

function updateRangeLabels(element) {
   let targetName = $(element).attr("name");
   let target = $(".range-label[data-range='" + targetName + "']");
   target.find("span").text($(element).val());
}

function updateRadioDisable(element) {
   let everythingElse = $(".image-radio--wrapper").find(".options select, .options input, .options button, .options .rangeslider");
   let myWrapper = $(element).closest(".image-radio--wrapper").find(".options select, .options input, .options button, .options .rangeslider");
   $(everythingElse).each(function () {
      $(this).prop("disabled", true);

      if($(this).hasClass("rangeslider")) $(this).addClass("rangeslider--disabled");
   });
   $(myWrapper).each(function () {
      $(this).prop("disabled", false);

      if($(this).hasClass("rangeslider")) $(this).removeClass("rangeslider--disabled");
   });
}

function removeShed() {
   $("#shed-length").remove();
   $("#shed-text").remove();
   $("#shed-width").remove();
   $("#removeShedBtn").remove();
   $("#shed").append($('<span id="shed-text">N/A </span>'));
   let btn = $('<button type="button" class="btn btn-outline-success" id="addShedBtn" onclick="addShed()">\n' +
       '                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16">\n' +
       '                                <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>\n' +
       '                                <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>\n' +
       '                            </svg>\n' +
       '                            Tilføj skur\n' +
       '                        </button>');
   $("#shed").append(btn);
}

let info = $("#changeInfoContainer");

function addShed() {
   $("#shed-text").remove();
   $("#addShedBtn").remove();
   let shedLength = $('<select class="change-input" id="shed-length" name="shed-length"></select>')
   $("#shed").append(shedLength);

   let minLength = parseInt(info.attr("data-shed-minLength"));
   let maxLength = parseInt(info.attr("data-shed-maxLength"));
   let minWidth = parseInt(info.attr("data-shed-minWidth"));
   let maxWidth = parseInt(info.attr("data-shed-maxWidth"));

   let i;
   for (i = minLength; i <= maxLength; i += 30) {
      $('#shed-length').append($('<option value="' + i + '">' + i + '</option>'));
   }
   $("#shed").append($('<span id="shed-text"> X </span>'));
   let shedWidth = $('<select class="change-input" id="shed-width" name="shed-width"></select>')
   $("#shed").append(shedWidth);

   for (i = minWidth; i <= maxWidth; i += 30) {
      $('#shed-width').append($('<option value="' + i + '">' + i + '</option>'));
   }

   $("#shed").append($('<button type="button" class="btn btn-outline-danger" id="removeShedBtn" onclick="removeShed()"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">\n' +
       '  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>\n' +
       '  <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>\n' +
       '</svg>Fjern skur</button>'));
}

let angled_roofs = [];
let flat_roofs = [];
$("#roof-type").change(function () {
   angled_roofs = [];
   flat_roofs = [];
   getRoofMaterials();
   if ($("#roof-type").val() === 'FLAT') {
      $("#roof-angle").remove();
      $("#angle").append($('<span id="angle-text">N/A</span>'));
      $("#roof_angled_material").remove();
      let roof_flat_material = $('<select name="roof_flat_material" class="change-input-height" id="roof_flat_material"></select>');
      $("#roof-material").append(roof_flat_material);

      /* Setting flat roof material dropdown*/
      for (let i = 0; i < flat_roofs.length; i++) {
         let tmp_roof = flat_roofs[i].split(",");
         $('#roof_flat_material').append($('<option value="' + tmp_roof[0] + '">' + tmp_roof[1] + '</option>'));
      }

   }
   else if ($("#roof-type").val() === 'ANGLED') {
      const minAngle = parseInt(info.attr("data-carport-minAngle"));
      const maxAngle = parseInt(info.attr("data-carport-maxAngle"));

      /*Setting angle dropdown*/
      $("#angle-text").remove();
      let roofAngle = $('<select name="roof-angle" class="change-input" id="roof-angle"></select>');
      $("#angle").append(roofAngle);
      for (let i = minAngle; i <= maxAngle; i += 5) {
         $("#roof-angle").append($('<option value="' + i + '">' + i + '</option>'));
      }

      /*Setting angled roof material dropdown*/
      $("#roof_flat_material").remove();
      let roof_angled_material = $('<select name="roof_angled_material" class="change-input-height" id="roof_angled_material"></select>');
      $("#roof-material").append(roof_angled_material);
      for (let i = 0; i < angled_roofs.length; i++) {
         let tmp_roof = angled_roofs[i].split(",");
         $('#roof_angled_material').append($('<option value="' + tmp_roof[0] + '">' + tmp_roof[1] + '</option>'));
      }
   }
});

function getRoofMaterials() {
   let roofsString = info.attr("data-material-angled");
   let allRoofs = roofsString.toString().replaceAll(" ", "").replaceAll("\n", "").replaceAll("-", " - ").split(";");
   allRoofs.pop();
   for (let i = 0; i < allRoofs.length; i++) {
      if (allRoofs[i].split(",")[1] === "ANGLED") {
         angled_roofs.push([i+1] + "," + allRoofs[i].split(",")[0]);
      }
      else {
         flat_roofs.push([i+1] + "," + allRoofs[i].split(",")[0]);
      }
   }
}

function getProfitPercent() {
   let orderInfo = $('#orderInfo');
   let profitSpan = $('#profitPercentSpan');
   const evenProfit = parseInt(orderInfo.attr("data-evenOffer"));
   let orderOffer = $('#offer').val();
   console.log(orderOffer);
   let profitPercent = ((orderOffer - evenProfit) / evenProfit * 100).toFixed(1);
   console.log(profitPercent);

   if (profitPercent >= 40) {
      profitSpan.css("backgroundColor", "green");
      profitSpan.css("color", "white");
      profitSpan.text(profitPercent);
      console.log("Over 40");
   }
   else if (profitPercent < 40 && profitPercent >= 30) {
      profitSpan.css("backgroundColor", "yellow");
      profitSpan.css("color", "black");
      profitSpan.text(profitPercent);
      console.log("Under 40 og over 30");
   }
   else {
      profitSpan.css("backgroundColor", "red");
      profitSpan.css("color", "white");
      profitSpan.text(profitPercent);
      console.log("Under 30");
   }
   profitSpan.append("%");
}

$('#offer').bind('keyup', function(){
   getProfitPercent();
});

function onLoad() {
   getProfitPercent();
}
onLoad();