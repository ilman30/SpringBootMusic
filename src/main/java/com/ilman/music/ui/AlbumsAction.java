package com.ilman.music.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ilman.music.impl.AlbumsJdbc;
import com.ilman.music.impl.RolesJdbc;
import com.ilman.music.model.Albums;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.service.AlbumsService;

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
public class AlbumsAction {

    @Autowired
    private AlbumsService albumsService;

    @Autowired
    private AlbumsJdbc albumsJdbc;

    @GetMapping(path= "/api/listalbumsjson")
    public ResponseEntity<List<Albums>> listAlbumsCariJson(){
        return ResponseEntity.ok().body(albumsJdbc.getAlbums());
    }
    
    @PostMapping("/api/savealbumsjson")
    public ResponseEntity <Map<String,Object>> savealbumsjson(@RequestBody Albums albums){
        Map<String, Object> status = new HashMap<>();
        albumsJdbc.insertOrUpdateAlbums(albums);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping(path="/api/listalbumsdatajson")
    public ResponseEntity<DataTablesResponse<Albums>> listAlbumsDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(albumsService.listAlbumsDataTable(dataRequest));
    }
        
    @GetMapping(path = "/api/listalbumsjson/{id}")
    public ResponseEntity<List<Albums>> findByArtis(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(albumsJdbc.getAlbumsByArtis(id));
    }

    @PostMapping("/api/uploadalbums")
    public ResponseEntity<Map<String,Object>> uploadFileAlbums(@RequestParam("file") MultipartFile file) {
        String message = null;
        Map<String,Object> pesan = new HashMap<>();
        try {
            String namaFile = albumsService.save(file);
            
            pesan.put("pesan", "Uploaded the file succesfully: " + namaFile);
            pesan.put("namaFile",  namaFile);
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        }catch (Exception e) {
            pesan.put("pesan", "Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }

    @GetMapping(value = "/api/image2/{id}")
    public ResponseEntity<InputStreamResource>getImage2(@PathVariable("id") String id){
        try{
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                    new InputStreamResource( albumsService.load(id).getInputStream() ));
        }catch(IOException ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @DeleteMapping("/api/deleteAlbums/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            albumsService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
}
