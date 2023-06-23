package com.spring.common.tools.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestResponse<T> {

  private boolean error;
  private int code;
  private String message;
  private String description;
  private T data;
}
