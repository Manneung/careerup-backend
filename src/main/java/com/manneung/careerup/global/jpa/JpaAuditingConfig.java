package com.manneung.careerup.global.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    //todo CareerupApplication에 @EnableJpaAuditing을 넣어야 하는지 이렇게 하는 게 좋은지 생각해보기

}
