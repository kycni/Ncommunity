package com.kycni.community.entity;

/**
 * @author Kycni
 * @date 2022/2/12 23:06
 */
public class Page {
    // 当前页,初始值为1
    private int current = 1;
    // 单页容量
    private int limit = 10;
    // 总数据量 (用于计算总页数)
    private int rows;
    // 查询路径 (用于复用分页链接)
    private String path;

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", limit=" + limit +
                ", rows=" + rows +
                ", path='" + path + '\'' +
                '}';
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current > 0) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > 0 && limit < 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {

        if (rows > 0) {
            this.rows = rows;
        }
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取offset当前页起始行
     * @return
     */
    public int getOffset() {
        // offset = current * limit - limit
        return ((current - 1) * limit);
    }

    /**
     * 用来获得总页数(边界判断条件)
     * @return
     */
    public int getTotal() {
        if (rows % limit == 0) {
           return rows / limit;
        } else  {
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码
     * @return
     */
    @SuppressWarnings("ManualMinMaxCalculation")
    public int getStartPage () {
        // 当前页前后两页 (当前页最少为3，才会有5个完整页码显示)
        int startPage = current - 2;
        return startPage < 1 ? 1 : startPage;
    }

    /**
     * 获取终止页码
     * @return
     */
    @SuppressWarnings("ManualMinMaxCalculation")
    public int getLastPage () {
        // 当前页前后两页 (当前页最少为3，才会有5个完整页码显示)
        int lastPage = current + 2;
        int totalPage = getTotal();
        return lastPage > totalPage ? totalPage : lastPage;
    }
    
}
