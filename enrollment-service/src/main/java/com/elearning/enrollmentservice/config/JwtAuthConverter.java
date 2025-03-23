package com.elearning.enrollmentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principal-claim-name}")
    private String principalClaimName;

    @Value("${jwt.auth.converter.resource-access-id}")
    private String resourceAccessId;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        System.out.println("FINAL AUTHORITIES: " + authorities);

        return new JwtAuthenticationToken(jwt, authorities, getPrincipleClaimName(jwt));
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (principalClaimName != null) {
            claimName = principalClaimName;
        }
        return jwt.getClaim(claimName);

    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resource;
        Map<String, Object> resourceAccess;
        Collection<String> resourceRoles;

        if (jwt.getClaim("resource_access") == null){
            System.out.println("No resource_access found for: " + resourceAccessId);
            return Set.of();
        }
        resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(resourceAccessId) == null){
            return Set.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(resourceAccessId);
        resourceRoles = (Collection<String>) resource.get("roles");
        System.out.println("Extracted resource roles: " + resourceRoles);

        Collection<GrantedAuthority> authorities = resourceRoles.stream()
                .map(role -> {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    System.out.println("Created authority: " + authority.getAuthority());
                    return authority;
                })
                .collect(Collectors.toSet());

        return authorities;



    }
}
