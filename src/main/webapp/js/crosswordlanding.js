let clueArray = [];
let wordArray = [];
let letterCount = 0;
let letterCountMax = 0;
let errorEdit = false;
let validCrossword = false;
let originalWord = 0;

let width = 30;
let height = 30;

let i = 5;
let j = 0;

const svg = document.querySelector("svg");

const svgns = "http://www.w3.org/2000/svg";

function addWord() {
    let wordField1 = $('#word1');
    let clueField1 = $('#clue1');
    if (j < 30) {
        let validatedWord = false;
        let validatedClue = false;
        let wordTemp1 = wordField1.val();
        //word
        let reg = /^[A-Za-z]{1,20}$/;
        if (reg.test(wordTemp1)) {
            wordField1.removeClass("is-invalid");
            wordField1.addClass("is-valid");
            validatedWord = true;
        } else {
            wordField1.removeClass("is-valid");
            wordField1.addClass("is-invalid");
            validatedWord = false;
        }
        //clue
        let clueTemp1 = clueField1.val();
        let regClue = /^\D+/;
        if (regClue.test(clueTemp1)) {
            clueField1.removeClass("is-invalid");
            clueField1.addClass("is-valid");
            validatedClue = true;
        } else {
            clueField1.removeClass("is-valid");
            clueField1.addClass("is-invalid");
            validatedClue = false;
        }
        if (validatedWord && validatedClue){
            j++;
            let clue = clueField1.val();
            clueArray.push(clue);
            clueField1.val("");
            console.log(clueArray)
            let word = wordField1.val();
            wordArray.push(word);
            wordField1.val("");
            console.log(wordArray)
            $("#wordTable tbody").append("<tr><td>" + j + ". " + word + ": " + clue + "</td>" +
                "<td>" +
                "<button type='button' name='edit' class='editButton btn btn-primary' value= " + j + "> Edit </button>" +
                "<button type='button' name='delete' class='deleteButton btn btn-danger' value= " + j + " style='margin-left: 5px'> Delete </button>" +
                "</td></tr>");

            let editButton = $(".editButton");
            editButton.on("click", editItem);

            let deleteButtons = $(".deleteButton");
            deleteButtons.on("click", deleteItem);

            wordField1.removeClass("is-invalid");
            wordField1.removeClass("is-valid");
            clueField1.removeClass("is-invalid");
            clueField1.removeClass("is-valid");
            previewClick();
        }
    } else {
        alert("Max word count :(")
    }

    return false;
}

function editItem(e) {
    let id = e.target.value;
    $('#id').val(id);
    let wordField = $('#wordEdit');
    let clueField = $('#clueEdit');
    let word = wordArray[id - 1];
    originalWord = word;
    console.log(word);
    // repeat line above for all the fields we need
    let clue = clueArray[id - 1];
    console.log(clue);
    wordField.val(word);
    clueField.val(clue);
    wordField.removeClass("is-invalid");
    wordField.removeClass("is-valid");
    clueField.removeClass("is-invalid");
    clueField.removeClass("is-valid");

    // Show the window
    $('#myModal').modal('show');
}
let saveChangesButton = $('#saveChanges');
saveChangesButton.on("click", saveChanges);

