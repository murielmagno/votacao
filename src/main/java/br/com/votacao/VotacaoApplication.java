package br.com.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
@EnableScheduling
public class VotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return route()
				.path("/v3/api-docs", builder -> builder.nest(accept(APPLICATION_JSON),
						b1 -> b1.GET("", this::v3ApiDocs))
				)
				.path("/v3/api-docs", builder -> builder.nest(accept(TEXT_HTML),
						b2 -> b2.GET("", this::swaggerUi))
				)
				.build();
	}

	private ServerResponse v3ApiDocs(ServerRequest request) {
		// Defina a definição OpenAPI da sua aplicação em JSON
		String openApiJson = "{"
				+ "\"openapi\": \"3.0.0\","
				+ "\"info\": {"
				+ "  \"title\": \"Minha API\","
				+ "  \"version\": \"1.0.0\""
				+ "},"
				+ "\"paths\": {"
				+ "  \"/public/endpoint\": {"
				+ "    \"get\": {"
				+ "      \"summary\": \"Descrição da operação GET\","
				+ "      \"responses\": {"
				+ "        \"200\": {"
				+ "          \"description\": \"Sucesso\""
				+ "        }"
				+ "      }"
				+ "    }"
				+ "  }"
				+ "}"
				+ "}";

		return ServerResponse.ok().body(openApiJson);
	}

	private ServerResponse swaggerUi(ServerRequest request) {

		return ServerResponse.temporaryRedirect(URI.create("/swagger-ui/index.html?url=/v3/api-docs")).build();
	}

}
