// 验证输入密码是否符合要求
function checkPassword(){
    var pwd1 = document.getElementById("newPassword").value;/*获取输入框的内容*/
  if(pwd1.length < 6){/*长度小于6位*/
       document.getElementById("newPasswordPrompt").innerHTML="<p style='color: red'>密码长度要大于6位!</p>";
       return false;
  }
  else{
      document.getElementById("newPasswordPrompt").innerHTML="";
  }
  var pwd2 = document.getElementById("againPassword").value;
<!-- 对比两次输入的密码 -->
    if(pwd1 == pwd2)
     {
         document.getElementById("againPasswordPrompt").innerHTML="";
         return true;
     }
    else {
        document.getElementById("againPasswordPrompt").innerHTML="<p style='color: red'>两次密码不相同</p>";
        alert("两次密码不相同!");
       return false;
    }

 }