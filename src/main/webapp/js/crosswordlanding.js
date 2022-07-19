let clueArray = [];
let wordArray = [];
let letterCount = 0;

let width = 30;
let height = 30;

let i = 5;
let j = 0;

function addWord() {
    j++;
    if (j <= 30) {
        let clue = $('#clue1').val();
        clueArray.push(clue);
        $('#clue1').val("");
        console.log(clueArray)
        let word = $('#word1').val();
        wordArray.push(word);
        $('#word1').val("");
        console.log(wordArray)
        previewClick();
        $("#wordTable tbody").append("<tr><td>" + j + ". " + word + ": " + clue + "</td>" +
            "<td>" +
            "<button type='button' name='edit' class='playButton btn btn-primary' value=\"j\"> Edit </button>" +
            "<button type='button' name='delete' class='playButton btn btn-danger' value=\"j\" style='margin-left: 5px'> Delete </button>" +
            "</td></tr>");
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

function submit() {
    let reg = /^[A-Za-z]{1,20}$/;
    let regClue = /^\D+/;
    let title = $('#title');
    let author = $('#author');
    let validatedFirst = true;
    if (true) {
    if (regClue.test(title.val())) {
        title.removeClass("is-invalid");
        title.addClass("is-valid");
    } else {
        title.removeClass("is-valid");
        title.addClass("is-invalid");
        validatedFirst = false;
    }
    if (regClue.test(author.val())) {
        author.removeClass("is-invalid");
        author.addClass("is-valid");
    } else {
        author.removeClass("is-valid");
        author.addClass("is-invalid");
        validatedFirst = false;
    }
    // for (let iClue = 0; iClue <= i; iClue++){
    //     let want = iClue + 1
    //     let clueChecking = $('#clue'+ want)
    //     let clueCheck = clueChecking.val();
    //     let clueField = clueChecking;
    //     if (regClue.test(clueCheck)) {
    //         clueField.removeClass("is-invalid");
    //         clueField.addClass("is-valid");
    //     } else {
    //         clueField.removeClass("is-valid");
    //         clueField.addClass("is-invalid");
    //         validatedFirst = false;
    //     }
    // }
    // for (let iWord = 0; iWord <= i; iWord++){
    //     let want = iWord + 1
    //     let wordChecking = $('#word'+ want)
    //     let wordCheck = wordChecking.val();
    //     let wordField = wordChecking;
    //     if (reg.test(wordCheck)) {
    //         wordField.removeClass("is-invalid");
    //         wordField.addClass("is-valid");
    //     } else {
    //         wordField.removeClass("is-valid");
    //         wordField.addClass("is-invalid");
    //         validatedFirst = false;
    //     }
    // }
     if (validatedFirst) {
        let title = $('#title').val();
        let author = $('#author').val();
        let dateObject = new Date;
        let date = dateObject.getFullYear() + '-' + (dateObject.getMonth() + 1) + '-' + dateObject.getDate();



        console.log(clueArray);
        console.log(wordArray);

        let dataToServer = {
            id: "null2",
            title: title,
            author: author,
            date: date,
            words: wordArray,
            clues: clueArray,
        };

        console.log(dataToServer);
        let url = "api/crossword";
        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(dataToServer),
            success: function (dataFromServer) {
                console.log(dataFromServer);
                let result = JSON.parse(dataFromServer)
                window.location.href = 'http://localhost:8080/Gradle___com_kurtis_project___kurtis_project_1_0_SNAPSHOT_war/crossword_site.html';
                //window.location.href = 'http://crosswordcreators.com/crossword_site.html'
            },
            contentType: "application/json",
            dataType: 'text', // Could be JSON or whatever too
        });

        let url2 = "api/puzzle_add";
        $.ajax({
            type: 'POST',
            url: url2,
            data: JSON.stringify(dataToServer),
            success: function (dataFromServer) {
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
    }else {
        alert("Clue or Word has character not allowed.");
    }

} else {
        alert("Word count must equal Clue count.");
    }
}

let newSubmitButton = $('#submit');
newSubmitButton.on("click", submit);


let newPreviewButton = $('#preview');
newPreviewButton.on("click", previewClick);

function previewClick() {
    console.log("preview clicked");
    // Here's where your code is going to go.

    let dataToServer = {
        words: wordArray,
        clues: clueArray,
    };

    console.log(dataToServer);
    let url = "api/previewcrossword";
    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(dataToServer),
        success: function (dataFromServer) {
            console.log(dataFromServer);
            let result = JSON.parse(dataFromServer)
            if ('error' in result) {
                // JavaScript alert the error.
                alert(result.error);
            }else {
                postPreview()
            }
        },
        contentType: "application/json",
        dataType: 'text', // Could be JSON or whatever too
    });
}

function postPreview(){

// Define a URL
    let url2 = "api/previewcrossword";

    console.log("call updateTable")
    let reg = /^[A-Za-z]$/;

    // Start a web call. Specify:
    // URL
    // Data to pass (nothing in this case)
    // Function to call when we are done
    $.getJSON(url2, null, function(json_result) {
        console.log(json_result);
        console.log(json_result.jsonArray2);
        console.log(json_result.jsonArray1);
        questionObjectArray = json_result.jsonArray2;
        gridObjectArray = json_result.jsonArray1;
        console.log(questionObjectArray)
        const svg = document.querySelector("svg");

        const svgns = "http://www.w3.org/2000/svg";

// change any value
        let columns = json_result.jsonArray1[1].length;
        let rows = json_result.jsonArray1.length;
        let counter = 0;
        const colorArray = ["#121212", "#46a4cc", "#a63e4b"];

// figure the new svg width/height
        const svgWidth = width * columns;
        const svgHeight = height * rows;


        gsap.set(svg, {
            attr: {
                width: svgWidth,
                height: svgHeight,
                viewBox: "0 0 " + svgWidth + " " + svgHeight
            }
        });

        for (let jaxis = 1; jaxis < json_result.jsonArray1.length; jaxis++) {
            for (let iaxis = 0; iaxis < json_result.jsonArray1[jaxis].length; iaxis++) {

                if (reg.test(json_result.jsonArray1[jaxis][iaxis])) {
                    if (counter < letterCount) {
                        console.log("here" + letterCount.toString() + counter.toString());
                        gsap.to(svg.childNodes.item(2 * counter),1,{
                            attr: {
                                x: iaxis * width,
                                y: jaxis * height,
                                width: width,
                                height: height,
                                fill: "#FFFFFF",
                                stroke: colorArray[0]
                            }
                        });

                        // let txt = document.createElementNS(svgns, "text");
                        svg.childNodes.item((2*counter)+1).textContent = json_result.jsonArray1[jaxis][iaxis];
                        // svg.appendChild(txt);
                        gsap.to(svg.childNodes.item((2 * counter) + 1), 1, {
                            x: iaxis * width + width / 3,
                            y: (jaxis * height) + (height*2 /3) ,
                        });
                        counter++;
                    } else {
                        console.log("here_new" + letterCount.toString() + counter.toString());

                        let newRect = document.createElementNS(svgns, "rect");
                        letterCount++;
                        counter++;
                        gsap.to(newRect, 1,{
                            attr: {
                                x: iaxis * width,
                                y: jaxis * height,
                                width: width,
                                height: height,
                                fill: "#FFFFFF",
                                stroke: colorArray[0]
                            }
                        });

                        svg.appendChild(newRect);

                        let txt = document.createElementNS(svgns, "text");
                        txt.textContent = json_result.jsonArray1[jaxis][iaxis];
                        svg.appendChild(txt);
                        gsap.to(txt, 1, {
                            x: iaxis * width + width / 3,
                            y: (jaxis * height) + (height*2 /3) ,
                        });
                    }

                }
            }
        }
    });
}

function removeElement(element){
    if(typeof(element) === "string"){
        element = document.querySelector(element);
    }
    return function() {
        element.parentNode.removeChild(element);
    };
}