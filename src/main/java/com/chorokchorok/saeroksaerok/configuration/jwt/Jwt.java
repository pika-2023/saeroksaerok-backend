package com.chorokchorok.saeroksaerok.configuration.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import com.chorokchorok.saeroksaerok.core.common.SecurityUser;

public class Jwt extends AbstractAuthenticationToken {

	private final Object principal;

	public Jwt(Object principal) {
		super(null);
		this.principal = principal;
		setAuthenticated(false);
	}

	public Jwt(SecurityUser principal) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.principal = principal;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
}
