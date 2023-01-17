package shop.mtcoding.bank.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;
import shop.mtcoding.bank.handler.ex.CustomForbiddenException;
import shop.mtcoding.bank.handler.ex.CustomValidationException;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<?> fobiddenException(CustomForbiddenException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationApiException(CustomValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), e.getErroMap()), HttpStatus.BAD_REQUEST);
    }
}
