package com.ilman.music.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

import com.ilman.music.model.AkunAdmin;
import com.ilman.music.model.StatusLogin;
import com.ilman.music.model.UserAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginJdbc {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AkunAdminJdbc akunAdminJdbc;

    //Admin

    public Optional<AkunAdmin> getUserAdminById(String userAdmin) {
        String SQL = "select username, keyword from akun_admin where username = ? ";
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL, (rs, rownum) -> {
                AkunAdmin kab = new AkunAdmin();
                kab.setUsername(rs.getString("username"));
                kab.setKeyword(rs.getString("keyword"));
                return kab;
            }, userAdmin));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public StatusLogin cekLoginValid(UserAdmin user){
        String baseQuery = "select a.user_name from user_admin a inner " +
                           "join akun_admin b on b.username = a.user_name where tokenkey = ?";
        StatusLogin slogin = new StatusLogin();

        try{
            boolean isValid = false;
            Optional<UserAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                UserAdmin use = new UserAdmin();
                use.setUsername(rs.getString("user_name"));
                return use;
            },user.getToken()));
                if (hasil.isPresent()){
                    if(Objects.equals(user.getUsername(), hasil.get().getUsername())){
                        List<String> rolesName = akunAdminJdbc.getRolesByUserName(hasil.get().getUsername());
                        slogin.setIsValid(true);
                        slogin.setRoles(rolesName);
                        slogin.setToken(user.getToken());
                        System.out.println(hasil.get().getUsername());
                    }else{
                        slogin.setIsValid(false);
                    }
                }
            }catch (Exception e){
                slogin.setIsValid(false);
            e.printStackTrace();
        }
        return slogin;
    }

    public List<String> getRolesById(Integer id){
        String query = "select role_name from roles where role_id = ?";

        Object param[] = {id};

        List<String> prop = jdbcTemplate.query(query, (rs, rownum) ->{
            return rs.getString("role_name");
        }, param);

        return prop;
    }

    public void insertUserLogin(Map<String, Object>param){
        String SQL = "insert into user_admin (user_name,tokenkey) values (?,?)";
        Object parameter[] = {param.get("username"), param.get("token")};
        jdbcTemplate.update(SQL, parameter);
    }
    
}
