package desafio.ustore.appui.configs;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class SSOInterceptor implements ClientHttpRequestInterceptor, RequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		request.getHeaders().add("Authorization", accessToken());
		ClientHttpResponse response = execution.execute(request, body);

		return response;
	}

	@Override
	public void apply(RequestTemplate template) {
		template.header("Authorization", accessToken());
	}

	private String accessToken() {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();

		return (String) session.getAttribute("token");

	}

}
