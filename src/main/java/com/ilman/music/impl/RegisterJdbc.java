package com.ilman.music.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.ilman.music.model.AkunAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterJdbc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Map<String, Object> param){
        String baseQuery = "insert into group_user(id,id_user, id_group) values (?,?,?)";
        Object parameter[] = {param.get("id"),param.get("idUser"), param.get("idGroup")};
        jdbcTemplate.update(baseQuery, parameter);
    }

    public boolean tambahRoleAdmin(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 2);
                insert(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }

    public boolean tambahRoleUser(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 3);
                insert(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }

    public boolean tambahRoleAdminPremium(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 4);
                insert(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }
    
}
