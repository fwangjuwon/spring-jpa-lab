package site.metacoding.dbproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto<T> {
    private Integer code; // -1: 실패, 1: 성공
    private String msg;
    private T data; // generic 뭘 담을지 모른다. body 데이터를 여기 담을거니까 나중에 들어오는거 보고 해
}
