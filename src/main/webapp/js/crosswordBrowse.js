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
            window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_site.html';
            //window.location.href = 'http://appdemo2-env.eba-ap9ig6v3.us-east-2.elasticbeanstalk.com/crossword_site.html'
        },
        contentType: "application/json",
        dataType: 'text', // Could be JSON or whatever too
    });
}

let oldCrosswordButton = $('#oldCrossword');
oldCrosswordButton.on("click", goToOldCrossword);