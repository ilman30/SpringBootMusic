package com.ilman.music.service;

import java.util.List;

import com.ilman.music.dto.GroupUserDto;
import com.ilman.music.impl.RolesJdbc;
import com.ilman.music.model.GroupUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GroupUserService {

    @Autowired
    private RolesJdbc rolesJdbc;
    
    public List<GroupUser> findAll() throws EmptyResultDataAccessException{
        return rolesJdbc.getGroupUser();
    }

    public void deleteById(GroupUserDto.Information value) throws DataAccessException {
        rolesJdbc.deleteById(value);
    }

}
