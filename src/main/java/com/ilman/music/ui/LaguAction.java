package com.ilman.music.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ilman.music.impl.KoneksiJdbc;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.model.Lagu;
import com.ilman.music.service.LaguService;

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
public class LaguAction {

    @Autowired
    private LaguService laguService;

    @Autowired
    private KoneksiJdbc koneksiJdbc;

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
    
    @GetMapping(path = "/api/listlagubygenrejson/{idx}")
    public ResponseEntity<List<Lagu>> findByGenre(@PathVariable("idx") Integer id){
        return ResponseEntity.ok().body(koneksiJdbc.getLaguByGenre(id));
    }
    
    @GetMapping(path= "/api/listlagujson")
    public ResponseEntity<List<Lagu>> listLaguCariJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getLagu());
    }

    @DeleteMapping("/api/deleteLagu/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            laguService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/uploadlagu")
    public ResponseEntity<Map<String,Object>> uploadFileArtis(@RequestParam("file") MultipartFile file){
        String messege = null;
        Map<String,Object> pesan = new HashMap<>(); 
        try{
            String namaFile = laguService.save(file);
            pesan.put("pesan", "Upload File Success: " + namaFile);
            pesan.put("namaFile", namaFile);
            return ResponseEntity.status(HttpStatus.OK).body(pesan);
        } catch(Exception e){
            pesan.put("pesan","Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }

    @GetMapping(value = "/api/music/{id}")
    public ResponseEntity<InputStreamResource>getImage2(@PathVariable("id") String id){
        try{
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                    new InputStreamResource( laguService.load(id).getInputStream() ));
        }catch(IOException ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
    
}
