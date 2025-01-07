package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);

    Optional<User> findById(Long id);

    boolean saveUser(User user);

    List<User> showUsers();

    void updateUser(User user);

    User readUser(String username);

    boolean deleteUser(Long userId);

    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);

    UserDetails loadUserByUsername(String username);
}
