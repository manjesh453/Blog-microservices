package com.postservice.service.impl;

import com.postservice.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name=file.getOriginalFilename();
        //random name generated file
        String randomId= UUID.randomUUID().toString();
        String filename=randomId.concat(name.substring(name.lastIndexOf(".")));
        //full path
        String filepath=path+ File.separator+filename;
        //create folder if not created
        File f=new File(path);
        if(!f.exists()) {
            f.mkdir();
        }
        //file copy
        Files.copy(file.getInputStream(), Paths.get(filepath));

        return name;
    }

//    @Override
//    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
//        String fullPath=path+File.separator+fileName;
//        File file = new File(fullPath);
//        if (!file.exists()) {
//            throw new FileNotFoundException("File not found: " + fullPath);
//        }
//        InputStream is = new FileInputStream(fullPath);
//        return is;
//    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        // Normalize the fileName by replacing any backslashes with forward slashes
        fileName = fileName.replace('\\', '/');

        // Create a proper path by using File constructor
        File file = new File(path, fileName);
        String fullPath = file.getAbsolutePath();

        // Check if file exists before attempting to open it
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fullPath);
        }

        InputStream is = new FileInputStream(file);
        return is;
    }
}




