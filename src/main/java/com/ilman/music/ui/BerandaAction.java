/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.ui;

import com.ilman.music.impl.KoneksiJdbc;
import com.ilman.music.model.Albums;
import com.ilman.music.model.Artis;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.model.Genre;
import com.ilman.music.model.LablesRekaman;
import com.ilman.music.model.Lagu;
import com.ilman.music.service.AlbumsService;
import com.ilman.music.service.ArtisService;
import com.ilman.music.service.GenreService;
import com.ilman.music.service.LablesRekamanService;
import com.ilman.music.service.LaguService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ilman
 */
@Controller
public class BerandaAction {
    
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    @Autowired 
    private LablesRekamanService lablesRekamanService;
    
    @Autowired
    private ArtisService artisService;
    
    @Autowired
    private GenreService genreService;
    
    @Autowired
    private AlbumsService albumsService;
    
    @Autowired
    private LaguService laguService;
    
    //Lables Rekaman
    
    @PostMapping(path="/api/listlablesdatajson")
    public ResponseEntity<DataTablesResponse<LablesRekaman>> listLablesRekamanDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(lablesRekamanService.listLablesRekamanDataTable(dataRequest));
    }
    
    @PostMapping("/api/savelablesjson")
    public ResponseEntity <Map<String,Object>> savelablesjson(@RequestBody LablesRekaman lablesRekaman){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateLables(lablesRekaman);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @GetMapping(path= "/api/listlablesjson")
    public ResponseEntity<List<LablesRekaman>> listLablesCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getLablesRekaman());
    }
    
    //Artis
    
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
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        } catch(Exception e){
            pesan.put("pesan","Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }
    
    //Genre
    
    @PostMapping("/api/savegenrejson")
    public ResponseEntity <Map<String,Object>> savegenrejson(@RequestBody Genre genre){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateGenre(genre);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping(path="/api/listgenredatajson")
    public ResponseEntity<DataTablesResponse<Genre>> listGenreDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(genreService.listGenreDataTable(dataRequest));
    }
    
    @GetMapping(path= "/api/listgenrejson")
    public ResponseEntity<List<Genre>> listGenreCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getGenre());
    }
    
    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            genreService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    //Albums
    
    @GetMapping(path= "/api/listalbumsjson")
    public ResponseEntity<List<Albums>> listAlbumsCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getAlbums());
    }
    
    @PostMapping("/api/savealbumsjson")
    public ResponseEntity <Map<String,Object>> savealbumsjson(@RequestBody Albums albums){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateAlbums(albums);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping(path="/api/listalbumsdatajson")
    public ResponseEntity<DataTablesResponse<Albums>> listAlbumsDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(albumsService.listAlbumsDataTable(dataRequest));
    }
    
    @PostMapping("/api/uploadalbums")
    public ResponseEntity<Map<String,Object>> uploadFileAlbums(@RequestParam("file") MultipartFile file){
        String messege = null;
        Map<String,Object> pesan = new HashMap<>(); 
        try{
            String namaFile = albumsService.save(file);
            pesan.put("pesan", "Upload File Success: " + namaFile);
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        } catch(Exception e){
            pesan.put("pesan","Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }
    
    @GetMapping(path = "/api/listalbumsjson/{id}")
    public ResponseEntity<List<Albums>> findByArtis(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(koneksiJdbc.getAlbumsByArtis(id));
    }
   
    //Lagu
    
    @PostMapping("/api/savelagujson")
    public ResponseEntity <Map<String,Object>> savelagujson(@RequestBody Lagu lagu){
        Map<String, Object> status = new HashMap<>();
        koneksiJdbc.insertOrUpdateLagu(lagu);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping(path="/api/listlagudatajson")
    public ResponseEntity<DataTablesResponse<Lagu>> listLaguDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(laguService.listLaguDataTable(dataRequest));
    }
    
    @GetMapping(path = "/api/listlagujson/{id}")
    public ResponseEntity<List<Lagu>> findByAlbums(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(koneksiJdbc.getLaguByAlbums(id));
    }
    
    @GetMapping(path = "/api/listlagubygenrejson/{ids}")
    public ResponseEntity<List<Lagu>> findByGenre(@PathVariable("ids") Integer id){
        return ResponseEntity.ok().body(koneksiJdbc.getLaguByGenre(id));
    }
    
    @GetMapping(path= "/api/listlagujson")
    public ResponseEntity<List<Lagu>> listLaguCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getLagu());
    }
    
       
    
}
