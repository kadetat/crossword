function goToOldCrossword() {
    let dataToServer = {id     : "1"};
    console.log(dataToServer);
    let url = "api/crossword";
    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(dataToServer),
        success: function(dataFromServer) {
            console.log(dataFromServer);
            let result = JSON.parse(dataFromServer)
            //window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_site.html';
            window.location.href = 'http://crosswordcreators.com/crossword_site.html'
        },
        contentType: "application/json",
        dataType: 'text', // Could be JSON or whatever too
    });
}

function goToOldCrossword2() {
    let dataToServer = {id     : "2"};
    console.log(dataToServer);
    let url = "api/crossword";
    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(dataToServer),
        success: function(dataFromServer) {
            console.log(dataFromServer);
            let result = JSON.parse(dataFromServer)
            //window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_site.html';
            window.location.href = 'http://crosswordcreators.com/crossword_site.html'
        },
        contentType: "application/json",
        dataType: 'text', // Could be JSON or whatever too
    });
}

let oldCrosswordButton = $('#oldCrossword');
oldCrosswordButton.on("click", goToOldCrossword);

let oldCrosswordButton2 = $('#oldCrossword2');
oldCrosswordButton2.on("click", goToOldCrossword2);