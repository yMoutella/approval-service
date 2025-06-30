package com.reimbursement.approval_service.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRealmRoleConverterImpl implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String GROUPS_CLAIM = "groups";
    public static final String ROLES_CLAIM = "roles";
    public static final String ROLES_PREFIX = "ROLE_";
    public static final String REALM_ACCESS_CLAIM = "realm_access";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (Objects.nonNull(jwt.getClaims().get(REALM_ACCESS_CLAIM)) &&
                jwt.getClaims().get(REALM_ACCESS_CLAIM) instanceof Map<?, ?> realmAccess &&
                Objects.nonNull(realmAccess.get(ROLES_CLAIM))
                && realmAccess.get(ROLES_CLAIM) instanceof List<?> roles) {
            grantedAuthorities.addAll(this.converterRoles(roles));
        }
        if (Objects.nonNull(jwt.getClaims().get(GROUPS_CLAIM)) &&
                jwt.getClaims().get(GROUPS_CLAIM) instanceof Collection<?> roles) {
            grantedAuthorities.addAll(this.converterRoles(roles));
        }
        return grantedAuthorities;
    }

    private List<SimpleGrantedAuthority> converterRoles(Collection<?> roles) {
        return roles.stream().map(String::valueOf).map(ROLES_PREFIX::concat).map(SimpleGrantedAuthority::new).toList();
    }
}