function saveChanges() {
    let id = $('#id').val();
    let wordField = $('#wordEdit');
    let clueField = $('#clueEdit');
    let validatedWord;
    let validatedClue;

    let wordTemp = wordField.val();
    //word
    let reg = /^[A-Za-z]{1,20}$/;
    if (reg.test(wordTemp)) {
        wordField.removeClass("is-invalid");
        wordField.addClass("is-valid");
        validatedWord = true;
    } else {
        wordField.removeClass("is-valid");
        wordField.addClass("is-invalid");
        validatedWord = false;
    }
    //clue
    let clueTemp = clueField.val();
    let regClue = /^\D+/;
    if (regClue.test(clueTemp)) {
        clueField.removeClass("is-invalid");
        clueField.addClass("is-valid");
        validatedClue = true;
    } else {
        clueField.removeClass("is-valid");
        clueField.addClass("is-invalid");
        validatedClue = false;
    }

    if (validatedWord && validatedClue) {
        wordArray[id - 1] = wordField.val();
        console.log("here1 " + wordField.val() + " " + originalWord);
        if (wordTemp !== originalWord){
            if (wordTemp.length < originalWord.length){
                letterCount = letterCount - (originalWord.length - wordTemp.length);
                let ugh = (originalWord.length - wordTemp.length)
                console.log("here2 " + ugh);
            }
            console.log("here3");
            previewClick();
        }
        if (!errorEdit){
            clueArray[id - 1] = clueField.val();
            let place = 2 + parseInt(id);
            document.getElementById("wordTable").childNodes[7].childNodes[place].childNodes[0].innerHTML =
                id + ". "  + wordField.val() + ": " + clueField.val();
            $('#myModal').modal('hide');
        } else{
            wordArray[id - 1] = originalWord
        }
        errorEdit = false;
    }
}

let confirmDeleteButton = $('#confirm');
confirmDeleteButton.on("click", confirm);

function confirm(e){
    console.log("Hiiii");
    let targetID = $('#idDelete').val();
    //let targetID = e.target.value;
    //console.log("ugh " + wordArray[targetID - 1].length);
    //letterCount = letterCount - wordArray[targetID - 1].length;
    //letterCountMax++;
    letterCount = 0;
    //console.log("letterCount " + letterCount);
    wordArray.splice(targetID - 1, 1);
    clueArray.splice(targetID - 1, 1);
    // if (j > 1){
    //     previewClick();
    // } else{
    //     for (i = 0; i <= letterCountMax; i++){
    //         svg.childNodes.item(svg.childNodes.length - 1).remove();
    //         svg.childNodes.item(svg.childNodes.length - 1).remove();
    //     }
    // }
    for (i = 0; i < letterCountMax; i++){
        svg.childNodes.item(svg.childNodes.length - 1).remove();
        svg.childNodes.item(svg.childNodes.length - 1).remove();
    }
    letterCountMax = 0;
    j--;
    for (i = parseInt(targetID); i <= j; i++){
        let place = 2 + i;
        document.getElementById("wordTable").childNodes[7].childNodes[place].childNodes[0].innerHTML =
            i + ". "  + wordArray[i-1] + ": " + clueArray[i-1];
    };
    document.getElementById("wordTable").childNodes[7].childNodes[3+j].remove();
    $('#myModalDelete').modal('hide');
    if (j > 0){
        previewClick()
    }
}
function deleteItem(e) {
    $('#myModalDelete').modal('show');
    let targetID = e.target.value;
    $('#idDelete').val(targetID);
}

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
     if (validatedFirst && validCrossword) {
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
    } else if (!validCrossword){
         alert("The crossword is not buildable. Add or delete words until crossword is buildable.")
     }
     else {
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
                errorEdit = true;
                validCrossword = false;
                alert(result.error);
            }else {
                postPreview()
                validCrossword = true;
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


// change any value
        let columns = json_result.jsonArray1[0].length;
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
                //viewBox: "0 0 " + svgWidth + " " + svgHeight
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
        } if(letterCount < letterCountMax){
            for (i = letterCount; i < letterCountMax; i++){
                svg.childNodes.item(svg.childNodes.length - 1).remove();
                svg.childNodes.item(svg.childNodes.length - 1).remove();
            }
        }
        console.log("letterCount= " + letterCount);
        console.log("letterCountMax= " + letterCountMax);
        letterCountMax = letterCount;
        console.log("letterCount= " + letterCount);
        console.log("letterCountMax= " + letterCountMax);
    });
}

$(document).keydown(function(e){
    // Log the key
    console.log(e.keyCode);
    if(e.keyCode === 13) {
        event.preventDefault();
        addWord();
    }
});

