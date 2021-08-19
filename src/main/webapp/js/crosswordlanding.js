
let i = 5;
let j = 5;

function addWord() {
    j++;
    if (j <= 30) {
        let html = '';
        html += '<div id=inputFormRow2>'
        html += '<label For="word"' + j + '>Word #' +j+ ':</label>';
        html += '<input type="text" id="word'+j+'" name="word" class="form-control">';
        html += '<div class="input-group-append" style="margin: 20px;">';
        html += '<button id="removeRow2" type="button" class="btn btn-danger">Remove</button>';
        html += '</div>';
        html += '</div>'

        $('#newRow1').append(html);
    } else {
        alert("Max word count :(")
    }
}

function addClue(){
    i++;
    if (i <= 30) {
        let html2 = '';
        //html2 += '<div id="inputFormRow"' + i + '>';
        html2 += '<div id=inputFormRow>'
        html2 += '<label For="clue"' + i + '>Clue #'+i+':</label>';
        html2 += '<input type="text" id="clue'+i+'" name="clue" class="form-control">';
        html2 += '<div class="input-group-append" style="margin: 20px;">';
        html2 += '<button id="removeRow" type="button" class="btn btn-danger">Remove</button>';
        html2 += '</div>';
        html2 += '</div>'

        $('#newRow2').append(html2);
    } else {
        alert("Max clue count :(")
    }
}

// remove row
$(document).on('click', '#removeRow', function () {
    $(this).closest('#inputFormRow').remove();
    i--;
});

$(document).on('click', '#removeRow2', function () {
    $(this).closest('#inputFormRow2').remove();
    j--;
});

let newWordButton = $('#addWord');
newWordButton.on("click", addWord);


let newClueButton = $('#addClue');
newClueButton.on("click", addClue);

function submit() {
    if (i ==j) {
    let title = $('#title').val();
    let author = $('#author').val();
    let dateObject = new Date;
    let date = dateObject.getFullYear() + '-' + (dateObject.getMonth()+1) + '-' + dateObject.getDate();

    let clueArray = []
    for (let index = 0; index < i; index++) {
        let want = index + 1
        let clue = $('#clue'+ want).val();
        clueArray.push(clue);
    }
    console.log(clueArray)
    let wordArray = []
    for (let index = 0; index < j; index++) {
        let want2 = index + 1
        let word = $('#word'+ want2).val();
        wordArray.push(word);
    }
    console.log(wordArray)

    let dataToServer = {id     : "null2",
        title  : title,
        author   : author,
        date     : date  ,
        words  : wordArray,
        clues  : clueArray,};

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

    let url2 = "api/puzzle_add";
    $.ajax({
        type: 'POST',
        url: url2,
        data: JSON.stringify(dataToServer),
        success: function(dataFromServer) {
            console.log(dataFromServer);
            let result = JSON.parse(dataFromServer);
            if ('error' in result) {
                // JavaScript alert the error.
                alert(result.error);
            }
        },
        contentType: "application/json",
        dataType: 'text', // Could be JSON or whatever too
    });

} else {
        alert("Word count must equal Clue count.");
    }
}

let newSubmitButton = $('#submit');
newSubmitButton.on("click", submit);

