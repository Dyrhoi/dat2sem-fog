//When page loads...
$(".custom-range").each(function () {
   updateRangeLabels(this);
})

$(".image-radio--radio").each(function () {
   updateRadioDisable(this);
   console.log($(this).prop("checked"));
   if($(this).prop("checked")) return false;
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

$('.custom-range').rangeslider({
   polyfill : false
});

$("#shed_checkbox").change(function () {
   $("#shed_dimensions .custom-range").rangeslider("update", true);
})

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