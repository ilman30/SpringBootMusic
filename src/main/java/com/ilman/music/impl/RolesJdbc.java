/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.impl;

import com.ilman.music.dto.GroupUserDto;
import com.ilman.music.model.GroupUser;
import com.ilman.music.model.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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

    @Autowired
    private NamedParameterJdbcTemplate template;

    //ROles
    
    public List<Roles> getRoles(){
        String SQL = "select id_role as idRole, role_name as namaRole from  roles";
        List<Roles> la = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Roles.class));
        return la;
    }

    public void deleteById(GroupUserDto.Information value) throws DataAccessException {
        String baseQuery = "delete from group_user where id_user = :idUser and id_group = :idGroup";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idUser", value.getIdUser());
        parameterSource.addValue("idGroup", value.getIdGruop());

        template.update(baseQuery, parameterSource);
    }

    public List<GroupUser> getGroupUser() throws EmptyResultDataAccessException {
        String baseQuery= "select g.id as id, a.username as username, r.role_name as roleName " +
                "from group_user g join akun_admin a on (g.id_user = a.id) join roles r on (g.id_group = r.id_role)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        return template.query(baseQuery, params, new RowMapper<GroupUser>() {
            @Override
            public GroupUser mapRow(ResultSet resultSet, int i) throws SQLException {
                GroupUser arsip = new GroupUser();
                arsip.setId(resultSet.getString("id"));
                arsip.setUsername(resultSet.getString("username"));
                arsip.setRoleName(resultSet.getString("roleName"));

                return arsip;
            }
        });
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