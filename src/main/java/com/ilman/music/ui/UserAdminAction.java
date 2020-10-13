package com.ilman.music.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.ilman.music.impl.LoginJdbc;
import com.ilman.music.impl.RolesJdbc;
import com.ilman.music.model.AkunAdmin;
import com.ilman.music.model.StatusLogin;
import com.ilman.music.model.UserAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ch.qos.logback.core.status.Status;

@Controller
public class UserAdminAction {
    
    @Autowired
    private LoginJdbc loginJdbc;

    @PostMapping("/api/login")
    public ResponseEntity<StatusLogin> login(@RequestBody UserAdmin userAdmin) throws Exception {
        StatusLogin statusLogin = new StatusLogin();
        if (userAdmin != null) {
            String username = userAdmin.getUsername();
            Optional<AkunAdmin> useradmindb = loginJdbc.getUserAdminById(username);
            if (useradmindb.isPresent() && Objects.equals(username, useradmindb.get().getUsername())) {
                String password = userAdmin.getPassword();
                if (Objects.equals(password,useradmindb.get().getKeyword())) {
                    String token = UUID.randomUUID().toString();
                    statusLogin.setIsValid(true);
                    statusLogin.setToken(token);
                    Map<String, Object> paramlogin = new HashMap<>();
                    paramlogin.put("username", username);
                    paramlogin.put("token", token);
                    loginJdbc.insertUserLogin(paramlogin);
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

    @PostMapping("/api/ceklogin")
    public ResponseEntity<StatusLogin>cekUserLoginValid(@RequestBody UserAdmin userAdmin){
        System.out.println(userAdmin.getUsername());
        return ResponseEntity.ok().body(loginJdbc.cekLoginValid(userAdmin));
    }


}
