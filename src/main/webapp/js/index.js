function playCrossword() {
    window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_browse.html';
    //window.location.href = 'http://crosswordcreators.com/crossword_browse.html'
}

function buildCrossword() {
    //window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_landing.html';
    window.location.href = 'http://crosswordcreators.com/crossword_landing.html'
}

let playCrosswordButton = $('#playCrossword');
playCrosswordButton.on("click", playCrossword);

let buildCrosswordButton = $('#buildCrossword');
buildCrosswordButton.on("click", buildCrossword);