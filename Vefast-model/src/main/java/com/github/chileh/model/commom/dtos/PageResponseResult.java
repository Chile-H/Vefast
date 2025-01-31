package com.github.chileh.model.commom.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageResponseResult<T> extends ResponseResult<T> implements Serializable {

    private Integer currentPage;
    private Integer size;
    private Integer total;

    public PageResponseResult() {

    }

    public PageResponseResult(Integer currentPage, Integer size, Integer total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
    }
}
