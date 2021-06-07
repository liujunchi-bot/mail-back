package utils.logtool;

import java.io.File;

/*作者：李晓彤
 * 时间：2020.4.20
 * 功能：用户注册时建立新的文件夹
 * */

public class CreateDocument {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateDocument.createDocument("1234567wer");
	}
	public static boolean createDocument(String userName)
	{
		boolean isSuccess = false;
		File popF = new File("C:\\Users\\23521\\Desktop\\"+"log"+"\\"+userName+"\\"+"POP3");
		System.out.println(popF.getAbsolutePath());
		if(popF.exists())
		{
			try {
				throw new Exception("POP3文件夹已存在！创建文件夹失败！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			popF.mkdirs();//POP3协议文件夹
			System.out.println("POP3文件夹创建成功！");
		}
		File smtpF = new File("C:\\Users\\23521\\Desktop\\"+"log"+"\\"+userName+"\\"+"SMTP");
		System.out.println(smtpF.getAbsolutePath());
		if(smtpF.exists())
		{
			try {
				throw new Exception("SMTP文件夹已存在！创建文件夹失败！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			smtpF.mkdirs();
			System.out.println("SMTP文件夹创建成功！");
			isSuccess = true;
		}
		return isSuccess;
	}
}
