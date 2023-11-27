package com.example.board.exhandler.advice;

import com.example.board.dto.CommonErrorCode;
import com.example.board.dto.ErrorCode;
import com.example.board.dto.ErrorResponse;
import com.example.board.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.board.dto.ErrorResponse.*;


// 일단 springMVC오류잖아? 이걸 커스텀하고싶은거지?
// 왜냐하면 기본적으로는 defaultExceptionResolver가 동작하니까
// 내가 하고싶은건.. 기본 동작에 대한 메시지를 바꾸고 싶은건데.. 다 바꿔도되는건가? 필요한것만 바꿔도 되지 않나?
// 일단.. 디폴트메시지익셉션핸들러가 동작하지 않는지 부터 체크하자.
// 모든 예외를 다잡아줄수가 없다.. 그렇다면
// 일단 확인이 필요한건 스프링MVC 예외를 잡아주는가?
// 잡아주긴하는데 Default에서 잡는게 아니라 ExceptionHandlerExceptionResolver 여기에서 잡는구나. 그렇다면 내가 커스텀하길 원하는 부분만 로깅하면 될듯?
// 근데 그러면 일관성이 없잖아
// 아 일관성을 잡는 방법이 있다. Json response에서 구조를 짜고 나머지 두분에 data를 넣어서 공통화하면되겠다.
// 나머지 스프링 예외도 모두 잡아야겠다.
@Slf4j
@RestControllerAdvice(basePackages = "com.example.board.api")
@RequiredArgsConstructor
public class GlobalExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

//    @ExceptionHandler(RestApiException.class)
//    public ResponseEntity<Object> handleRestApiException(RestApiException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//
//
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(c -> ValidationError.of(messageSource, c)).filter(Objects::nonNull).collect(Collectors.toList());
        CommonErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getHttpStatus(), errorCode.getMessage(), errors);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

}
