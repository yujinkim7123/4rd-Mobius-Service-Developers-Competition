userInputType = "ae"; // default

var addr = "http://203.253.128.161:7579";

var contentType = {
    "ae":"application/vnd.onem2m-res+json; ty=2",
    "cnt":"application/vnd.onem2m-res+json; ty=3",
    "cin":"application/json; ty=4"
};

function makeDataFormat(type, value){
    var data = {
        "ae": {
            "m2m:ae" : {
                "rn": value,
                    "api": "0.2.481.2.0001.001.000111",
                    "lbl": ["key1", "key2"],
                    "rr": true,
                    "poa": ["http://203.254.173.104:9727"]
            }
        },
        "cnt": {
            "m2m:cnt": {
                "rn": value,
                "lbl": ["ss"],
                "mbs": 16384
            }
        },
        "cin": {
            "m2m:cin": {
                "con": value
            }
        }
    };
    return data[type];
}

document.getElementById('send').addEventListener('submit', function (e) {
    e.preventDefault();

    var type = e.target.type;
    type = type.options[type.selectedIndex].value;
    url = addr + e.target.url.value;
    data = makeDataFormat(type, e.target.val.value)
    
    var settings = {
        "url": "http://203.253.128.161:7579/Mobius/sch20171518/time_test/la",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Accept": "application/json",
          "X-M2M-RI": "12345",
          "X-M2M-Origin": "SOrigin"
        },
      };
      
      $.ajax(settings).done(function (response) {
        var res = JSON.stringify(response, null, 2);
        document.getElementById("res").innerHTML = res;
      })
      .fail(function (response) {
        var res = JSON.stringify(response, null, 2);
        document.getElementById("res").innerHTML = res;
    });
});


