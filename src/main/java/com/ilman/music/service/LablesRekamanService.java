/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.service;

import com.ilman.music.impl.LablesRekamanJdbc;
import com.ilman.music.impl.RolesJdbc;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.model.LablesRekaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilman
 */
@Service
public class LablesRekamanService {
    @Autowired
    private LablesRekamanJdbc lablesRekamanJdbc;
    
//    @Transactional(readOnly = false)
    public DataTablesResponse<LablesRekaman> listLablesRekamanDataTable (DataTablesRequest req) {
        DataTablesResponse dataTableRespon = new DataTablesResponse();
        dataTableRespon.setData(lablesRekamanJdbc.getListLablesRekaman(req));
        Integer total = lablesRekamanJdbc.getBanyakLablesRekaman(req);
        dataTableRespon.setRecordsFiltered(total);
        dataTableRespon.setRecordsTotal(total);
        dataTableRespon.setDraw(req.getDraw());
        return dataTableRespon;
    }

    public void deleteById(Integer id) throws DataAccessException{
        lablesRekamanJdbc.deleteLables(id);
    }
}
