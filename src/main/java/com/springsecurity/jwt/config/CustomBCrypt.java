package com.springsecurity.jwt.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomBCrypt extends BCryptPasswordEncoder {

}
