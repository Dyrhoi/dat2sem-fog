document.querySelectorAll(".image-radio--image").forEach(radioImage  => {
    radioImage.addEventListener("click", e => {
        let toUpdate = e.target.parentElement.dataset.radio;
        document.querySelector(`input[value='${toUpdate}']`).checked = true;
    });
});

$('.custom-range').on('input', function () {
   let targetName = $(this).attr('name');
   let target = $('.range-label[data-range*=' + targetName + ']');
   target.find('span').text($(this).val());
});
