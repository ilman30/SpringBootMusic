/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.service;

import com.ilman.music.impl.ArtisJdbc;
import com.ilman.music.impl.RolesJdbc;
import com.ilman.music.model.Artis;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ilman
 */
@Service
public class ArtisService {
    @Autowired
    private ArtisJdbc artisJdbc;
    
    public DataTablesResponse<Artis> listArtisDataTable (DataTablesRequest req) {
        DataTablesResponse dataTableRespon = new DataTablesResponse();
        dataTableRespon.setData(artisJdbc.getListArtis(req));
        Integer total = artisJdbc.getBanyakArtis(req);
        dataTableRespon.setRecordsFiltered(total);
        dataTableRespon.setRecordsTotal(total);
        dataTableRespon.setDraw(req.getDraw());
        return dataTableRespon;
    }
    
    @Value("${upload.artis}")
    private String pathFile;
    
    public String save (MultipartFile file){
        try{
            Path root = Paths.get(pathFile);
            String[] fileFrags = file.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length - 1];
            String uuid = UUID.randomUUID().toString() + "." +extension;
            Files.copy(file.getInputStream(), root.resolve(uuid));
            return uuid;
        }catch (IOException e){
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
        
    }

    public Resource load(String filename) {
        try {
            Path root = Paths.get(pathFile);
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteById(Integer id) throws DataAccessException{
        artisJdbc.deleteArtis(id);
    }
    
    
}
