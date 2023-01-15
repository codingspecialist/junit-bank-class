package shop.mtcoding.bank.handler.ex;

import java.util.Map;

import lombok.Getter;

@Getter
public class CustomValidationException extends RuntimeException {

    private Map<String, String> erroMap;

    public CustomValidationException(String message, Map<String, String> erroMap) {
        super(message);
        this.erroMap = erroMap;
    }
}
