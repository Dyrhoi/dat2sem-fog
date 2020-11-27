$(".image-radio--image").click(function() {
   let targetToUpdate = $(this).parent().data("radio");
   $(`input[value='${targetToUpdate}'`).prop("checked", true);
});

$(".custom-range").on("input", function () {
   let targetName = $(this).attr("name");
   let target = $(".range-label[data-range='" + targetName + "']");
   target.find("span").text($(this).val());
});

$('.custom-range').rangeslider({
   polyfill : false
});
