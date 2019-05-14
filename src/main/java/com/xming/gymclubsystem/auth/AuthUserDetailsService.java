package com.xming.gymclubsystem.auth;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.repository.primary.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Xiaoming.
 * Created on 2019/03/11 23:25.
 */
@Service("authUserDetailsService")
public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名错误");
        } else {
            return new MyUserDetails(user);
        }
    }
}
