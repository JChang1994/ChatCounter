package edu.handong.csee.java.hw3;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ChatCounter {
	String inputPath;
	String outputPath;
	boolean help;
	
	public static void main(String[] args) {
		ChatCounter counter = new ChatCounter();
		counter.run(args);
	}
	
	private Options setOptions() {
		Options options = new Options();
		options.addOption(Option.builder("i").longOpt("inputdir")
			.desc("Set a path of a directory to retrieve chat data")
			.hasArg()
			.argName("Path name to retrieve from")
			.required()
			.build());
		
		options.addOption(Option.builder("o").longOpt("outputdir")
			.desc("Set a path to put the output file with the count results")
			.hasArg()
			.argName("Path name to output to")
			.required()
			.build());
		
		options.addOption(Option.builder("h").longOpt("help")
			.desc("Help")
			.build());
		
		return options;
	}
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Chat Counter Program";
		formatter.printHelp("ChatCounter", options, help);
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		String path = null;
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
			inputPath = cmd.getOptionValue("i");
			outputPath = cmd.getOptionValue("o");
			help = cmd.hasOption("h");
		} catch (ParseException e) {
			printHelp(options);
			return false;
		}
		return true;
	}
	
	private void run(String[] args) {
		Options options = setOptions();
		if(parseOptions(options, args)) {
			if(help) {
				printHelp(options);
				return;
			}
		}
		
		DataReader reader = new DataReader();
		File directory = reader.getDirectory(inputPath);
		File[] chatFiles = reader.getListOfFilesFromDirectory(directory);
		
		for(File file : chatFiles) {
			if(file.getPath().contains(".txt")) {
				reader.getMessagesFromTXTFiles(file);
			}
			else if(file.getPath().contains(".csv")) {
				reader.getMessagesFromCSVFiles(file);
			}
		}
		
		DataWriter writer = new DataWriter();
		writer.writeAFile(sortByValue(countMessages(reader.getData())), outputPath);
	}
	
	private HashMap<String, Integer> countMessages(HashMap<String,ArrayList<Message>> messages){
		HashMap<String, Integer> counter = new HashMap<String,Integer>();
		
		for(String key:messages.keySet()) {
			counter.put(key,  messages.get(key).size());
		}
		
		return counter;
	}
	
	private ArrayList<String> sortByValue(final HashMap<String,Integer> map){
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(map.keySet());
		
		Collections.sort(list,new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object o1, Object o2) {
				Object v1 = map.get(o1);
				Object v2 = map.get(o2);
				
				return((Comparable<Object>) v1).compareTo(v2);
			}
		});
		Collections.reverse(list);;
		return list;
	}
}
