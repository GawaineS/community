package com.nowcoder.community.entity;

/**
 * 封装分页相关的信息
 */

public class Page {

    // Current Page
    private int current = 1;
    // Maximum display
    private int limit = 10;
    //Total number of data(total number of pages)
    private int rows;
    //Path to specific page
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**Get the number of the starting row of the current page
     *
     * @return
     */
    public int getOffset() {
        return (current - 1) * limit;
    }

    /**Get total number of pages
     *
     * @return
     */
    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     * Get the starting page
     *
     * @return
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1:from;
    }

    /**
     * Get the last page
     *
     * @return
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
