package com.example.board.exhandler.advice;

import com.example.board.dto.CommonErrorCode;
import com.example.board.dto.ErrorCode;
import com.example.board.dto.ErrorResponse;
import com.example.board.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


// 일단 springMVC오류잖아? 이걸 커스텀하고싶은거지?
// 왜냐하면 기본적으로는 defaultExceptionResolver가 동작하니까
// 내가 하고싶은건.. 기본 동작에 대한 메시지를 바꾸고 싶은건데.. 다 바꿔도되는건가? 필요한것만 바꿔도 되지 않나?
// 일단.. 디폴트메시지익셉션핸들러가 동작하지 않는지 부터 체크하자.
// 모든 예외를 다잡아줄수가 없다.. 그렇다면
// 일단 확인이 필요한건 스프링MVC 예외를 잡아주는가?
// 잡아주긴하는데 Default에서 잡는게 아니라 ExceptionHandlerExceptionResolver 여기에서 잡는구나. 그렇다면 내가 커스텀하길 원하는 부분만 로깅하면 될듯?
// 근데 그러면 일관성이 없잖아
// 아 일관성을 잡는 방법이 있다. Json response에서 구조를 짜고 나머지 두분에 data를 넣어서 공통화하면되겠다.
//
@Slf4j
@RestControllerAdvice(basePackages = "com.example.board.api")
@RequiredArgsConstructor
public class GlobalExceptionControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.error("[handleRestApiException]", e);
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception e) {
        log.error("[handleAllException]", e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }
    
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleMehtodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.error("[handleMehtodArgumentNotValidException]", e);
//
//        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
//        return handleExceptionInternal(errorCode, e);
//    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, BindException e) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, e));
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, BindException e) {
        List<ErrorResponse.ValidationError> validationErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrors)
                .build();
    }

    private ErrorResponse makeErrorResponse(ErrorCode code) {
        return ErrorResponse.builder()
                .code(code.name())
                .message(code.getMessage())
                .build();
    }

    private ErrorResponse makeErrorResponse(String code, String msg) {
        return ErrorResponse.builder()
                .code(code)
                .message(msg)
                .build();
    }
}
