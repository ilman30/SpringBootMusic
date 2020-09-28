/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.service;

import com.ilman.music.impl.KoneksiJdbc;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.model.LablesRekaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilman
 */
@Service
public class LablesRekamanService {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
//    @Transactional(readOnly = false)
    public DataTablesResponse<LablesRekaman> listLablesRekamanDataTable (DataTablesRequest req) {
        DataTablesResponse dataTableRespon = new DataTablesResponse();
        dataTableRespon.setData(koneksiJdbc.getListLablesRekaman(req));
        Integer total = koneksiJdbc.getBanyakLablesRekaman(req);
        dataTableRespon.setRecordsFiltered(total);
        dataTableRespon.setRecordsTotal(total);
        dataTableRespon.setDraw(req.getDraw());
        return dataTableRespon;
    }
}
