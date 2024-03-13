package com.nikita.shop.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikita.shop.model.UserDto;
import com.nikita.shop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFilter implements Filter {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {

            JsonNode node = objectMapper.readTree(request.getInputStream());
            if (!node.has("name")) {

                throw new EntityNotFoundException("Юзер с данным именем не может быть сохранен");
            }
            String userName = node.get("name").asText();
            String email = node.get("email").asText();
            if (StringUtils.isEmpty(userName)) {
                log.error("ошибка, поле name пустое" + userName);
                throw new EntityNotFoundException("Юзер с данным именем не может быть сохранен");
            }

            UserDto userDto = new UserDto();
            userDto.setName(userName);
            userDto.setEmail(email);
            userService.add(userDto);

            filterChain.doFilter(request, response);
            log.info("юзер сохранен" + userDto);
        } catch (Exception e) {
            throw new EntityNotFoundException("Юзер с данным именем не может быть сохранен");
        }
    }
}