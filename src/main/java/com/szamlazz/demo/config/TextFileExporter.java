package com.szamlazz.demo.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

 

@Service
public class TextFileExporter implements FileExporter {

	 

	private static final String EXPORT_DIRECTORY_CREATE = "C:\\Szamla-Agent\\NYUGTA-CREATE";
	private static final String EXPORT_DIRECTORY_GET = "C:\\Szamla-Agent\\NYUGTA-GET";
	private static final String EXPORT_DIRECTORY_STORNO = "C:\\Szamla-Agent\\NYUGTA-STORNO";
	
	private Logger logger = LoggerFactory.getLogger(TextFileExporter.class);
	
	@Override
	public Path export(String fileContent, String fileName) {
		String EXPORT_DIRECTORY = "";

		if (fileName.contains("Get")){
			EXPORT_DIRECTORY =  EXPORT_DIRECTORY_GET;
		}
		if (fileName.contains("Create")){
			EXPORT_DIRECTORY =  EXPORT_DIRECTORY_CREATE;
		}
		if (fileName.contains("Storno")){
			EXPORT_DIRECTORY =  EXPORT_DIRECTORY_STORNO;
		}

		Path filePath = Paths.get(EXPORT_DIRECTORY, fileName);
		try {
			Path exportedFilePath = Files.write(filePath, fileContent.getBytes(), StandardOpenOption.CREATE);
			return exportedFilePath;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}	
		return null;
	}
}