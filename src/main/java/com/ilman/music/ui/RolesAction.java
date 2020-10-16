package com.ilman.music.ui;

import java.util.List;

import com.ilman.music.impl.RolesJdbc;
import com.ilman.music.model.GroupUser;
import com.ilman.music.model.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RolesAction {

    @Autowired
    private RolesJdbc rolesJdbc;

    @GetMapping(path= "/api/listrolesjson")
    public ResponseEntity<List<Roles>> listRoleJson(){
        return ResponseEntity.ok().body(rolesJdbc.getRoles());
    }

    @PostMapping(path = "/checkingsuperadmin")
    public ResponseEntity<GroupUser> checkingSuperAdmin(@RequestBody String idUser){
        return ResponseEntity.ok().body(rolesJdbc.checkingSuperAdmin(idUser));
    }
    
}
