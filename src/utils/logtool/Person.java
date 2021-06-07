package utils.logtool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {
	private String userId;
	private String date;
	private String name;//协议类型
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		setDate();
		return date;
	}
	public void setDate() {
		this.date = df.format(new Date());
	}
}
