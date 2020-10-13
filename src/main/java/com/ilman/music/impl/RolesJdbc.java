/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.impl;

import com.ilman.music.dto.AkunAdminDto;
import com.ilman.music.model.AkunAdmin;
import com.ilman.music.model.Albums;
import com.ilman.music.model.Artis;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.Genre;

import com.ilman.music.model.Lagu;
import com.ilman.music.model.Roles;
import com.ilman.music.model.StatusLogin;
import com.ilman.music.model.UserAdmin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ilman
 */
@Repository
public class RolesJdbc {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //ROles
    
    public List<Roles> getRoles(){
        String SQL = "select id_role as idRole, role_name as namaRole from  roles";
        List<Roles> la = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Roles.class));
        return la;
    }


}