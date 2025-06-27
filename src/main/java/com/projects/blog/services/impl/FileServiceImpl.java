package com.projects.blog.services.impl;

import com.projects.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String originalName = file.getOriginalFilename();
		String extension = "";

		if (originalName != null && originalName.contains(".")) {
			extension = originalName.substring(originalName.lastIndexOf("."));
		}

		String randomFileName = UUID.randomUUID().toString() + extension;
		String filePath = path + File.separator + randomFileName;
		File dir = new File(path);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				throw new IOException(" Failed to create upload directory: " + path);
			}
		}
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(file.getBytes());
		fos.close();
		return randomFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String filePath = path + File.separator + fileName;
		return new FileInputStream(filePath);
	}

}
