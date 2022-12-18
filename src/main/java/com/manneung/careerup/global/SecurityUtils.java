package com.manneung.careerup.global;

import com.manneung.careerup.global.jwt.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.util.Objects;

import static com.manneung.careerup.domain.base.BaseResponseStatus.USER_ERROR;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class SecurityUtils {

    public static Object getLoggedInUser() {
        try {
            return ((CustomUserDetails) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getUser();
        } catch (NullPointerException e) {
            return USER_ERROR;
        }
    }
}