package com.ilman.music.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.ilman.music.impl.KoneksiJdbc;
import com.ilman.music.model.StatusLogin;
import com.ilman.music.model.UserAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserAdminAction {
    
    @Autowired
    private KoneksiJdbc koneksiJdbc;

    @PostMapping("/api/login")
    public ResponseEntity<StatusLogin> login(@RequestBody UserAdmin userAdmin) throws Exception {
        System.out.println("masuk");
        StatusLogin statusLogin = new StatusLogin();
        if (userAdmin != null) {
            String username = userAdmin.getUsername();
           Optional<UserAdmin>useradmindb = koneksiJdbc.getUserAdminById(username);
            if (useradmindb.isPresent() && Objects.equals(username, useradmindb.get().getUsername())) {
                String password = userAdmin.getPassword();
                if (Objects.equals(password,useradmindb.get().getPassword())) {
                    System.out.println(useradmindb.get().getUsername());
                    statusLogin.setIsValid(true);
                    String token =UUID.randomUUID().toString();
                    Map<String, Object> paramlogin = new HashMap<>();
                    paramlogin.put("username", username);
                    paramlogin.put("token", token);
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
