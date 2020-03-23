package com.company.demo.util;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PageUtil {
    public int limit;

    public int page;

    public PageUtil(int limit, int page) {
        this.limit = limit;
        if (page < 1) {
            this.page = 1;
        } else  {
            this.page = page;
        }
    }

    public int calculateOffset() {
        return (page - 1) * limit;
    }

    public int calculateTotalPage(int totalItems) {
        int totalPages;
        if (totalItems % limit == 0) {
            totalPages = totalItems / limit;
        } else {
            totalPages = totalItems / limit + 1;
        }

        return totalPages;
    }
}
