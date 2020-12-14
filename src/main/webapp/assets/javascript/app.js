//When page loads...
$(".custom-range").each(function () {
   updateRangeLabels(this);
})

$(".image-radio--radio").each(function () {
   updateRadioDisable(this);
   if($(this).prop("checked")) return false;
});

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


$('.custom-range').rangeslider({
   polyfill : false
});


//Events
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

function addShed() {
   $("#shed-text").remove();
   $("#addShedBtn").remove();
   let shedLength = $('<select class="change-input" id="shed-length" name="shed-length"></select>')
   $("#shed").append(shedLength);

   let i;
   for (i = 150; i <= 690; i += 30) {
      $('#shed-length').append($('<option value="' + i + '">' + i + '</option>'));
   }
   $("#shed").append($('<span id="shed-text"> X </span>'));
   let shedWidth = $('<select class="change-input" id="shed-width" name="shed-width"></select>')
   $("#shed").append(shedWidth);

   for (i = 210; i <= 720; i += 30) {
      $('#shed-width').append($('<option value="' + i + '">' + i + '</option>'));
   }

   $("#shed").append($('<button type="button" class="btn btn-outline-danger" id="removeShedBtn" onclick="removeShed()"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">\n' +
       '  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>\n' +
       '  <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>\n' +
       '</svg>Fjern skur</button>'));
}

$("#roof-type").change(function() {
   if ($("#roof-type").val() === 'FLAT') {
      $("#roof-angle").remove()
      $("#angle").append($('<span id="angle-text">N/A</span>'));
   }
   else if ($("#roof-type").val() === 'ANGLED') {
      const minAngle = $("#changeInfoContainer").attr("data-carport-minAngle");
      const maxAngle = $("#changeInfoContainer").attr("data-carport-maxAngle");
      console.log(minAngle);
      console.log(maxAngle);

      $("#angle-text").remove();
      let roofAngle = $('<select name="roof-angle" class="change-input" id="roof-angle"></select>');
      $("#angle").append(roofAngle);
      for (let i = 15; i <= 45; i += 5) {
         console.log(i);
         $("#roof-angle").append($('<option value="' + i + '">' + i + '</option>'));
      }
   }
});

