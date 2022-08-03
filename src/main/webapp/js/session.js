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
        console.log(dataFromServer);
        if (dataFromServer === ""){
            setSessionJava();
            getSessionJava();
        }
        // Update the HTML with our result
        //sessionStorage.setItem("id", dataFromServer)
    });
}

getSessionJava();