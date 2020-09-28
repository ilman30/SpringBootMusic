/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.service;

import com.ilman.music.impl.KoneksiJdbc;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.DataTablesResponse;
import com.ilman.music.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ilman
 */
@Service
public class GenreService {
    @Autowired
    private KoneksiJdbc koneksiJdbc;
    
    public DataTablesResponse<Genre> listGenreDataTable (DataTablesRequest req) {
        DataTablesResponse dataTableRespon = new DataTablesResponse();
        dataTableRespon.setData(koneksiJdbc.getListGenre(req));
        Integer total = koneksiJdbc.getBanyakGenre(req);
        dataTableRespon.setRecordsFiltered(total);
        dataTableRespon.setRecordsTotal(total);
        dataTableRespon.setDraw(req.getDraw());
        return dataTableRespon;
    }
    
    public void deleteById(Integer id) throws DataAccessException{
        koneksiJdbc.deleteGenre(id);
    }
    
}
