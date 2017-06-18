package cn.cj.appthems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AppThemsApplication {
	@RequestMapping("/")
	public String wel() {
		return "hello world!";
	}
	/*@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	   return (container -> {
	        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error.html");
	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error.html");
	        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.html");

	        container.addErrorPages(error401Page, error404Page, error500Page);
	   });
	}*/
	
	
	public static void main(String[] args) {
		SpringApplication.run(AppThemsApplication.class, args);
	}
}
