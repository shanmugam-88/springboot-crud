package org.learn.curd.dto;

public class ResponsePageable {

    private long total;
    private int offset;
    private int limit;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public ResponsePageable(long total, int offset, int limit) {
        this.total = total;
        this.offset = offset;
        this.limit = limit;
    }
}
