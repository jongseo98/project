$(document).ready(function() {
    homeAll();
});

function homeAll() {
    console.log("homeAll");
    var search = {}
    search["gender"] = "";
    search["age"] = "";
    search["occupation"] = "";
    search["genres"] = "";
    console.log(search);

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
        $("#result_all").html(result);
        homeAction();
    });
}

function homeAction() {
    console.log("homeAction");
    var search = {}
    search["gender"] = "";
    search["age"] = "";
    search["occupation"] = "";
    search["genres"] = "Action";
    console.log(search);

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
        $("#result_action").html(result);
        homeDrama();
    });
}

function homeDrama() {
    console.log("homeDrama");
    var search = {}
    search["gender"] = "";
    search["age"] = "";
    search["occupation"] = "";
    search["genres"] = "Drama";
    console.log(search);

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
        $("#result_drama").html(result);
        homeAni();
    });
}

function homeAni() {
    console.log("homeAni");
    var search = {}
    search["gender"] = "";
    search["age"] = "";
    search["occupation"] = "";
    search["genres"] = "Animation";
    console.log(search);

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
        $("#result_ani").html(result);
    });
}