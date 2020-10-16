package com.ilman.music.ui;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ilman.music.dto.AkunAdminDto;
import com.ilman.music.impl.AkunAdminJdbc;
import com.ilman.music.model.AkunAdmin;
import com.ilman.music.service.AkunAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AkunAdminAction {

    @Autowired
    private AkunAdminService akunAdminService;

    @Autowired
    private AkunAdminJdbc akunAdminJdbc;
    

    @PostMapping("/api/registerakun")
    public ResponseEntity <?> registerakunjson(@RequestBody AkunAdminDto.New akunAdminDto ) throws SQLException{
        Map<String, Object> status = new HashMap<>();
        akunAdminService.saveUser(akunAdminDto);
        status.put("pesan", "Simpan Berhasil");
        return new ResponseEntity<>(akunAdminDto,HttpStatus.CREATED);
    }

    @PostMapping("/api/registeradmin")
    public ResponseEntity <?> getisteradminjson(@RequestBody AkunAdminDto.New akunAdminDto ) throws SQLException{
        Map<String, Object> status = new HashMap<>();
        akunAdminService.saveAdmin(akunAdminDto);
        status.put("pesan", "Simpan Berhasil");
        return new ResponseEntity<>(akunAdminDto,HttpStatus.CREATED);
    }

    @GetMapping(path="/api/listakunjson")
    public ResponseEntity<List<AkunAdmin>> listAkunCari( ){
        return ResponseEntity.ok().body(akunAdminJdbc.getAkun());
    }

    @GetMapping(path="/api/listakunjson/{id}")
    public ResponseEntity<List<AkunAdmin>> listAkunByIdJson(@PathVariable("id") String id ){
        return ResponseEntity.ok().body(akunAdminJdbc.getAkunById(id));
    }

    
}
