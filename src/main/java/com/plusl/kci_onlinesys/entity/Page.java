package com.plusl.kci_onlinesys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: kci_onlinesys
 * @description: 分页相关
 * @author: PlusL
 * @create: 2022-03-15 14:33
 **/


public class Page {
    //当前页面
    private int current = 1;
    //每页条数
    private int limit = 9;
    //数据总数（用于计算总页数）
    private int rows;
    //查询路径（用于复用分页链接）
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >=1 && limit <= 100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0){
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
     * 获得当前页面的起始行
     * @return 起始行
     */
    public int getOffset(){
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     * @return 总页数
     */
    public int getTotal(){
        if(rows % limit == 0){
            return rows / limit;
        }else {
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码
     * @return
     */
    public int getFrom(){
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取终止页码，
     * 这两个方法的目的是：在页面中，我们不能将所有的页号全部显示出来，
     * 那样前端布局会炸，所以只获取离当前页近的几页
     * @return
     */
    public int getTo(){
        int to = current + 2;
        int total = getTotal();
        if(to > total){
            return total;
        }else {
            return to;
        }
    }

}
