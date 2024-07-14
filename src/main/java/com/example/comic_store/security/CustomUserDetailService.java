package com.example.comic_store.security;

import com.example.comic_store.entity.Role;
import com.example.comic_store.entity.UserEntity;
import com.example.comic_store.repository.RoleRepository;
import com.example.comic_store.repository.UserRepository;
import com.example.comic_store.repository.UserRoleRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
        List<Long> roleIdList = userRoleRepository.findAllUserRoleIdById(user.getId());
        List<Role> roleList = roleRepository.findAllById(roleIdList);
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(roleList));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
}
