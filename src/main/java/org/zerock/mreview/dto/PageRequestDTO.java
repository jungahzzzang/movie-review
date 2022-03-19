package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO<B, T> { //JPA에서 사용하는 Pageable 타입의 객체를 생성하는 것이 목적임.

    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO(){
        this.page=1;
        this.size=10;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);    //페이지 번호가 0부터 시작하기 때문에 1페이지의 경우 0이 될 수 있도록 page-1
    }
}
