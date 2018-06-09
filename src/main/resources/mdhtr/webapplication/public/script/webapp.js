(function() {
    getResource("./api/message", displayResult);

    function displayResult(result) {
        var paragraph = document.createElement("p");
        paragraph.classList.add("center")
        paragraph.innerHTML = result + " - displayed through a script!"
        document.getElementsByTagName('body')[0].appendChild(paragraph);
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