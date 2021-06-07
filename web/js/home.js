function opens(obj) {
    for (var i = 0; i <= 6; i++) {
        if (i === obj) {
            document.getElementById("con" + i).style.display = "block";
            document.getElementById("title" + i).className="selected m-flag";
        } else{
            document.getElementById("con" + i).style.display = "none";
            document.getElementById("title" + i).className="unselected m-flag";
        }
    }
}
