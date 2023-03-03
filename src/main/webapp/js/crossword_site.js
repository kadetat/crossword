// Main Javascript File

let questionOn = 0;
let questionObjectArray;
let gridObjectArray;
let questionToBlockArray;
let numberCorrect = 0;
let questionsCorrectArray;
const svg = document.querySelector("svg");

const svgns = "http://www.w3.org/2000/svg";

let width = 30;
let height = 30;

function htmlSafe(data) {
    return data.replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;")
}

function showLinkModel(e) {
    $('#LinkModel').modal('show');
}
let linkModelButton = $('#Link');
linkModelButton.on("click", showLinkModel);

function showEmbedModel(e) {
    $('#EmbedModel').modal('show');
}
let embedModelButton = $('#Embed');
embedModelButton.on("click", showEmbedModel);

function updateTable() {
    // Here's where your code is going to go.
    let currentURL = new URL(window.location.href);
    const urlParams = new URLSearchParams(currentURL.search);
    const id = urlParams.get('id');
    let validatedID;

    //word
    let reg = /^[0-9]{1,20}$/;
    if (reg.test(id)) {
        validatedID = true;
    }
    if (!validatedID){
        window.location.href = 'https://crosswordcreators.com/crossword_browse.html';
        return;
    }
    let dataToServer = {id     : id};
    console.log(dataToServer);
    let url = "api/crossword";
    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(dataToServer),
        success: function(dataFromServer) {
            let result = JSON.parse(dataFromServer)
            $("#crosswordTable tbody tr:first-child").remove();
            console.log(result);
            console.log(result.jsonArray2);
            console.log(result.jsonArray1);
            questionObjectArray = result.jsonArray2;
            gridObjectArray = result.jsonArray1;
            console.log(questionObjectArray)
            $('#getTitleResult').html(result.jsonArray3[1]);
            const svg = document.querySelector("svg");

            const svgns = "http://www.w3.org/2000/svg";

// change any value
            let columns = result.jsonArray1[1].length;
            let rows = result.jsonArray1.length;
            let counter = 1;
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
            questionToBlockArray = new Array(result.jsonArray2.length); // create an empty array of length n
            questionsCorrectArray = new Array(result.jsonArray2.length);
            for (var i = 0; i < result.jsonArray2.length; i++) {
                questionToBlockArray[i] = [] // make each element an array
                questionsCorrectArray[i] = 0;
            }
            nextQuestion();
            for (let j = 1; j < result.jsonArray1.length; j++) {
                for (let i = 0; i < result.jsonArray1[j].length; i++) {
                    if (result.jsonArray1[j][i] !== " ") {
                        let name = "";
                        for (let wordInd = 0; wordInd < result.jsonArray2.length; wordInd++) {
                            if ((result.jsonArray2[wordInd].vertical === 0) && (result.jsonArray2[wordInd].row === j) && (0 <= i+1 - result.jsonArray2[wordInd].col) && (i+1 - result.jsonArray2[wordInd].col <= result.jsonArray2[wordInd].length)){
                                questionToBlockArray[result.jsonArray2[wordInd].number-1].push(counter);
                            }
                            if ((result.jsonArray2[wordInd].vertical === 1) && (result.jsonArray2[wordInd].col === i+1) && (0 <= j - result.jsonArray2[wordInd].row) && (j - result.jsonArray2[wordInd].row <= result.jsonArray2[wordInd].length)){
                                questionToBlockArray[result.jsonArray2[wordInd].number-1].push(counter);
                            }
                        }
                        counter++;
                        console.log(result.jsonArray1[j][i] + " " + name + " j =" + j + "i=" + i);
                        let newRect = document.createElementNS(svgns, "rect");
                        gsap.set(newRect, {
                            attr: {
                                x: i * width,
                                y: j * height,
                                width: width,
                                height: height,
                                fill: "#FFFFFF",
                                stroke: colorArray[0]
                            }
                        });
                        svg.appendChild(newRect);
                        // let txt = document.createElementNS(svgns, "text");
                        // txt.textContent = json_result.jsonArray1[j][i];
                        // svg.appendChild(txt);
                        // gsap.set(txt, {
                        //     x: i * width + width / 2,
                        //     y: j * height + height / 2,
                        // });
                    }
                }
            }
            for (let j = 0; j < result.jsonArray2.length; j++) {
                        if (result.jsonArray2[j].vertical === 0){
                            console.log(result.jsonArray2[j].word)
                            let number = j+1;
                            $("#across tbody").append("<tr><td>" + number + ". " + result.jsonArray2[j].clue + "</td></tr>");
                            let txt = document.createElementNS(svgns, "text");
                            txt.textContent = j+1;
                            svg.appendChild(txt);
                            gsap.set(txt, {
                                x: (result.jsonArray2[j].col - 1) * width + 2,
                                y: result.jsonArray2[j].row * height + height / 2,
                            });
                        }
                        else {
                            console.log(result.jsonArray2[j].word)
                            let number = j+1;
                            $("#vertical tbody").append("<tr><td>" + number + ". " + result.jsonArray2[j].clue + "</td></tr>");
                            let txt = document.createElementNS(svgns, "text");
                            txt.textContent = j+1;
                            svg.appendChild(txt);
                            gsap.set(txt, {
                                x: (result.jsonArray2[j].col - 1) * width + 2,
                                y: result.jsonArray2[j].row * height + height - 2,
                            });
                        }
            }
        },
        contentType: "application/json",
        dataType: 'text', // Could be JSON or whatever too
    });
}

