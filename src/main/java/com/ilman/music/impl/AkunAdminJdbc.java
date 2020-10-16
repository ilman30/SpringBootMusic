package com.ilman.music.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import com.ilman.music.dto.AkunAdminDto;
import com.ilman.music.model.AkunAdmin;
import com.ilman.music.model.DataTablesRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AkunAdminJdbc {


    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //akunadmin

    public void registerAkun(AkunAdminDto.New akunAdminDto) throws SQLException{
        String sql = "insert into akun_admin (id,username,keyword) values (?,?,?)";
        Object param[] = {akunAdminDto.getId(),akunAdminDto.getUsername(),akunAdminDto.getKeyword()};
        jdbcTemplate.update(sql, param);
    }
    
    public void insertGroupUser(Map<String, Object> param){
        String sql= "insert into group_user (id,id_user,id_group) values (?,?,?)";
        Object parameter[] = {param.get("id"),param.get("idUser"), param.get("idGroup")};
        jdbcTemplate.update(sql, parameter);
    }

    public void deleteGroupUser(Integer id){
        String SQL = "delete from group_user where id=?";
        Object parameters[] = {id};
        
        jdbcTemplate.update(SQL, parameters);
    }

    public List<String> getRolesByUserName(String userName){
        String query = "select r.role_name from group_user g join roles r on (g.id_group = r.id_role) " +
                " join akun_admin a on (g.id_user = a.id) where a.username = ?";

        Object param[] = {userName};

        List<String> prop = jdbcTemplate.query(query, (rs, rownum) ->{
            return rs.getString("role_name");
        }, param);

        return prop;
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
                insertGroupUser(param);
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
                insertGroupUser(param);
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

    public List<AkunAdmin> getAkun() throws EmptyResultDataAccessException {
        String baseQuery = "select id as id, username as username, keyword as keyword from akun_admin";
        List<AkunAdmin> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(AkunAdmin.class));
        return prop;
    }
    public List<AkunAdmin> getAkunById(String id) throws EmptyResultDataAccessException {
        String baseQuery = "select id as id, username as username from akun_admin where id = ?";
        Object[] param = {id};

        return jdbcTemplate.query(baseQuery, param,  (rs, rowNUm) -> {
            AkunAdmin akunAdmin = new AkunAdmin();
            akunAdmin.setUsername(rs.getString("username"));
            akunAdmin.setId(rs.getString("id"));
            return akunAdmin;
        });
    }
    
}
