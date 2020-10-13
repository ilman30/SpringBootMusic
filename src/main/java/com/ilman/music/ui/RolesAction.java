package com.ilman.music.ui;

import java.util.List;

import com.ilman.music.impl.RolesJdbc;
import com.ilman.music.model.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RolesAction {

    @Autowired
    private RolesJdbc koneksiJdbc;

    @GetMapping(path= "/api/listrolesjson")
    public ResponseEntity<List<Roles>> listRoleJson(){
        return ResponseEntity.ok().body(koneksiJdbc.getRoles());
    }
    
}
