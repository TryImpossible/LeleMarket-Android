package com.bynn.marketll.module_mine.bean;

import lombok.Data;

@Data
public class SeeMoreBean {
    private String Title;
    private String content;

    public SeeMoreBean(String title) {
        Title = title;
    }

    public SeeMoreBean(String title, String content) {
        Title = title;
        this.content = content;
    }
}
