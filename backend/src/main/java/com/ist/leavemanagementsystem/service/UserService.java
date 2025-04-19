package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.UserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserDto getUserDtoFromOAuth(OAuth2User principal) {
        if (principal == null)
            return null;
        String id = principal.getAttribute("sub");
        if (id == null)
            id = principal.getName();
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String avatarUrl = principal.getAttribute("picture");
        if (avatarUrl == null)
            avatarUrl = principal.getAttribute("avatar_url");
        return new UserDto(id, name, email, avatarUrl);
    }
}
