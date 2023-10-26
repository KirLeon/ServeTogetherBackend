package com.bsuiramt.servetogetherbackend.security;

import com.bsuiramt.servetogetherbackend.exception.InvalidTokenException;
import com.bsuiramt.servetogetherbackend.exception.InvalidUserRoleException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.model.UserRole;
import com.bsuiramt.servetogetherbackend.service.AuthenticationService;
import com.bsuiramt.servetogetherbackend.service.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeaderBasedAuthorizationFilter extends OncePerRequestFilter {
	
	private final AuthorizationService authorizationService;
	private final AuthenticationService authenticationService;
	private final List<String> permittedUrls =
			List.of("/api/v1/signup.*", "/api/v1/authorization.*", "/swagger-ui.*", "/v3/api-docs.*");
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestUrl = request.getRequestURI();
		log.info("Request url: {}", requestUrl);
		
		if (isPermittedUrl(requestUrl)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			String authToken = request.getHeader("authToken");
			
			authenticationService.authenticateUser(authToken);
			UserRole userRole = authorizationService.checkUserRole(authToken);
			
			if (requestUrl.startsWith("/api/v1/admin/")) {
				if (userRole == UserRole.ADMIN) {
					filterChain.doFilter(request, response);
				} else {
					sendErrorResponse(response, "Invalid access level", 403);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		} catch (UserNotFoundException e) {
			sendErrorResponse(response, "Invalid user", 403);
		} catch (InvalidUserRoleException e) {
			sendErrorResponse(response, "Invalid user role", 500);
		} catch (InvalidTokenException e) {
			sendErrorResponse(response, "Invalid token", 403);
		}
	}
	
	private boolean isPermittedUrl(String requestUrl) {
		return permittedUrls.stream().anyMatch(requestUrl::matches);
	}
	
	
	private void sendErrorResponse(HttpServletResponse response, String error, int statusCode) throws IOException {
		response.setHeader("error", error);
		response.sendError(statusCode);
	}
}
