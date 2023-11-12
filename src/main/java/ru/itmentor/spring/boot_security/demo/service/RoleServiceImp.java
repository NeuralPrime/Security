package ru.itmentor.spring.boot_security.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.DAO.RoleDaoImp;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleDaoImp roleDaoImp;
    @Autowired
    public RoleServiceImp(RoleDaoImp daoRoleImp) {
        this.roleDaoImp = daoRoleImp;
    }

    @Override
    public void setUserRoles(Long userId, Set<Role> newRoles) {
        roleDaoImp.setUserRoles(userId, newRoles);

    }
    public void removeRoles(Long userId, Set<Role> rolesToRemove){
        roleDaoImp.removeRoles(userId, rolesToRemove);
    }
    @Override
    public UserDetails loadUserByUsername(String email) {
        return roleDaoImp.loadUserByUsername(email);
    }

    @Override
    public void deleteById(Long id) {
        roleDaoImp.deleteById(id);
    }

    public User getInfo(UserDetails currentUserDetails){
        return roleDaoImp.getInfo(currentUserDetails);
    }
}