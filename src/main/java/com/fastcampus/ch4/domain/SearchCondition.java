package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class SearchCondition {
   private Integer page=1;
   private String keyword="";
   private String option="";
   private Integer pageSize = 10;

   public SearchCondition() {}

    public SearchCondition(Integer page, Integer pageSize, String keyword, String option) {
        this.page = page;
        this.keyword = keyword;
        this.option = option;
        this.pageSize = pageSize;
    }

    public String getQueryString(Integer page) {
        return UriComponentsBuilder.newInstance()
                .queryParam("page",page)
                .queryParam("keyword",keyword)
                .queryParam("option",option)
                .queryParam("pageSize",pageSize)
                .build().toString();
    }

    public String getQueryString() {
        return getQueryString(getPage());
    }


    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                ", offset=" + getOffset() +
                ", pageSize=" + pageSize +
                '}';
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getOffset() {
        return (page-1) * pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
