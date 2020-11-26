document.querySelectorAll(".image-radio--image").forEach(radioImage  => {
    radioImage.addEventListener("click", e => {
        let toUpdate = e.target.parentElement.dataset.radio;
        document.querySelector(`input[value='${toUpdate}']`).checked = true;
    });
});