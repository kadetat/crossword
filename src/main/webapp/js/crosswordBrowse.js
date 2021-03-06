function htmlSafe(data) {
    return data.replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;")
}

function playItem(e) {
    let targetID = e.target.value;
    let dataToServer = {id     : targetID};
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

// function goToOldCrossword2() {
//     let dataToServer = {id     : "2"};
//     console.log(dataToServer);
//     let url = "api/crossword";
//     $.ajax({
//         type: 'POST',
//         url: url,
//         data: JSON.stringify(dataToServer),
//         success: function(dataFromServer) {
//             console.log(dataFromServer);
//             let result = JSON.parse(dataFromServer)
//             //window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_site.html';
//             window.location.href = 'http://crosswordcreators.com/crossword_site.html'
//         },
//         contentType: "application/json",
//         dataType: 'text', // Could be JSON or whatever too
//     });
// }

// let oldCrosswordButton = $('#oldCrossword');
// oldCrosswordButton.on("click", goToOldCrossword);
//
// let oldCrosswordButton2 = $('#oldCrossword2');
// oldCrosswordButton2.on("click", goToOldCrossword2);

function updateTable() {
    // Here's where your code is going to go.
    console.log("updateTable called");

    // Define a URL
    let url = "api/crossword_get";

    // Start a web call. Specify:
    // URL
    // Data to pass (nothing in this case)
    // Function to call when we are done
    $.getJSON(url, null, function(json_result) {
        $("#datatable tbody tr:first-child").remove()
        console.log(json_result)
        for (let i = 0; i < json_result.length; i++) {

            $("#datatable tbody").append("<tr><td>" + json_result[i].id + "</td>" +
                "<td>" + htmlSafe(json_result[i].title) + "</td>" +
                "<td>" + htmlSafe(json_result[i].author) + "</td>" +
                "<td>" + htmlSafe(json_result[i].date) + "</td>" +
                "<td>" +
                "<button type='button' name='play' class='playButton btn btn-danger' value=" + json_result[i].id + "> Play </button>" +
                "</td></tr>");

        }
        let buttons = $(".playButton");
        buttons.on("click", playItem);

    });

}

// Call your code.
updateTable();