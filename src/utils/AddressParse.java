package utils;

import java.util.HashMap;
import java.util.Map;

/*
Qiao Qing
2020/04/04
*/

/*
用法：
AddressInfo info=new AddressParse().parse("qiao@qq.com");
if(info==null)
    System.out.println("暂不支持该邮箱域名！");
else {
    info.getPop3Port();
    info.getSmtpPort();
    info.getPop3Server();
    info.getSmtpServer();
}
 */


public class AddressParse {
    Map<String,AddressInfo> map=new HashMap<String,AddressInfo>();

    public AddressInfo parse(String address) {
        String effAddress=address.substring(address.indexOf("@")+1);
        return map.getOrDefault(effAddress,null);
    }

    public AddressParse() {
        map.put("sina.com",new AddressInfo("pop3.sina.com.cn", 110,
                "smtp.sina.com.cn",25));
        map.put("sina.cn",new AddressInfo("pop3.sina.com", 110,
                "smtp.sina.com",25));
        map.put("vip.sina.com",new AddressInfo("pop3.vip.sina.com", 110,
                "smtp.vip.sina.com",25));
        map.put("sohu.com",new AddressInfo("pop3.sohu.com", 110,
                "smtp.sohu.com",25));
        map.put("126.com",new AddressInfo("pop.126.com", 110,
                "smtp.126.com",25));
        map.put("139.com",new AddressInfo("POP.139.com", 110,
                "SMTP.139.com",25));
        map.put("163.com",new AddressInfo("pop.163.com", 110,
                "smtp.163.com",25));
        map.put("qq.com",new AddressInfo("pop.qq.com", 110,
                "smtp.qq.com",25));
        map.put("yahoo.com.cn",new AddressInfo("pop.mail.yahoo.com.cn", 995,
                "smtp.mail.yahoo.com.cn",587));
        map.put("google.com",new AddressInfo("pop.gmail.com", 995,
                "smtp.gmail.com",587));
        map.put("263.net",new AddressInfo("pop3.263.net", 110,
                "smtp.263.net",25));
        map.put("263.net.cn",new AddressInfo("pop.263.net.cn", 110,
                "smtp.263.net.cn",25));
        map.put("x263.net",new AddressInfo("pop.x263.net", 110,
                "smtp.x263.net",25));
        map.put("21cn.com",new AddressInfo("pop.21cn.com", 110,
                "smtp.21cn.com",25));
        map.put("foxmail.com",new AddressInfo("POP.foxmail.com", 110,
                "SMTP.foxmail.com",25));
        map.put("china.com",new AddressInfo("pop.china.com", 110,
                "smtp.china.com",25));
        map.put("tom.com",new AddressInfo("pop.tom.com", 110,
                "smtp.tom.com",25));
        map.put("yeah.net",new AddressInfo("pop.yeah.net", 110,
                "smtp.yeah.net",25));
    }
}
