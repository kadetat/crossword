let closeButton = $('#redirectClose');
closeButton.on("click", redirectFunction);

function redirectFunction() {
    window.location.href = 'https://crosswordcreators.com'
}

let allowButton = $('#allow');
allowButton.on("click", allowFunction);

function allowFunction() {
    let url = "api/check_session_attribute_servlet";
    $.post(url, null, function (dataFromServer) {
        let result = JSON.parse(dataFromServer)
        if ('noLogin' in result) {
            alert('error setting user\'s email settings');
            return;
        }
        let validatedWord = false;
        let reg = /^[A-Za-z.0-9]{1,50}$/;
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
                        window.location.href = 'https://crosswordcreators.com'
                    }
                },
                contentType: "application/json",
                dataType: 'text', // Could be JSON or whatever too
            });
        }
    });
}

function ListFillFunction() {
    let url = "api/check_session_attribute_servlet";
    $.post(url, null, function (dataFromServer) {
        let result = JSON.parse(dataFromServer)
        if ('noLogin' in result) {
            alert('error- not logged in.');
            return;
        }
        let validatedWord = false;
        let reg = /^[A-Za-z.0-9]{1,50}$/;
        if (reg.test(result.amazonID)) {
            validatedWord = true;
        }
        if (validatedWord){
            let dataToServer = {amazonID    : result.amazonID};
            let url = "api/profile_Lists";
            $.ajax({
                type: 'POST',
                url: url,
                data: JSON.stringify(dataToServer),
                success: function (dataFromServer) {
                    let result = JSON.parse(dataFromServer)
                    if ('error' in result) {
                        alert('error' + result.error);
                    } else {
                        console.log(result);
                        if (result.jsonArray1.length !== 0){
                            $("#completedTable tbody tr:first-child").remove();
                        }
                        for (let i = 0; i < result.jsonArray1.length; i++) {
                            $("#completedTable tbody").append("<tr><td>" + result.jsonArray1[i].id + "</td>" +
                                "<td>" + htmlSafe(result.jsonArray1[i].title) + "</td>" +
                                "<td>" + htmlSafe(result.jsonArray1[i].author) + "</td>" +
                                "<td>" + htmlSafe(result.jsonArray1[i].completed) + "</td>" +
                                "<td>" +
                                "<button type='button' name='play' class='playButton btn btn-danger' value=" + result.jsonArray1[i].id + "> Play </button>" +
                                "</td></tr>");

                        }
                        if (result.jsonArray2.length !== 0){
                            $("#builtTable tbody tr:first-child").remove();
                        }
                        for (let i = 0; i < result.jsonArray2.length; i++) {

                            $("#builtTable tbody").append("<tr><td>" + result.jsonArray2[i].id + "</td>" +
                                "<td>" + htmlSafe(result.jsonArray2[i].title) + "</td>" +
                                "<td>" + htmlSafe(result.jsonArray2[i].author) + "</td>" +
                                "<td>" + htmlSafe(result.jsonArray2[i].date) + "</td>" +
                                "<td>" +
                                "<button type='button' name='play' class='playButton btn btn-danger' value=" + result.jsonArray2[i].id + "> Play </button>" +
                                "</td></tr>");

                        }
                        let buttons = $(".playButton");
                        buttons.on("click", playItem);
                    }
                }
            });
        }
    });
}
function htmlSafe(data) {
    return data.replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;")
}
function playItem(e) {
    let targetID = e.target.value;
    let data = {
        url : 'https://crosswordcreators.com/crossword_site.html?',
        params: {
            'id': targetID,
        }
    }
    let queryParam = encodeQuery(data);
    window.location.href = queryParam;
}
function encodeQuery(data){
    let query = data.url
    for (let d in data.params)
        query += encodeURIComponent(d) + '='
            + encodeURIComponent(data.params[d]) + '&';
    return query.slice(0, -1)
}
ListFillFunction();