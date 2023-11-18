package com.example.board.exhandler.advice;

import com.example.board.exception.UserException;
import com.example.board.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.board.api")
public class ExceptionControllerAdvice {

//    RestControllerAdvice인데...ResponseEntity랑 ErrorResult를 그대로 반환한거 차이가 있나?

    /**
     * exceptionResolver를 사용하는 이유는 WAS까지 에러가 전파하도록 하지 않기 위함임. WAS로 에러가 전달되면 그냥 500 에러를 내뱉기 떄문이다.
     * 그래서 컨트롤러에서 Exception을 잡아서 에러를 정의해서 내뱉어야함.
     * ResponseEntity는 HTTP 상태코드 지정이 가능함. ResponseStatus 를 따로 지정하지 않으면 200코드임. 즉 그냥 정상!
     */

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        return new ResponseEntity<>(new ErrorResult("USER-EX", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
