package com.example.orchestratorservice.web.response;

import java.util.List;

public class ApiPaginationResponse<T> {

    private List<T> content;
    private int pageIndex;
    private int size;
    private int totalPages;

    public ApiPaginationResponse(List<T> content, int pageIndex, int size, int totalPages) {
        this.content = content;
        this.pageIndex = pageIndex;
        this.size = size;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return pageIndex;
    }

    public void setPage(int page) {
        this.pageIndex = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
