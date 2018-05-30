package edu.handong.csee.java.hw3;

public class Message {
	String date;
	String dateTime;
	String user;
	String strMessage;
	
	public Message(String date, String dateTime, String user, String strMessage) {
		this.date = date;
		this.dateTime = dateTime;
		this.user = user;
		this.strMessage = strMessage;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getDateTime() {
		return this.dateTime;
	}
	
	public String getMessage() {
		return this.strMessage;
	}
}
