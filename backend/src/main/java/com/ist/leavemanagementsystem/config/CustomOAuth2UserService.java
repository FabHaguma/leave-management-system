package com.ist.leavemanagementsystem.config;

import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.repository.RoleRepository;
import com.ist.leavemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = new DefaultOAuth2UserService().loadUser(request);
        String email = user.getAttribute("email");

        if (email == null || !email.endsWith("@ist.com")) {
            throw new OAuth2AuthenticationException("Unauthorized email domain");
        }

        User appUser = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(user.getAttribute("name"));
                    newUser.setRoles(Set.of(roleRepository.findByName("STAFF")));
                    return userRepository.save(newUser);
                });

        return new DefaultOAuth2User(
                appUser.getAuthorities(),
                user.getAttributes(),
                "email");
    }
}
