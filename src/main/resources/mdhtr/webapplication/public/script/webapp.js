(function() {
    getResource("./api/message", displayResult);

    function displayResult(result) {
        var messages = JSON.parse(result);
        var messagesDiv = document.getElementById("messages");
        messages.forEach(function(item) {
            var div = document.createElement("div");
            div.innerHTML = item["message"];
            messagesDiv.appendChild(div);
        })
    }

    function getResource(url, callback) {
        var xhr = createRequest();
        xhr.open("GET", url, true);
        xhr.onload = function (e) {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    callback(xhr.responseText);
                } else {
                    console.error(xhr.statusText);
                }
            }
        };
        xhr.onerror = function (e) {
            console.error(xhr.statusText);
        };
        xhr.send(null);
    }

    function createRequest() {
        var request;
        try {
            request = new XMLHttpRequest();
        } catch (tryMS) {
            try {
                request = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (otherMS) {
                try {
                    request = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (failed) {
                    console.log("WARNING: Ajax request not possible on your browser");
                }
            }
        }
        return request;
    }
})();