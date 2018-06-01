package com.boe.dacrestapi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boe.dacrestapi.model.UploadModel;
import com.boe.dacrestapi.utils.MessageDigestUtils;

@RestController
public class RestUploadController {

	@Value("${web.upload-path}")
	private String UPLOADED_FOLDER;
	
	@PostMapping("/api/single/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

		if (uploadfile.isEmpty()) {
			return new ResponseEntity<>("请选择一个文件!", HttpStatus.OK);
		}
		File targetFile = null;
		try {
			File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
			if(!rootPath.exists()) rootPath = new File("");
			File upload = new File(rootPath.getAbsolutePath(),"static/images/upload/");
			if (!upload.exists())
				upload.mkdirs();
			String filename = uploadfile.getOriginalFilename();
			String extString = filename.substring(filename.lastIndexOf(".") + 1);
			String newFileName = MessageDigestUtils.md5(filename) + "." + extString;
			targetFile = new File(upload, newFileName);
			saveUploadedFiles(uploadfile,targetFile);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(targetFile.getName(), new HttpHeaders(),
				HttpStatus.OK);

	}

	@PostMapping("/api/upload")
	public ResponseEntity<?> uploadFile(@ModelAttribute UploadModel model) {
		try {
			 
            saveUploadedFiles(Arrays.asList(model.getFiles()));
 
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
 
        return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
	}
	
	private void saveUploadedFiles(MultipartFile file,File newFile) throws IOException {

		if (file.isEmpty()) {
			return;
		}

		byte[] bytes = file.getBytes();
		Path path = Paths.get(newFile.toString());
		Files.write(path, bytes);

	}
	
	private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
		File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
		if(!rootPath.exists()) rootPath = new File("");
		File upload = new File(rootPath.getAbsolutePath(),"static/images/upload/");
		if (!upload.exists())
			upload.mkdirs();
        for (MultipartFile file : files) {
 
            if (file.isEmpty()) {
                continue; //next pls
            }
 
            byte[] bytes = file.getBytes();
            Path path = Paths.get(upload.toPath() + file.getOriginalFilename());
            Files.write(path, bytes);
 
        }
 
    }
}
