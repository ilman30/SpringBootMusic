package com.ilman.music.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ilman.music.impl.KoneksiJdbc;
import com.ilman.music.model.Artis;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.service.ArtisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ArtisAction {

    @Autowired
    private ArtisService artisService;

    @Autowired
    private KoneksiJdbc koneksiJdbc;

    @PostMapping("/api/saveartisjson")
    public ResponseEntity <Map<String,Object>> saveartisjson(@RequestBody Artis artis){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateArtis(artis);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping(path="/api/listartisdatajson")
    public ResponseEntity<DataTablesResponse<Artis>> listArtisDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(artisService.listArtisDataTable(dataRequest));
    }
    
    @GetMapping(path= "/api/listartisjson")
    public ResponseEntity<List<Artis>> listArtisCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getArtis());
    }
    
    @PostMapping("/api/uploadartis")
    public ResponseEntity<Map<String,Object>> uploadFileArtis(@RequestParam("file") MultipartFile file){
        String messege = null;
        Map<String,Object> pesan = new HashMap<>(); 
        try{
            String namaFile = artisService.save(file);
            pesan.put("pesan", "Upload File Success: " + namaFile);
            pesan.put("namaFile", namaFile);
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        } catch(Exception e){
            pesan.put("pesan","Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }

    @GetMapping(value = "/api/image/{id}")
    public ResponseEntity<InputStreamResource>getImage(@PathVariable("id") String id){
        try{
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                    new InputStreamResource( artisService.load(id).getInputStream() ));
        }catch(IOException ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @DeleteMapping("/api/deleteArtis/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            artisService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
}
