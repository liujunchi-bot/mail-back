package server;

import dao.MailMgr;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

public class SocketService {

    String context ;
    String from;
    String to;
    String subject;
    String date;
    static ServerSocket server=null;
//    public String parseCommand(String command){
//        if(command == null || "".equals(command)){
//            return "ERR";
//        }
////        if("HELO smtp.test.com".equals(command)){
////            return "250 OK";
////        }
////        else if("AUTH LOGIN".equals(command)){
////            //这里应该是验证账号密码
////            return "334";
////        }
////        else if("AUTH LOGIN".equals(command)){
////            //这里应该是验证账号密码
////            return "334";
////        }
//        if(command.startsWith("Mail From:")){
//            return command.substring(10,command.length()-1);
//        }
//        else if(command.startsWith("RCPT TO:")){
//            return command.substring(8,command.length()-1);
//        }
//        else if(command.startsWith("DATA")){
//            return command.substring(4);
//        }
//
//
//    }



    public  void oneServer(){
        try{
//            ServerSocket server=null;
//            try{
//                server=new ServerSocket(5209);
//                //b)指定绑定的端口，并监听此端口。
//                System.out.println("服务器启动成功");
//                //创建一个ServerSocket在端口5209监听客户请求
//            }catch(Exception e) {
//                System.out.println("没有启动监听："+e);
//                //出错，打印出错信息
//            }
            Socket socket=null;
            try{
                socket=server.accept();
                //2、调用accept()方法开始监听，等待客户端的连接
                //使用accept()阻塞等待客户请求，有客户
                //请求到来则产生一个Socket对象，并继续执行
            }catch(Exception e) {
                System.out.println("Error."+e);
                //出错，打印出错信息
            }
            //3、获取输入流，并读取客户端信息
            String line;
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //由Socket对象得到输入流，并构造相应的BufferedReader对象
            PrintWriter writer=new PrintWriter(socket.getOutputStream());
            //由Socket对象得到输出流，并构造PrintWriter对象
//            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            //由系统标准输入设备构造BufferedReader对象
            //System.out.println("Client:"+in.readLine());
            //在标准输出上打印从客户端读入的字符串
//            line=br.readLine();
            //从标准输入读入一字符串
            //4、获取输出流，响应客户端的请求
            String command = in.readLine();
            System.out.println("Client:"+command);
            while(true){
                if(command == null || "".equals(command)){
                    //接下来是正文
                    context = "";
                    while(true){
                        command = in.readLine();
                        System.out.println("Client:"+command);
                        if(command.toLowerCase().equals(".")){
                            break;  //正文读取完毕
                        }
                        context += command;
                    }
                }

                if(command.toLowerCase().equals(".")){
                    break;  //正文读取完毕
                }

                if(command.startsWith("Mail From:")){
                    from = command.substring(11,command.length()-1);
                }
                else if(command.startsWith("RCPT TO:")){
                    to = command.substring(9,command.length()-1);
                }
                else if(command.startsWith("DATE:")){
                    date = command.substring(5);
                }
                else if(command.startsWith("Subject:")){
                    subject = command.substring(7);
                }


                command = in.readLine();
                //从系统标准输入读入一字符串
                System.out.println("Client:"+command);
            } //继续循环

//            writer.println("OK");
            writer.flush();
//            MailMgr mailmgr = null;
//            mailmgr = new MailMgr();
//            Base64 base64 = new Base64();
//            subject = new String(base64.decode(subject));
//            context = new String(base64.decode(context));
//            Timestamp Date = new Timestamp(System.currentTimeMillis());
//            mailmgr.addMail(from,to, Date, subject,context,1,context.length());
            System.out.println("from: " + from);
            System.out.println("to: " + to);
            System.out.println("date: " + date);
            System.out.println("subject: " + subject);
            System.out.println("context: " + context);
            //在系统标准输出上打印读入的字符串
            System.out.println("Client: "+in.readLine());
            //从Client读入一字符串，并打印到标准输出上

            //5、关闭资源
            writer.close(); //关闭Socket输出流
            in.close(); //关闭Socket输入流
//            socket.close(); //关闭Socket
            socket.shutdownOutput();
            server.close(); //关闭ServerSocket
        }catch(Exception e) {//出错，打印出错信息
//            System.out.println("Error."+e);
            System.out.println("连接已断开");
        }
    }

    //搭建服务器端
    public static void main(String[] args) throws IOException{
        SocketService socketService = new SocketService();
        //1、a)创建一个服务器端Socket，即SocketService

        try{
            server=new ServerSocket(5209);
            //b)指定绑定的端口，并监听此端口。
            System.out.println("服务器启动成功");
            //创建一个ServerSocket在端口5209监听客户请求
        }catch(Exception e) {
            System.out.println("没有启动监听："+e);
            //出错，打印出错信息
        }
        while(true){
            socketService.oneServer();
        }
    }

}