package com.ilman.music.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ilman.music.dto.AkunAdminDto;
import com.ilman.music.impl.AkunAdminJdbc;
import com.ilman.music.model.AkunAdmin;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AkunAdminService {

    @Autowired
    private AkunAdminJdbc akunAdminJdbc;

    public void saveAdmin(AkunAdminDto.New akunAdminDto) throws SQLException {
        akunAdminDto.setId(UUID.randomUUID().toString());
        Map<String, Object> paramRegAcc = new HashMap<>();
        paramRegAcc.put("id", UUID.randomUUID().toString()); 
        paramRegAcc.put("idUser", akunAdminDto.getId());
        paramRegAcc.put("idGroup", 2);      
        akunAdminJdbc.insertGroupUser(paramRegAcc);
        akunAdminJdbc.registerAkun(akunAdminDto);
    }

    public void saveUser(AkunAdminDto.New akunAdminDto) throws SQLException {
        akunAdminDto.setId(UUID.randomUUID().toString());
        Map<String, Object> paramRegAcc = new HashMap<>();
        paramRegAcc.put("id", UUID.randomUUID().toString());  
        paramRegAcc.put("idUser", akunAdminDto.getId());
        paramRegAcc.put("idGroup", 3);      
        akunAdminJdbc.insertGroupUser(paramRegAcc);
        akunAdminJdbc.registerAkun(akunAdminDto);        
    }
    
}
