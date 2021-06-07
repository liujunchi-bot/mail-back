package utils.logtool;

import java.io.File;

public class DeleteLog {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(DeleteLog.delete("E:\\李晓彤\\我的文档\\计算机网络\\javaMail\\POP3-1.xls"));
    }
    public static boolean delete(String path) {
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }
}
