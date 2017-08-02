package cn.cherish.springcloud.security;

import cn.cherish.springcloud.service.FeignUserService;
import cn.cherish.springcloud.service.dto.UserDTO;
import me.cherish.web.MResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final FeignUserService feignUserService;

    @Autowired
    public CustomUserDetailsService(FeignUserService feignUserService) {
        this.feignUserService = feignUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MResponse<UserDTO> response = feignUserService.findByUsername(username);
        UserDTO user = response.getData();
        if (user == null) {
            throw new UsernameNotFoundException("username " + username + " not found");
        }
        return new SecurityUser(user);
    }
}
