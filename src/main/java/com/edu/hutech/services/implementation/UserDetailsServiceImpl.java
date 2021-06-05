package com.edu.hutech.services.implementation;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import com.edu.hutech.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
//        // User user = userRepository.findByAccountClassAdmin(username);
//        ClassAdmin admin = classAdminRepository.findByAccount(username);
//        if(admin == null) { // if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        // Set<Role> roles = user.getRoles();
//        Set<Role> roles = admin.getRoles();
//        for (Role role : roles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                admin.getAccount(), admin.getPassword(), grantedAuthorities); // has change
        try{
            String sql = "Select * from demo.user where account ='"+ account +"'";
            Query query = entityManager.createNativeQuery(sql, User.class);
            return (User) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
