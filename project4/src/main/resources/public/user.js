$(document).ready(function() {
    $('#search-form1').submit(function (event) {
        event.preventDefault();
        test1();
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
        url: "/LINKER/users/recommendations",
        data: search,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        type: "post"
    }).done(function(datas)  {
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
        $("#btn-search1").prop("disabled", false);
    });
}