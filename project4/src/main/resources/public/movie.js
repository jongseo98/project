$(document).ready(function() {
    $('#search-form2').submit(function (event) {
        event.preventDefault();
        test2();
    });
});

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
        url: "/LINKER/movies/recommendations",
        data: search,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        type: "post"
    }).done(function(datas) {
        var result = "";
        var i;
        for (i=0; i < datas.length; i++) {
            var data = datas[i];
            result += '<div style="width:300px; height:400px; background-color:white; float:left; border:1px solid black">'
            result += '<a href="' + data.imdb + '" target="_blank">';
            if (data.poster == "noimg.png")
            {
                result += '<p style="text-align:center;">' + data.title + "</p>";
                result += '<img src="'+ data.poster + '"width="300px" height="300px">';
            } else {
                result += '<img src="'+ data.poster + '"width="300px" height="400px">';
            }
            result += '</a>';
            result += '</div>';
        }
        $("#result_list").html(result);
        $("#btn-search2").prop("disabled", false);
     });
}