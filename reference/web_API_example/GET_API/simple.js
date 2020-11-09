var addr = "http://203.253.128.161:7579";


document.getElementById('send').addEventListener('submit', function (e) {
    e.preventDefault();

    url = addr + e.target.url.value;
    var settings = {
        "url": url,
        "method": "GET",
        "timeout": 0,
        "headers": {
            "Accept": "application/json",
            "X-M2M-RI": "12345",
            "X-M2M-Origin": "SOrigin"
        },
    };
    $.ajax(settings)
    .done(function (response) {
        var res = JSON.stringify(response, null, 2);
        document.getElementById("res").innerHTML = res; # 값 받아오는 것을 성공했을 경우에 동작 정의
    })
    .fail(function (response) {
        var res = JSON.stringify(response, null, 2);
        document.getElementById("res").innerHTML = res; # 값을 받아오는 것에 실패했을 경우에 동작 정의
    });
});


