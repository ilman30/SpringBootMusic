/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.ui;

import com.ilman.music.impl.LablesRekamanJdbc;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.model.LablesRekaman;
import com.ilman.music.service.LablesRekamanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 *
 * @author ilman
 */
@Controller
public class LablesRekamanAction {
    
    @Autowired
    private LablesRekamanJdbc lablesRekamanJdbc;
    
    @Autowired 
    private LablesRekamanService lablesRekamanService;
    
    //Lables Rekaman
    
    @PostMapping(path="/api/listlablesdatajson")
    public ResponseEntity<DataTablesResponse<LablesRekaman>> listLablesRekamanDataTable(@RequestBody DataTablesRequest dataRequest){
        return ResponseEntity.ok().body(lablesRekamanService.listLablesRekamanDataTable(dataRequest));
    }
    
    @PostMapping("/api/savelablesjson")
    public ResponseEntity <Map<String,Object>> savelablesjson(@RequestBody LablesRekaman lablesRekaman){
        Map<String, Object> status = new HashMap<>();
        lablesRekamanJdbc.insertOrUpdateLables(lablesRekaman);
        status.put("pesan", "Simpan Berhasil");
        return ResponseEntity.ok().body(status);
    }
    
    @GetMapping(path= "/api/listlablesjson")
    public ResponseEntity<List<LablesRekaman>> listLablesCariJson(){
        return ResponseEntity.ok().body(lablesRekamanJdbc.getLablesRekaman());
    }
    
}
