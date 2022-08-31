function playCrossword() {
    //window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_browse.html';
    window.location.href = 'http://crosswordcreators.com/crossword_browse.html'
}

function buildCrossword() {
    //window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_landing.html';
    window.location.href = 'http://crosswordcreators.com/crossword_landing.html'
}

let playCrosswordButton = $('#playCrossword');
playCrosswordButton.on("click", playCrossword);

let buildCrosswordButton = $('#buildCrossword');
buildCrosswordButton.on("click", buildCrossword);

let allowButton = $('#allow');
allowButton.on("click", allowFunction);

function allowFunction() {
    $.post(url, null, function (dataFromServer) {
        let result = JSON.parse(dataFromServer)
        if ('noLogin' in result) {
            alert('error setting user\'s email settings');
            return;
        }
        let validatedWord = false;
        let reg = /^[A-Za-z.1-9]{1,50}$/;
        if (reg.test(result.amazonID)) {
            validatedWord = true;
        }
        if (validatedWord){
            let dataToServer = {amazonID    : result.amazonID};
            let url = "api/set_Email_Settings";
            $.ajax({
                type: 'POST',
                url: url,
                data: JSON.stringify(dataToServer),
                success: function(dataFromServer) {
                    $('#myLetEmailModal').modal('hide');
                    let result = JSON.parse(dataFromServer)
                    if (!'set' in result) {
                        alert('error setting user\'s email settings');
                    } else {
                        window.location.href = 'http://crosswordcreators.com'
                    }
                },
                contentType: "application/json",
                dataType: 'text', // Could be JSON or whatever too
            });
        }
    });
}


