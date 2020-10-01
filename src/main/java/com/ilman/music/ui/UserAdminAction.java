package com.ilman.music.ui;

import java.util.Objects;
import java.util.UUID;

import com.ilman.music.impl.KoneksiJdbc;
import com.ilman.music.model.StatusLogin;
import com.ilman.music.model.UserAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserAdminAction {
    
    @Autowired
    private KoneksiJdbc koneksiJdbc;

    @PostMapping("/api/login")
    public ResponseEntity<StatusLogin> login(@RequestBody UserAdmin userAdmin) throws Exception {
        StatusLogin statusLogin = new StatusLogin();
        if (userAdmin != null) {
            String username = userAdmin.getUsername();
            if ( Objects.equals(username, "admin")) {
                String password = userAdmin.getPassword();
                if (Objects.equals(password,"123456")) {
                    statusLogin.setIsValid(true);
                    statusLogin.setToken(UUID.randomUUID().toString());
                } else {
                    statusLogin.setIsValid(false);
                    statusLogin.setToken(null);
                }
            }
        } else {
            statusLogin.setIsValid(false);
            statusLogin.setToken(null);
        }
        return ResponseEntity.ok().body(statusLogin);
    }

}
