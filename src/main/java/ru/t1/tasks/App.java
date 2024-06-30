package ru.t1.tasks;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws MalformedURLException {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        /*WebClient webClient = context.getBean(WebClient.Builder.class).build();*/
        WebClient webClient = WebClient.create();

        /*fetchData(webClient, "http://193.19.100.32:7000/api/get-roles")
                .subscribe(response -> {
                    System.out.println("Получен ответ: " + response);
                }, error -> {
                    System.err.println("Ошибка при выполнении запроса: " + error.getMessage());
                });*/

        String hostname = "193.19.100.32";
        int port = 7000;
        String apiPath = "/api/get-code";
        String email = "petr.smirnowv@gmail.com";
        String urlWithParams = String.format("http://%s:%d%s?email=%s",
                hostname, port, apiPath, email);
        System.out.println(urlWithParams);
        fetchDataWithParam(webClient, email)
                .doOnSuccess(response -> {
                    System.out.println("Запрос успешно выполнен: " + response);
                })
                .subscribe(response -> {
                    System.out.println("Ответ: " + response);
                }, error ->{
                    System.out.println("Ошибка");
                });


        /*fetchDataWithParam(webClient, "petr.smirnowv1@gmail.com")
                .doOnSuccess(response -> {
                    System.out.println("Запрос успешно выполнен: " + response);
                })
                .subscribe(response -> {
                    System.out.println("Получен ответ: " + response);
                }, error -> {
                    System.err.println("Ошибка при выполнении запроса: " + error.getMessage());
                });

    }*/
    }
    public static Mono<String> fetchData(WebClient webClient, String url) {
        return webClient.get()
                .uri(url)
                .header("Accept", "text/plain")
                .retrieve()
                .bodyToMono(String.class); // Или другой тип данных
    }
    public static Mono<String> fetchDataWithParam(WebClient webClient, String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("193.19.100.32")
                        .port(7000)
                        .path("/api/get-code")
                        .queryParam("email", email)
                        .build())
                .header("Accept", "text/plain")
                .retrieve()
                .bodyToMono(String.class);
    }

}
