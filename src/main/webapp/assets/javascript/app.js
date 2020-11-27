//When page loads...
$(".custom-range").each(function () {
   updateRangeLabels(this);
})

//Events
$(".image-radio--image").click(function() {
   let targetToUpdate = $(this).parent().data("radio");
   $(`input[value='${targetToUpdate}'`).prop("checked", true);
});

$(".custom-range").on("input", function () {
   updateRangeLabels(this);
});

$('.custom-range').rangeslider({
   polyfill : false
});

function updateRangeLabels(element) {
   let targetName = $(element).attr("name");
   let target = $(".range-label[data-range='" + targetName + "']");
   target.find("span").text($(element).val());
}