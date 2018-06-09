(function() {
    var paragraph = document.createElement("p");
    paragraph.classList.add("center")
    paragraph.innerHTML = "This text was added by a script!"
    document.getElementsByTagName('body')[0].appendChild(paragraph);
})();