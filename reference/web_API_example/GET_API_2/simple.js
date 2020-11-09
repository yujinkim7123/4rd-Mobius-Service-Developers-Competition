var addr = "http://203.253.128.161:7579";


document.getElementById('send').addEventListener('submit', function (e) {
    e.preventDefault();
    var cra = e.target.cra.value;
    var crb = e.target.crb.value;
    cra = cra.replace(/-/g,"");
    crb = crb.replace(/-/g,"");
    url = addr + e.target.url.value + "?rcn=4&cra=" + cra + "&crb=" + crb;
    console.log(url);
    
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
        document.getElementById("res").innerHTML = res;
    })
    .fail(function (response) {
        var res = JSON.stringify(response, null, 2);
        document.getElementById("res").innerHTML = res;
    });
});


