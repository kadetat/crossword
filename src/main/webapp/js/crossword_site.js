// Main Javascript File


let questionObjectArray;
let gridObjectArray;

const svg = document.querySelector("svg");

const svgns = "http://www.w3.org/2000/svg";

let width = 30;
let height = 30;

function htmlSafe(data) {
    return data.replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;")
}

function updateTable() {
    // Here's where your code is going to go.

    // Define a URL
    let url2 = "api/crossword";

    console.log("call updateTable")

    // Start a web call. Specify:
    // URL
    // Data to pass (nothing in this case)
    // Function to call when we are done
    $.getJSON(url2, null, function(json_result) {
        $("#crosswordTable tbody tr:first-child").remove();
        console.log(json_result);
        console.log(json_result.jsonArray2);
        console.log(json_result.jsonArray1);
        questionObjectArray = json_result.jsonArray2;
        gridObjectArray = json_result.jsonArray1;
        console.log(questionObjectArray)
        $('#getTitleResult').html(json_result.jsonArray3[1]);
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
                //viewBox: "0 0 " + svgWidth + " " + svgHeight
            }
        });
        for (let j = 1; j < json_result.jsonArray1.length; j++) {
            for (let i = 0; i < json_result.jsonArray1[j].length; i++) {
                counter++;
                let newRect = document.createElementNS(svgns, "rect");
                if (json_result.jsonArray1[j][i] !== " ") {
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
        for (let j = 1; j < json_result.jsonArray2.length + 1; j++) {
            for (let k = 0; k < json_result.jsonArray2.length; k++)
            if (json_result.jsonArray2[k].number === j) {
                if (json_result.jsonArray2[k].vertical === 0){
                    console.log(json_result.jsonArray2[k].word)
                    $("#across tbody").append("<tr><td>" + j + ". " + json_result.jsonArray2[k].clue + "</td></tr>");
                    let txt = document.createElementNS(svgns, "text");
                    txt.textContent = json_result.jsonArray2[k].number;
                    svg.appendChild(txt);
                    gsap.set(txt, {
                        x: (json_result.jsonArray2[k].col - 1) * width + 2,
                        y: json_result.jsonArray2[k].row * height + height / 2,
                    });
                }
                else {
                    console.log(json_result.jsonArray2[k].word)
                    $("#vertical tbody").append("<tr><td>" + j + ". " + json_result.jsonArray2[k].clue + "</td></tr>");
                    let txt = document.createElementNS(svgns, "text");
                    txt.textContent = json_result.jsonArray2[k].number;
                    svg.appendChild(txt);
                    gsap.set(txt, {
                        x: (json_result.jsonArray2[k].col - 1) * width + 2,
                        y: json_result.jsonArray2[k].row * height + height - 2,
                    });
                }
            }
        }

    });
}

// Call your code.
updateTable();


function myUpdateFunction(event) {
    let correct = false;
    let playerGuess = $("#guessForm").val();
    for (let index = 0; index < questionObjectArray.length; index++) {
        if (questionObjectArray[index].word.toLowerCase() === playerGuess.toLowerCase()) {
            correct = true;
            $("#toast-body").html("Correct!");
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