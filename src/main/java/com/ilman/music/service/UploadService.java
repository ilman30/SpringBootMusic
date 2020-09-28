/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ilman
 */
@Service
public class UploadService {
    
//    @Value("${upload.images}")
//    private String pathFile;
//    
//    public String save (MultipartFile file){
//        try{
//            Path root = Paths.get(pathFile);
//            String[] fileFrags = file.getOriginalFilename().split("\\.");
//            String extension = fileFrags[fileFrags.length - 1];
//            String uuid = UUID.randomUUID().toString() + "." +extension;
//            Files.copy(file.getInputStream(), root.resolve(uuid));
//            return uuid;
//        }catch (IOException e){
//            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
//        }
//        
//    }
    
}
