/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.impl;

import com.ilman.music.model.GroupUser;
import com.ilman.music.model.Roles;

import java.util.List;
import java.util.Optional;

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

    public GroupUser checkingSuperAdmin(String idUser){
        String baseQuery="select id , id_group as idGroup, id_user as idUser from group_user " +
                "where id_user = ? and id_group = 1";
        GroupUser groupUsers = new GroupUser();
        boolean isCheck = false;
        try{
            Optional<GroupUser> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                GroupUser groupUser = new GroupUser();
                groupUser.setId(rs.getString("id"));
                groupUser.setIdGroup(rs.getInt("idGroup"));
                groupUser.setIdUser(rs.getString("idUser"));
                return groupUser;
            },idUser));
            if (hasil != null){
                isCheck = true;
                groupUsers.setId(hasil.get().getId());
                groupUsers.setIdUser(hasil.get().getIdUser());
                groupUsers.setIsCheck(isCheck);
                return groupUsers;
            } else{
                isCheck = false;
                groupUsers.setIsCheck(isCheck);
            }
        } catch (Exception e){
            e.printStackTrace();
            isCheck = false;
            groupUsers.setIsCheck(isCheck);
        }
        return groupUsers;
    }



}