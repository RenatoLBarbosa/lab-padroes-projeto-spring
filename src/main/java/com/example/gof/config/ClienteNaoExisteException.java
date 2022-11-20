package com.example.gof.config;

import com.example.gof.model.Cliente;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ClienteNaoExisteException extends RuntimeException {
    public ClienteNaoExisteException() {
        super("Cliente com id solicitado não existe!");
    }

    public ClienteNaoExisteException(String message, Throwable cause) {
        super(message, cause);
    }
}