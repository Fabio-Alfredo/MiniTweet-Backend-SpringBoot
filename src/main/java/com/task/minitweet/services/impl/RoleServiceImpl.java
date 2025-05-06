package com.task.minitweet.services.impl;

import com.task.minitweet.domains.dtos.role.CreateRoleDto;
import com.task.minitweet.domains.models.Role;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.RoleRepository;
import com.task.minitweet.services.contract.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Role findRoleById(String id) {
        try{
            Role role = roleRepository.findById(id).orElse(null);

            if(role == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "Role not found");
            }
            return role;
        }catch (HttpError e ){
            throw e;
        }
    }

    @Override
    public List<Role> findAllRoles() {
        try{
            List<Role> roles = roleRepository.findAll();
            if(roles.isEmpty() ){
                throw new HttpError(HttpStatus.NOT_FOUND, "No roles found");
            }
            return roles;
        }catch (HttpError e ){
            throw e;
        }
    }

    @Override
    public void createRole(CreateRoleDto roleDto) {
        try{
            Role role = roleRepository.findById(roleDto.getId()).orElse(null);
            if(role != null){
                throw new HttpError(HttpStatus.CONFLICT, "Role already exists");
            }
            role = modelMapper.map(roleDto, Role.class);

            roleRepository.save(role);
        }catch (HttpError e ){
            throw e;
        }
    }
}
