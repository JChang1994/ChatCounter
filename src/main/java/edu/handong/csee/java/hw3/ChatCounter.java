package edu.handong.csee.java.hw3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
				
			}
			else if(file.getPath().contains(".csv")) {
				reader.getMessagesFromCSVFiles(file);
			}
		}
	}
}
