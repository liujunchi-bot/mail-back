package utils.logtool;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*处理传入的文件夹地址，将该文件中的每个excel读取*/
public class FileHanding {
	public static String base = "C:\\Users\\23521\\Desktop\\mail\\wemail_master_6.13\\wemail_master\\web\\";
	public static String basepath = "C:\\Users\\23521\\Desktop\\mail\\wemail_master_6.13\\wemail_master\\web\\log";
	//	public static void main(String[] args) {
//		ArrayList<String> listFileName = new ArrayList<String>();//存放文件夹下面所有的excel文件名
//		try {
//			FileHanding.getAllFileName("E:\\李晓彤\\我的文档\\软件需求\\数据字典范例文档", listFileName);
//			System.out.println(listFileName.get(listFileName.size()-1));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	public static int getAllFileName(String path, ArrayList<String> listFileName) throws Exception {
		if (path != null) {
			File file = new File(path);
			File[] files = file.listFiles();
			String[] names = file.list();

			for (File a : files) {
				if (a.isDirectory()) {// 如果文件夹下有子文件夹，抛出错误
					System.out.println("有子文件夹，错误！");
					throw new Exception(path + "下有子文件夹，错误！");
				}
			}
			if (names != null) {// names不为空，说明里面有文件
				String[] completNames = new String[names.length];
				for (int i = 0; i < names.length; i++) {
					completNames[i] = path +"\\"+ names[i];
				}
				listFileName.addAll(Arrays.asList(completNames));
			}
		} else
			throw new Exception("path为空！");
//		names为空，说明file里面没有文件，是个空的文件夹
		return 0;
	}
	//获取路径
	public static List<String> findFilePaths(String path, List<String> filePaths) {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
			return filePaths;
		}
		String[] files = dir.list();// 读取目录下的所有目录文件信息
		for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
			File file = new File(dir, files[i]);
			path = file.getPath();
			if (file.isFile()) {// 如果文件
				filePaths.add(dir + "\\" + file.getName());// 添加文件全路径名
			} else {// 如果是目录
				findFilePaths(path, filePaths);// 回调自身继续查询
			}
		}
		return filePaths;
	}
	//获取相对路径
	public static List<String> getFilePaths(String path, List<String> filePaths){
		List<String> list = FileHanding.findFilePaths(path,filePaths);
		List<String> relativeList = new ArrayList<>();
		int length = FileHanding.base.length();
		for(String str : list){
			System.out.println(str);
			relativeList.add(str.substring(length,str.length()));
		}
		for(String str : relativeList){
			System.out.println(str);
		}
		return relativeList;
	}
	//获取文件名
	public static List<String> getFileNames(){
		List<String> fileName = new ArrayList<String>();
		List<String> filePath = new ArrayList<String>();
		new FileHanding().findFilePaths(FileHanding.basepath,filePath);

		int lastIndex;
		for(String str : filePath) {
			lastIndex = str.lastIndexOf("\\");
			fileName.add(str.substring(lastIndex+1, str.length()));
			System.out.println(str);
		}
		for(String str : fileName) {
			System.out.println(str);
		}
		return fileName;
	}

	public static void main(String[] args) {

	}
}
