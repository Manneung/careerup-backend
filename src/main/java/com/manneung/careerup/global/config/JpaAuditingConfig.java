package com.manneung.careerup.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    //CareerupApplication에 @EnableJpaAuditing 넣는 대신
    //JpaAuditingConfig 생성

}
