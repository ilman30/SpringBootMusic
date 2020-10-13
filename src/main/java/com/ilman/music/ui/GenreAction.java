package com.ilman.music.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ilman.music.impl.GenreJdbc;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.model.Genre;
import com.ilman.music.service.GenreService;

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

@Controller
public class GenreAction {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreJdbc genreJdbc;

    @PostMapping("/api/savegenrejson")
    public ResponseEntity <Map<String,Object>> savegenrejson(@RequestBody Genre genre){
        Map<String, Object> status = new HashMap<>();
        genreJdbc.insertOrUpdateGenre(genre);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @PostMapping(path="/api/listgenredatajson")
    public ResponseEntity<DataTablesResponse<Genre>> listGenreDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(genreService.listGenreDataTable(dataRequest));
    }
    
    @GetMapping(path= "/api/listgenrejson")
    public ResponseEntity<List<Genre>> listGenreCariJson(){
        return ResponseEntity.ok().body(genreJdbc.getGenre());
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
    
}
