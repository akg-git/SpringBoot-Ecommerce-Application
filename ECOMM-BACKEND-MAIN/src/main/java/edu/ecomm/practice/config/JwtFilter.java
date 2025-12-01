package edu.ecomm.practice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.ecomm.practice.service.impl.JWTService;
import edu.ecomm.practice.service.impl.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private ApplicationContext ctx;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		// if authentication header is not null and it starts with standard format - "Bearer <Token>". Then fetch details
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);	// extract token from header
			username = jwtService.extractusername(token);	// extract user name from token
		}
		
		// if user name is not null and its not authenticated yet - proceed for authentication
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = ctx.getBean(MyUserDetailsService.class).loadUserByUsername(username);
			
			if(jwtService.validateToken(token, userDetails) ) {
				
				UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
				newToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(newToken);
				
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
