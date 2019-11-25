package com.wondersgroup.cloud.paas.storage.pojo;

import java.util.List;

/**
 * @author chenlong
 */
public class TemplateGetPojo extends ResponsePojo {

    private List<TemplateItem> items;

    private int total;
    private int page;
    private int page_size;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public List<TemplateItem> getItems() {
        return items;
    }

    public void setItems(List<TemplateItem> items) {
        this.items = items;
    }
}