// Call your code.
updateTable();


function myUpdateFunction(event) {
    let correct = false;
    let playerGuess = $("#guessForm").val();
    let index = questionOn - 1
        if (questionObjectArray[index].word.toLowerCase() === playerGuess.toLowerCase()) {
            if (questionsCorrectArray[index] === 0) {
                numberCorrect += 1;
                questionsCorrectArray[index] = 1;
                correct = true;
                $("#toast-body").html("Correct!");
                for (let letterIndex = 0; letterIndex < questionToBlockArray[questionObjectArray[index].number - 1].length; letterIndex++) {
                    console.log(questionToBlockArray[questionObjectArray[index].number - 1][letterIndex])
                    gsap.to(svg.childNodes.item(questionToBlockArray[questionObjectArray[index].number - 1][letterIndex] - 1), 2, {
                        attr: {
                            fill: "#00FF00",
                        }
                    });
                }
                // Set the delay in ms. Defaults to 500.
                let myToast = $('#myToast')
                myToast.toast({delay: 2500});
                // Show it
                myToast.toast('show');
                console.log(playerGuess);
                console.log(index);
                // console.log(questionObjectArray[index].word)
                if (questionObjectArray[index].vertical === 0) {
                    for (let wordindex = 0; wordindex < questionObjectArray[index].word.length; wordindex++) {
                        let txt = document.createElementNS(svgns, "text");
                        txt.textContent = gridObjectArray[questionObjectArray[index].row][questionObjectArray[index].col + wordindex - 1];
                        svg.appendChild(txt);
                        gsap.set(txt, {
                            x: (questionObjectArray[index].col + wordindex - 1) * width + width / 2,
                            y: (questionObjectArray[index].row) * height + height / 2,
                        });
                    }
                } else {
                    for (let wordindex = 0; wordindex < questionObjectArray[index].word.length; wordindex++) {
                        let txt = document.createElementNS(svgns, "text");
                        txt.textContent = gridObjectArray[questionObjectArray[index].row + wordindex][questionObjectArray[index].col - 1];
                        svg.appendChild(txt);
                        gsap.set(txt, {
                            x: (questionObjectArray[index].col - 1) * width + width / 2,
                            y: (questionObjectArray[index].row + wordindex) * height + height / 2,
                        });
                    }
                }
                if (numberCorrect === questionObjectArray.length) {
                    $('#previous').hide();
                    $('#next').hide();
                    document.getElementById('questionDisplay').innerHTML
                        = 'You solved the crossword! Congrats!';
                } else {
                    nextQuestion();
                }
            }
        }
    if (!correct) {
        $("#toast-body").html("Wrong guess :(");
        // Set the delay in ms. Defaults to 500.
        let myToast = $('#myToast')
        myToast.toast({delay: 2500});
        // Show it
        myToast.toast('show');
    }
    $("#guessForm").val("");
}

let formButton1 = $("#Guess");
formButton1.on("click", myUpdateFunction);

$(document).keydown(function(e){
    // Log the key
    console.log(e.keyCode);
    if(e.keyCode == 13) {
        event.preventDefault();
        myUpdateFunction();
    }
});


let printButton1 = $("#Print");
printButton1.on("click", printDiv);

function printDiv() {
    var divContents = document.getElementById("printPart").innerHTML;
    var divContents2 = document.getElementById("printPart2").innerHTML;
    var a = window.open('', '', 'height=900, width=1300');
    a.document.write('<html>');
    a.document.write('<body >');
    a.document.write(divContents);
    a.document.write(divContents2);
    a.document.write('</body><h4>Brought to you by CrosswordCreators.com</h4></html>');
    a.document.close();
    a.print();
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
                        //window.location.href = 'https://crosswordcreators.com/crossword_site.html'
                    }
                },
                contentType: "application/json",
                dataType: 'text', // Could be JSON or whatever too
            });
        }
    });
}

let nextButton = $('#next');
nextButton.on("click", nextQuestion);

function nextQuestion(){
    if (questionOn === questionsCorrectArray.length){
        questionOn = 0;
    }
    for (let questionIndex = questionOn; questionIndex <= questionsCorrectArray.length + 1; questionIndex++){
        if (questionsCorrectArray[questionIndex] === 0){
            questionOn = questionIndex + 1;
            break;
        } else if (questionIndex === questionsCorrectArray.length ){
            questionIndex = -1;
        }
        console.log(questionIndex);
    }
    document.getElementById('questionDisplay').innerHTML
        = questionOn + '. '  + questionObjectArray[questionOn - 1].clue;
}

let previousButton = $('#previous');
previousButton.on("click", previousQuestion);

function previousQuestion(){
    if (questionOn === 1){
        questionOn = questionsCorrectArray.length + 1;
    }
    for (let questionIndex = questionOn - 2; questionIndex <= questionsCorrectArray.length + 1; questionIndex--){
        if (questionsCorrectArray[questionIndex] === 0){
            questionOn = questionIndex + 1;
            break;
        } else if (questionIndex <= 0){
            questionIndex = questionsCorrectArray.length + 1;
        }
        console.log(questionIndex);
    }

    document.getElementById('questionDisplay').innerHTML
            = questionOn + '. '  + questionObjectArray[questionOn - 1].clue;

}