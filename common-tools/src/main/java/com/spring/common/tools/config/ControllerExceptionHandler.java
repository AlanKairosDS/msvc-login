package com.spring.common.tools.config;

import com.spring.common.tools.entity.RestResponse;
import org.apache.http.MethodNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<RestResponse<Object>> myOwnException (Exception e, WebRequest request) {
    return new ResponseEntity<>(this.armarRespuesta(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
            HttpStatus.BAD_REQUEST);
  }

  public RestResponse<Object> armarRespuesta (int code, String message) {
    return RestResponse.builder()
            .code(code)
            .message("Hubo un error interno al procesar los datos")
            .description(message)
            .error(true)
            .data(null)
            .build();
  }
}
