package ru.t1.tasks;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class App {

    private final static String PROTOCOL = "http";
    private final static String HOST = "193.19.100.32";
    private final static int PORT = 7000;
    private static final RestTemplate restTemplate = createRestTemplate();

    public static void main(String[] args) {

        String result;
        System.out.println("Вам доступны следующие роли:" + fetchData(constructUri("/api/get-roles")));

        Scanner inputData = new Scanner(System.in);
        HashMap<String, String> user = new HashMap<>();
        System.out.print("\nВведите вашу фамилию: ");
        user.put("last_name", inputData.nextLine());
        System.out.print("Введите ваше имя: ");
        user.put("first_name", inputData.nextLine());
        System.out.print("Введите электронную почту: ");
        user.put("email", inputData.nextLine());
        System.out.print("Введите название интересующей вас специальности из списка: ");
        user.put("role", inputData.nextLine());

        result = sendData(user, constructUri("/api/sign-up"));
        System.out.println(result);

        String code = fetchDataWithParam(constructUriWithParam("/api/get-code", user.get("email")));
        code = code.trim().replaceAll("\"", "");

        HashMap<String, String> token = new HashMap<>();
        token.put("token",encodeToken(user.get("email"), code));
        token.put("status", "increased");
        System.out.println(sendData(token, constructUri("/api/set-status")));
        //Для фиксации выполнения задания
        System.out.println(sendData(token, constructUri("/api/set-status")));
    }

    private static RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        final HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .build();

        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    public static String constructUri(String api) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme(PROTOCOL)
                .host(HOST)
                .port(PORT)
                .path(api)
                .toUriString();
        return uri;
    }

    public static String constructUriWithParam(String api, String email) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme(PROTOCOL)
                .host(HOST)
                .port(PORT)
                .path(api)
                .queryParam("email", email)
                .toUriString();
        return uri;

    }

    public static String encodeToken(String email, String code) {
        String emailCode = email.concat(":").concat(code);
        return Base64.getEncoder().encodeToString(emailCode.getBytes());
    }

    public static String fetchData(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return checkResponse(response);
    }

    public static String sendData(Map<String, String> data, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        return checkResponse(response);
    }

    public static String fetchDataWithParam(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return checkResponse(response);
    }

    public static String checkResponse(ResponseEntity<String> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return ("Ошибка: " + response.getBody() + response.getStatusCode() + " " + response.getHeaders());
        }
    }
}
