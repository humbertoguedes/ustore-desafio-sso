package desafio.ustore.appui;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.GenericFilterBean;

@SpringBootApplication
public class AppUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUiApplication.class, args);
	}

	@Bean
	public GenericFilterBean jWTRequestFilterBean() {
		return new GenericFilterBean() {
			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {

				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
						.currentRequestAttributes();
				final HttpSession session = attr.getRequest().getSession(true);

				if (Objects.isNull(session.getAttribute("token"))) {

					Optional.ofNullable(((HttpServletRequest) request).getHeader("Authorization"))
							.filter(fa -> fa.startsWith("Bearer")).map((a) -> {
								return a.substring(7);
							}).ifPresent(token -> {
								session.setAttribute("token", token);
								session.setMaxInactiveInterval(10);
							});

				}
				chain.doFilter(request, response);

			}
		};
	}

}
