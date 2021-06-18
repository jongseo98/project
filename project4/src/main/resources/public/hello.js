window.onload = function() {
    $('#btn-search1').click();
}

$(document).ready(function() {
    $('#search-form1').submit(function (event) {
        event.preventDefault();
        test1();
    });

    $('#search-form2').submit(function (event) {
        event.preventDefault();
        test2();
    });
});

function getCheckboxValue()  {
    // 선택된 목록 가져오기
    const query = 'input[name="genre"]:checked';
    const selectedEls = 
        document.querySelectorAll(query);
    
    // 선택된 목록에서 value 찾기
    let result = '';
    selectedEls.forEach((el) => {
        result += el.value + '|';
    });
    result = result.slice(0,-1);
    console.log(result);
    // 출력
    // document.getElementById('result').innerText
    //   = result;
    return result;
}


function test1() {
    console.log("test1");
    var search = {}
    search["gender"] = $("#gender").val();
    search["age"] = $("#age").val();
    search["occupation"] = $("#occupation").val();
    var genre = getCheckboxValue();
    search["genres"] = genre;
    // console.log(genre);
    // search["genre"] = $("#genre").val();
    // genre
    console.log(search);
    $("#btn-search1").prop("disabled", true);

    $.ajax({
        url: "/users/recommendations",
        data: search,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        type: "post"
    }).done(function(datas)  {
        var result = "";
        var i;
        for (i=0; i < datas.length; i++) {
            var data = datas[i];
            // result += '<div class =result>';
            // result += '<h4 class="result_title">' + data.title + '</h4>';
            // result += '<p class="result_genre">' + data.genre + '</p>';
            // result += '<p class="result_imdb">' + data.imdb + '</p>';
            result += '<a href="' + data.imdb + '" target="_blank">';
            result += '<img src="'+ data.poster + '"width="300" height="400">';
            result += '</a>';
            // result += '</div>';
        }
        $("#result_list").html(result);
        $("#btn-search1").prop("disabled", false);
    });
}

function test2() {
    var search = {}
    search["title"] = $("#title").val();
    search["limit"] = $("#limit_hidden").val();
    console.log($("#limit_hidden").val());
    if ($("#limit").val() != "")
        search["limit"] = $("#limit").val();
    // search["sex"] = $("#sex").val();

    console.log(search);
    $("#btn-search2").prop("disabled", true);

    $.ajax({
        url: "/movies/recommendations",
        data: search,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        type: "post"
    }).done(function(datas) {
        var result = "";
        var i;
        for (i=0; i < datas.length; i++) {
            var data = datas[i];
            // result += '<div class =result>';
            // result += '<h4 class="result_title">' + data.title + '</h4>';
            // result += '<p class="result_genre">' + data.genre + '</p>';
            // result += '<p class="result_imdb">' + data.imdb + '</p>';
            result += '<a href="' + data.imdb + '" target="_blank">';
            result += '<img src="'+ data.poster + '" width="300" height="400">';
            result += '</a>';
            // result += '</div>';
        }
        $("#result_list").html(result);
        $("#btn-search2").prop("disabled", false);
     });
}