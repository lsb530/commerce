package com.boki.commerce.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.dto.UserSession;
import com.boki.commerce.resolver.LoginUserArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ComponentScan(basePackages = {"com.boki.commerce.config"})
public class ControllerTestEnv {

    @MockBean
    protected LoginUserArgumentResolver loginUserArgumentResolver;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    protected User user;

    protected MockHttpSession session;

    protected final String email = "commerce@commerce.com";

    protected final String password = "password";

    protected final String HEADER_NAME = "content-type";

    protected final String HEADER_VALUE = "application/json;charset=UTF-8";

    @BeforeEach
    protected void setUp(WebApplicationContext webApplicationContext) throws Exception {
        objectMapper = webApplicationContext.getBean("mappingJackson2HttpMessageConverter",
            MappingJackson2HttpMessageConverter.class).getObjectMapper();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .apply(springSecurity())
            .alwaysDo(print())
            .build();

        user = User.builder().email(email).password(password).build();
        UserSession userSession = UserSession.of(user);

        session = new MockHttpSession();
        session.setAttribute("user", userSession);

        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(
            userSession);
    }
}