package ru.buzynnikov.spring_security_oauth2.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_security_oauth2.models.CustomOAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        // Здесь логика обработки пользователя и назначения ролей
        return new CustomOAuth2User(user, determineRoles(user));
    }

    private Collection<GrantedAuthority> determineRoles(OAuth2User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Пример: если login пользователя соответствует админу
        String login = user.getAttribute("login");
        if (login != null && login.equals("AntonBuzynnikov")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }
}

