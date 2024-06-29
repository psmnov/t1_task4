package ru.t1.tasks;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RoleService {

    private final WebClient webClient;

    public RoleService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getRolesAsString() {
        return webClient.get()
                .uri("http://193.19.100.32:7000/api/get-roles")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }
}
