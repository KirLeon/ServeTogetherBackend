package com.bsuiramt.servetogetherbackend.security;

import com.bsuiramt.servetogetherbackend.exception.InvalidTokenException;
import com.bsuiramt.servetogetherbackend.exception.InvalidUserRoleException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeaderBasedAuthorizationFilter extends OncePerRequestFilter {
	
	private final AuthenticationService authenticationService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestUrl = request.getRequestURI();
		log.info(" request url + {requestUrl} here " + requestUrl);
		
		if (urlWithoutAuthentication(requestUrl)) {
			filterChain.doFilter(request, response);
		} else {
			String authToken = request.getHeader("authToken");
			try {
				authenticationService.checkUserRole(authToken);
				filterChain.doFilter(request, response);
			} catch (InvalidTokenException | UserNotFoundException e) {
				response.setHeader("error", "Invalid token");
				response.sendError(401);
			} catch (InvalidUserRoleException e) {
				response.setHeader("error", "Unable to determine user role");
				response.sendError(500);
			}
		}
	}
	
	private boolean urlWithoutAuthentication(String url) {
		String apiRegexAuthorize = "/api/v1/authorize.*";
		String apiRegexRegister = "/api/v1/registration.*";
		String swaggerDocsTestRegex = "/swagger-ui/.*";
		String swaggerConfigRegex = "/v3/api-docs/.*";
		return url.matches(apiRegexAuthorize) || url.matches(apiRegexRegister)
				|| url.matches(swaggerDocsTestRegex) || url.matches(swaggerConfigRegex);
	}
}
