let closeButton = $('#redirectClose');
closeButton.on("click", redirectFunction);

function redirectFunction() {
    window.location.href = 'https://crosswordcreators.com'
}