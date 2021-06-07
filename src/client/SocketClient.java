package client;



import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Date;

public class SocketClient {
    // 搭建客户端

    public static void sendMail(String from,String to,String subject,String content) throws IOException {
        Socket socket=new Socket("127.0.0.1",5209);
        // 向本机的5209端口发出客户请求
        PrintWriter write = new PrintWriter(socket.getOutputStream());
        // 由Socket对象得到输出流，并构造PrintWriter对象
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 由Socket对象得到输入流，并构造相应的BufferedReader对象
        try {
            String readline;

            write.println("Mail From:<" + from + ">");
            write.println("RCPT TO:<" + to + ">");

            Date nowDate = new Date();
            write.println("DATE:" + nowDate.toString());
            Base64 base64 = new Base64();
            write.println("Subject:" + base64.encodeToString(subject.getBytes("UTF-8")));
            write.println("");
            write.println(base64.encodeToString(content.getBytes("UTF-8")));
            write.println(".");
            write.flush();


        } catch (Exception e) {
            System.out.println("can not listen to:" + e);// 出错，打印出错信息
        }
        finally {
            if(write!=null){
                write.close(); // 关闭Socket输出流
            }
            if(in!=null){
                in.close(); // 关闭Socket输入流
            }
            if(socket!=null){
                socket.close();  // 关闭Socket
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        // 1、创建客户端Socket，指定服务器地址和端口
//        Socket socket=new Socket("127.0.0.1",5209);
//    //            Socket socket = new Socket("192.168.1.115", 5209);
//        System.out.println("客户端启动成功");
//        // 2、获取输出流，向服务器端发送信息
//        // 向本机的5209端口发出客户请求
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        // 由系统标准输入设备构造BufferedReader对象
//        PrintWriter write = new PrintWriter(socket.getOutputStream());
//        // 由Socket对象得到输出流，并构造PrintWriter对象
//        //3、获取输入流，并读取服务器端的响应信息
//        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        // 由Socket对象得到输入流，并构造相应的BufferedReader对象
//        try {
//            String readline;
//            readline = br.readLine(); // 从系统标准输入读入一字符串
//            while (!readline.equals("end")) {
//                // 若从标准输入读入的字符串为 "end"则停止循环
//                write.println(readline);
//                // 将从系统标准输入读入的字符串输出到Server
//                write.flush();
//                // 刷新输出流，使Server马上收到该字符串
//                System.out.println("Client:" + readline);
//                // 在系统标准输出上打印读入的字符串
////                System.out.println("Server:" + in.readLine());
//                // 从Server读入一字符串，并打印到标准输出上
//                readline = br.readLine(); // 从系统标准输入读入一字符串
//            } // 继续循环
//
//        } catch (Exception e) {
//            System.out.println("can not listen to:" + e);// 出错，打印出错信息
//        }
//        finally {
//            //4、关闭资源
//            write.close(); // 关闭Socket输出流
//            in.close(); // 关闭Socket输入流
////            socket.close(); // 关闭Socket
//            socket.shutdownOutput();
//        }

        sendMail("285013442@test.com","79555574@test.com","test","There are four words.");

    }

}