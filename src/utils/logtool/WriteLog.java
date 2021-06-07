package utils.logtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/*
 * 作者：李晓彤
 * 日期：2020.4.23
 * 功能：写入excel
 * */
public class WriteLog {
	// 传入参数：文件地址、用户名、协议类型、excel限制条数

//	public static void main(String[] args) {
//		WriteLog w = new WriteLog();
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "1LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "2LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "3456LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "456LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "56LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "6LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "7LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "8LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "9LXt", "POP3", 10);
//    	w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls", "10LXt", "POP3", 10);
//		w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail", "11LXt", "POP3", 10);
//		w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail", "12LXt", "POP3", 10);
//		w.write("E:\\李晓彤\\我的文档\\计算机网络\\javaMail", "13LXt", "POP3", 10);
//	}

	public void write(String fileName, String userID, String name, int limit) {
		// TODO
		ArrayList<String> listFileName = new ArrayList<String>();//存放文件夹下面所有的excel文件名
		try {
			FileHanding.getAllFileName(fileName, listFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileInputStream fs;
		boolean emptyFile = true;
		String emptyfileName = "";
		try {
			if(listFileName.size()==0)//长度为0，也就是空文件,新建文件
			{
				String s = fileName+"\\"+name+"-1.xls";
				emptyfileName = s;
				createExcelFile(s);
				fs = new FileInputStream(s);
			}
			else {
				fs = new FileInputStream(listFileName.get(listFileName.size()-1));
				emptyFile = false;
			}
//			POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
//			HSSFWorkbook wb = new HSSFWorkbook(ps);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0); // 获取到工作表
			int rowNumbers = sheet.getLastRowNum() + 1;// 因为getLastRowNum()起始是0
			if(rowNumbers>limit)//最后一个表满了
			{
				String s = listFileName.get(listFileName.size()-1);
				String newFileName = s.substring(0, s.length()-5)+(Integer.parseInt(s.substring(s.length()-5,s.length()-4))+1)+".xls";
				Person p = new Person();
				p.setUserId(userID);
				p.setName(name);
				writeNewExcel(p,newFileName);
			}
			else//在最后写
			{
				String nowFileName = null;
				if(emptyFile)//空文件
				{
					nowFileName = emptyfileName;
				}
				else
				{
					nowFileName = listFileName.get(listFileName.size()-1);
				}

				Person p = new Person();
				p.setUserId(userID);
				p.setName(name);
				writeNowExcel(p,nowFileName);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("最后一个对应的文件不存在！");
			e.printStackTrace();
		}
	}

	// 如果没有溢出，就写在现在的filename下，往后写
	private void writeNowExcel(Person p, String fileName) {
		FileInputStream fs;
		try {
			fs = new FileInputStream(fileName);
			POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
			HSSFWorkbook wb = new HSSFWorkbook(ps);
			HSSFSheet sheet = wb.getSheetAt(0); // 获取到工作表
			HSSFRow row = sheet.getRow(0);
			int hang = 0;// 行数
			if ("".equals(row) || row == null) {
				hang = 0;
			} else {
				hang = sheet.getLastRowNum() + 1;
//	        	 hang=hang+1;
			}
			// 分别得到最后一行的行号，和一条记录的最后一个单元格
			FileOutputStream out = new FileOutputStream(fileName); // 向已有的file后追加数据
			row = sheet.createRow((short) (hang)); // 在现有行号后追加数据
			row.createCell(0).setCellValue(p.getUserId()); // 设置第一个（从0开始）单元格的数据
			row.createCell(1).setCellValue(p.getName()); // 设置第二个（从0开始）单元格的数据
			row.createCell(2).setCellValue(p.getDate()); // 设置第三个（从0开始）单元格的数据
			out.flush();
			wb.write(out);
			out.close();
			System.out.println("已向末尾追加完数据！");
			System.out.println("现在excel行数为：" + sheet.getLastRowNum());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("fileName对应的文件不存在！");
			e.printStackTrace();
		}
	}

	// 如果出现溢出，并且最后一个文件也满了，那就建新的文件写入
	private void writeNewExcel(Person p,String fileName) {
		// TODO
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Log");

		// 创建Excel标题行，第一行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("用戶名");
		headRow.createCell(1).setCellValue("协议名");
		headRow.createCell(2).setCellValue("时间");
		// 往Excel表中写入数据
		createCell(p, sheet);
		File xlsFile = new File(fileName);
		try {
			workbook.write(xlsFile);
		} catch (IOException e) {
			// TODO
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO
			}
		}
	}

	// 写入excel表的一行数据。
	private void createCell(Person person, HSSFSheet sheet) {
		HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow.createCell(0).setCellValue(person.getUserId());
		dataRow.createCell(1).setCellValue(person.getName());
		dataRow.createCell(2).setCellValue(person.getDate());
	}

	//创建excel表
	private void createExcelFile(String path)
	{
		HSSFWorkbook hWorkbook = null;
		XSSFWorkbook xWorkbook = null;
		//创建workbook
		hWorkbook = new HSSFWorkbook();
		//新建文件
		FileOutputStream fileOutputStream = null;
		HSSFRow row = null;
		try {
			//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
			String sheetName = "Log";
			String titleRow[] = new String[] {
					"userID","协议名","时间"
			};
			hWorkbook.createSheet(sheetName);
			hWorkbook.getSheet(sheetName).createRow(0);
			//添加表头, 创建第一行
			row = hWorkbook.getSheet(sheetName).createRow(0);

			for (short j = 0; j < titleRow.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(titleRow[j]);
			}
			fileOutputStream = new FileOutputStream(path);
			hWorkbook.write(fileOutputStream);
			System.out.println("创建成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("创建失败！");
			e.printStackTrace();
		}finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
