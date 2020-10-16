package com.ilman.music.ui;

import java.sql.SQLException;

import com.ilman.music.dto.AkunAdminDto;
import com.ilman.music.impl.AkunAdminJdbc;
import com.ilman.music.service.AkunAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterAction {

    @Autowired
    private AkunAdminService akunAdminService;

    @Autowired
    private AkunAdminJdbc akunAdminJdbc;

    @PostMapping(path = "/api/saveAdmin")
    public ResponseEntity<?> saveAdmin(@RequestBody AkunAdminDto.New newAkun){
        try{
            akunAdminService.saveAdmin(newAkun);
            return new ResponseEntity<>(newAkun, HttpStatus.CREATED);
        } catch (SQLException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/api/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody AkunAdminDto.New newAkun){
        try{
            akunAdminService.saveUser(newAkun);
            return new ResponseEntity<>(newAkun, HttpStatus.CREATED);
        } catch (SQLException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
}
