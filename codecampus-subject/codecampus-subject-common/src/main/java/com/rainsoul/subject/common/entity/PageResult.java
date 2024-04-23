package com.rainsoul.subject.common.entity;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果封装类
 *
 * @param <T> 结果集元素类型，泛型参数，表示分页结果中包含的数据对象类型
 */
@Data
public class PageResult<T> {

    /**
     * 当前页码，默认为1
     */
    private Integer pageNo = 1;

    /**
     * 每页大小，默认为20
     */
    private Integer pageSize = 20;

    /**
     * 总记录数
     */
    private Integer total = 0;

    /**
     * 总页数
     */
    private Integer totalPages = 0;

    /**
     * 当前页数据结果集，初始化为空列表
     */
    private List<T> result = Collections.emptyList();

    /**
     * 当前页数据起始索引（从1开始）
     */
    private Integer start = 1;

    /**
     * 当前页数据结束索引
     */
    private Integer end = 0;

    /**
     * 设置当前页数据结果集，并根据结果集更新总记录数和相关计算字段
     *
     * @param result 当前页数据结果集
     */
    public void setRecords(List<T> result) {
        this.result = result;
        if (result != null && result.size() > 0) {
            setTotal(result.size());
        }
    }

    /**
     * 设置总记录数，并根据总记录数更新总页数和相关计算字段
     *
     * @param total 总记录数
     */
    public void setTotal(Integer total) {
        this.total = total;
        if (this.pageSize > 0) {
            this.totalPages = (total / this.pageSize) + (total % this.pageSize > 0 ? 1 : 0);
        } else {
            this.totalPages = 0;
        }
        this.start = (this.pageSize > 0 ? (this.pageNo - 1) * this.pageSize : 0) + 1;
        this.end = (this.start - 1 + this.pageSize * (this.pageNo > 0 ? 1 : 0));
    }

    /**
     * 设置每页大小，并触发相关计算字段更新
     *
     * @param pageSize 每页大小
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 设置当前页码，并触发相关计算字段更新
     *
     * @param pageNo 当前页码
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
