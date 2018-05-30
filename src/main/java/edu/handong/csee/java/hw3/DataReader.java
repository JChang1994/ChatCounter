package edu.handong.csee.java.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class DataReader {
	public File getDirectory(String strDir) {
		File dataDir = new File(strDir);
		return dataDir;
	}
	
	public File[] getListOfFilesFromDirectory(File dataDir) {
		File dirPath = dataDir;
		return dirPath.listFiles();
	}
	
	private HashMap<String,ArrayList<Message>> getMessagesFromCSVFiles(File file){
		HashMap<String,ArrayList<Message>> messages = new HashMap<String,ArrayList<Message>>();
		Reader in;
		
		try {
			in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
			for(CSVRecord record : records) {
				String date = record.get(0).substring(11,16);
				String user = record.get(1);
				String strMessage = record.get(2);
				
				Message message = new Message(date, record.get(0), user, strMessage);
				
				if(!messages.containsKey(message.getUser())) {
					messages.put(user, new ArrayList<Message>());
				}
				
				if(!existingMessage(messages,message))
					messages.get(message.getUser()).add(message);
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return messages;
	}
	
	private boolean existingMessage(HashMap<String, ArrayList<Message>> messagesToCheck, Message message) {
		ArrayList<Message> messages = messagesToCheck.get(message.getUser());
		
		for(Message messageInMessages:messages) {
			String dateTimeOfMessageInMessages = messageInMessages.getDateTime();
			String dateTimeOfMessageToCheck = message.getDateTime();
			
			if(dateTimeOfMessageInMessages.equals(dateTimeOfMessageToCheck) && messageInMessages.getUser().equals(message.getUser())) {
				if((message.getMessage().equals("Photo") && messageInMessages.getMessage().equals("사진"))
						|| (message.getMessage().equals("사진") && messageInMessages.getMessage().equals("Photo"))) {
					return true;
				}
			}
			
			String strMessageInMessages = messageInMessages.getMessage().trim();
			String strNewMessage = message.getMessage().trim();
			
			int length = strMessageInMessages.length();
			int lengthInNewMessage = strNewMessage.length();
			
			String shortMessage = "";
			String longMessage = "";
			if(length<lengthInNewMessage) {
				shortMessage = strMessageInMessages;
				longMessage = strNewMessage;
			} else {
				shortMessage = strNewMessage;
				longMessage = strMessageInMessages;
			}
			
			if(longMessage.startsWith(shortMessage))
				return true;
		}
		return false;
	}
	
	private HashMap<String,ArrayList<Message>> getMessagesFromTXTFiles(File file){
		String thisLine = "";
		try {
			BufferedReader br = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}