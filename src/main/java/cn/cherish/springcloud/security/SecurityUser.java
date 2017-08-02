package cn.cherish.springcloud.security;

import cn.cherish.springcloud.service.dto.UserDTO;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = -3231211806158557844L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String telephone;

    private Date createdTime;

    public SecurityUser(UserDTO user) {
        if (user == null) {
            throw new NullPointerException();
        }
        this.setId(user.getId());
        this.setNickname(user.getNickname());
        this.setTelephone(user.getTelephone());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setCreatedTime(user.getCreatedTime());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //**************
    // 静态工具方法
    public static SecurityUser ME(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (SecurityUser) authentication.getPrincipal();
    }

    public static String nickname(){
        SecurityUser securityUser = ME();
        if (securityUser == null) {
            return null;
        }
        return securityUser.getNickname();
    }

    public static Long id(){
        SecurityUser securityUser = ME();
        if (securityUser == null) {
            return null;
        }
        return securityUser.getId();
    }

}
