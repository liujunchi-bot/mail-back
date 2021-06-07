/*设置cookie*/
/*setCookie(cname, cvalue, exdays)三个参数分别是存放的cookie名字、cookie值、cookie有效小时数*/
function setCookie(cname, cvalue, exdays) {
    var loginTime = new Date();
    loginTime.setTime(loginTime.getTime() + (exdays*60*60*1000));
    var expires = "expires="+loginTime.toUTCString();
    document.cookie = cname + "=" + escape(cvalue) + "; " + expires;/*使用escape() 函数对字符串进行编码*/
}

/*获取cookie*/
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)===' ') c = c.substring(1);
        if (c.indexOf(name) !== -1){
            var cnameValue = unescape(c.substring(name.length, c.length));/*使用unescape()函数解码*/
            return cnameValue;
        }
    }
    return "";
}

/*清除cookie*/
function clearCookie(cname) {
    setCookie(cname, "", -1);
}