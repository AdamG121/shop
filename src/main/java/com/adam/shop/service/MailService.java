package com.adam.shop.service;


import java.util.Map;

public interface MailService {
    void send(String mail, String templateName, Map<String, Object> variables, byte[] file, String fileName);
}
