package ru.t1.tasks;

import cn.hutool.json.JSONObject;

import io.netty.channel.ChannelOption;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Base64;



public class App
{

    /*private static final WebClient client = WebClient.create();*/

    public static void doPostAddStudent(){
        throw new RuntimeException();

    }
    public static JSONObject doGetCode(){
        throw new RuntimeException();
    }
    public static String getStringToIncreaseStatus(String email, String code) {
        String emailCode = email.concat(":").concat(code);
        return Base64.getEncoder().encodeToString(emailCode.getBytes());
    }

    public static void doPostIncreaseTheLvl(){

    }
    public static void main( String[] args )
    {
        /*HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000));*/

        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        RoleService roleService = context.getBean(RoleService.class);
        MyService myService = new MyService(roleService);
        myService.useRoles();
    }
}
