package com.example.board.exhandler.advice;

import com.example.board.exception.UserException;
import com.example.board.exhandler.ErrorResult;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.board.api")
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final MessageSource ms;


//    RestControllerAdvice인데...ResponseEntity랑 ErrorResult를 그대로 반환한거 차이가 있나?

    /**
     * exceptionResolver를 사용하는 이유는 WAS까지 에러가 전파하도록 하지 않기 위함임. WAS로 에러가 전달되면 그냥 500 에러를 내뱉기 떄문이다.
     * 그래서 컨트롤러에서 Exception을 잡아서 에러를 정의해서 내뱉어야함.
     * ResponseEntity는 HTTP 상태코드 지정이 가능함. ResponseStatus 를 따로 지정하지 않으면 200코드임. 즉 그냥 정상!
     */

    /**
     * 일반적으로 ExceptionHandler를 사용하게 되면 Response가 200 OK로 전달된다.
     * 그래서 ResponseStatus로 바꿔줘야함 200 코드를 바꿔서 전달해야하긴 한다. 500 이닌 400 에러로. 즉, 사용자 에러라는 의미로 전달해줘야하긴 함.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalAArgumentExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

// 여긴 200 에러로 전달되는군.
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        return new ResponseEntity<>(new ErrorResult("USER-EX", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult constraintViolationExHandler(MethodArgumentNotValidException e) {
        log.error("[exceptionHandler] ex", e);

        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();


        for (ObjectError error : allErrors) {
            String message = Arrays.stream(Objects.requireNonNull(error.getCodes()))
                    .map(c -> {
                        Object[] arguments = error.getArguments();
                        Locale locale = LocaleContextHolder.getLocale();

                        try {
                            return ms.getMessage(c, arguments, locale);

                        } catch (NoSuchMessageException ex) {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .findFirst()
                    .orElse(error.getDefaultMessage());

            log.error("[exceptionHandler] error messages : {}", message);
        }

        return new ErrorResult("BAD", e.getMessage());
    }

}
