package edu.handong.csee.java.hw3;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DataWriter {
	public void writeAFile(ArrayList<String> lines, String targetFileName) {
		try {
			File file = new File(targetFileName);
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(fos);
			
			for(String line:lines) {
				dos.write((line+"\n").getBytes());
			}
			dos.close();
			fos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
