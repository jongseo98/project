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

function test1() {
    console.log("test1");
    var search = {}
    search["gender"] = $("#gender").val();
    search["age"] = $("#age").val();
    search["occupation"] = $("#occupation").val();
    search["genres"] = $("#genres").val();

    console.log(search);
    $("#btn-search1").prop("disabled", true);

    $.ajax({
        url: "/users/recommendations",
        data: search,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        type: "post"
    }).done(function(data)  {
        console.log(data);
    });
}

function test2() {

    var search = {}
    search["title"] = $("#title").val();
    search["limit"] = $("#limit").val();

    console.log(search);
    $("#btn-search2").prop("disabled", true);

    $.ajax({
        url: "/movies/recommendations",
        data: search,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        type: "post"
    }).done(function(datas) {
        console.log(datas);
        var result;
        var i;
        for (i=0; i < datas.length; i++) {
            var data = datas[i];
            result += '<div class =result>';
            result += '<h4 class="result_title">' + data.title + '</h4>';
            result += '<p class="result_genre">' + data.genre + '</p>';
            result += '<p class="result_imdb">' + data.imdb + '</p>';
            result += '</div>';
        }
        console.log(result);
        $(".result_list").html(result);
     });
}