package vn.hoanggiang.jobhunter.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPaginationDTO {
  private Meta meta;
  private Object data;
}
