package com.spring.common.tools.service;

import com.spring.common.tools.entity.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UtilService {

  public ResponseEntity<RestResponse<Object>> armarRespuesta (int code, String message, String description,
          boolean error, Object data, HttpStatus status) {
    return new ResponseEntity<>(
            RestResponse.builder()
                    .code(code)
                    .message(message)
                    .description(description)
                    .error(error)
                    .data(data)
                    .build(),
            status);
  }

  public String obtenerFechaActual () {
    LocalDateTime fechaHoraActual = LocalDateTime.now();
    DateTimeFormatter fechaHoraFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    return fechaHoraActual.format(fechaHoraFormat);
  }
}
