function setSessionJava() {

    var url = "api/set_session_servlet";

    // Grab data from the HTML form
    var sessionKey = makeid(15);
    var sessionValue = 25;

    // Create a JSON request based on that data
    var dataToServer = {sessionKey : sessionKey, sessionValue : sessionValue};

    // Post
    $.post(url, dataToServer, function (dataFromServer) {
        // We are done. Write a message to our console
        console.log("Finished calling servlet.");
        console.log(dataFromServer);
    });
}

function makeid(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
        result += characters.charAt(Math.floor(Math.random() *
            charactersLength));
    }
    return result;
}
// This gets session info from our back-end servlet.
function getSessionJava() {

    var url = "api/get_session_servlet";

    $.post(url, null, function (dataFromServer) {
        console.log("Finished calling servlet.");
        if (dataFromServer === ""){
            setSessionJava();
            getSessionJava();
        }
        // Update the HTML with our result
        //sessionStorage.setItem("id", dataFromServer)
    });
}

// function profile() {
//     //window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_landing.html';
//     window.location.href = 'https://crosswordcreators.com/profile.html'
// }
//
// let profileLinkButton = $('#profileButton');
// profileLinkButton.on("click", profile);

getSessionJava();