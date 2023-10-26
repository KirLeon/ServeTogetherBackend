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
			List.of("/api/v1/registration.*", "/api/v1/authorize.*", "/swagger-ui.*", "/v3/api-docs.*");
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestUrl = request.getRequestURI();
		log.info("Request url: {}", requestUrl);
		
		if (isPermittedUrl(requestUrl)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String authToken = request.getHeader("authToken");
		
		try {
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
		} catch (Exception e) {
			handleException(e, response);
		}
	}
	
	private boolean isPermittedUrl(String requestUrl) {
		return permittedUrls.stream().anyMatch(requestUrl::matches);
	}
	
	private void handleException(Exception e, HttpServletResponse response) throws IOException {
		int statusCode = 500;
		String errorMessage = "Unknown error";
		
		if (e instanceof InvalidTokenException) {
			statusCode = 401;
			errorMessage = "Invalid token";
		} else if (e instanceof UserNotFoundException) {
			statusCode = 401;
			errorMessage = "User not found";
		} else if (e instanceof InvalidUserRoleException) {
			errorMessage = "Unable to determine user role";
		}
		
		sendErrorResponse(response, errorMessage, statusCode);
	}
	
	private void sendErrorResponse(HttpServletResponse response, String error, int statusCode) throws IOException {
		response.setHeader("error", error);
		response.sendError(statusCode);
	}
}
