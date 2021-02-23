package model;

import java.sql.Date;

public class Notificable {
	
	private long taskId;
	private String taskTitle;
	private Date taskExpirationDate;
	private long panelId;
	private String panelName;
	private long userId;
	private String phone;
	
	
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public Date getTaskExpirationDate() {
		return taskExpirationDate;
	}
	public void setTaskExpirationDate(Date taskExpirationDate) {
		this.taskExpirationDate = taskExpirationDate;
	}
	public long getPanelId() {
		return panelId;
	}
	public void setPanelId(long panelId) {
		this.panelId = panelId;
	}
	public String getPanelName() {
		return panelName;
	}
	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	

}
