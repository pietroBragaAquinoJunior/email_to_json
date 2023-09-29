package com.pietro.json_to_email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pietro.json_to_email.model.MyObject;
import com.pietro.json_to_email.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Estou usando ObjectMapper que vem da dependencia : spring-boot-starter-json
 * <p>
 * Poderia usar Gson que vem da dependencia: Gson
 */

@SpringBootApplication
public class JsonToEmailApplication {

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(JsonToEmailApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void enviarEmail() {
        String jsonString = "{\"nome\":\"pietro\", \"email\":\"pietrobragaaquino@gmail.com\"}";
        System.out.println("Usando spring-boot-starter-json:");
        MyObject myObject = new MyObject();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            myObject = objectMapper.readValue(jsonString, MyObject.class);
            System.out.println(myObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Usando Gson:");
        MyObject myObject2 = new MyObject();
        try {
            Gson gson = new Gson();
            myObject2 = gson.fromJson(jsonString, MyObject.class);
            System.out.println(myObject2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Enviando Email");

        try {
            emailService.sendEmail(myObject.getEmail(), "Assunto do Email", "Conteúdo do email.");
            System.out.println("E-mail enviado com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar o e-mail.");
        }
    }
}