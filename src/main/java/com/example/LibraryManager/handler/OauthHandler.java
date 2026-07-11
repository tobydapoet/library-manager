package com.example.LibraryManager.handler;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.requests.user.UserGoogleCreateRequest;
import com.example.LibraryManager.services.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OauthHandler implements AuthenticationSuccessHandler {
    @Lazy
    private final SessionService sessionService;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) auth.getPrincipal();

        UserGoogleCreateRequest userDto = new UserGoogleCreateRequest();
        userDto.setEmail(oAuth2User.getAttribute("email"));
        userDto.setName(oAuth2User.getAttribute("name"));
        userDto.setAvatar_url(oAuth2User.getAttribute("picture"));
        userDto.setProvided_id(oAuth2User.getAttribute("sub"));

        Map<String, String> token = sessionService.googleLogin(userDto);

        res.setContentType("application/json");
        res.getWriter().write(objectMapper.writeValueAsString(token));
        res.getWriter().flush();

    }
}
