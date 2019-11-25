package com.wondersgroup.cloud.paas.storage.pojo;

import java.util.List;

/**
 * @author chenlong
 */
public class MessageGetPojo extends ResponsePojo {
    private List<MessageItem> items;
    private int total;
    private int page;
    private int page_size;

    public List<MessageItem> getItems() {
        return items;
    }

    public void setItems(List<MessageItem> items) {
        this.items = items;
    }

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
}
