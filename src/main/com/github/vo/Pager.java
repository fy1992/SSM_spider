package github.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 李新栋 lxd2773@163.com
 * @Date 2017/4/15 8:59
 * DataTable分页
 */
public class Pager {
    /**
     * dataTables分页使用
     */
    private int draw; // dataTables使用
    private int recordsTotal;
    private int recordsFiltered;

    /**
     * 普通分页使用
     */
    private int page; //当前页
    private List dep = new ArrayList();//部门来源
    private List data;//查询到的分页集合
    private int pages;//总页数

    /**
     * 内部使用数据
     */
    @JsonIgnore
    private int count; //数据库总数
    @JsonIgnore
    private int length;//每页长度
    @JsonIgnore
    private int start;//开始索引


    public Pager() {
    }

    /**
     * 封装 开始页, 每页长度
     *
     * @param page
     * @param length
     */
    public Pager(int page, int length) {
        this.page = page;
        this.length = length;
        this.start = (page - 1) * length;
    }

    /**
     * 封装 当前页, 每页长度, 总数, 数据集
     * 算出 总页数
     *
     * @param page
     * @param length
     * @param count
     * @param data
     */
    public Pager(int page, int length, int count, List data) {
        this.length = length;
        this.page = page;
        this.count = count;
        this.data = data;
        this.pages = (count + length - 1) / length;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List getDep() {
        if (dep == null){
            dep = new ArrayList();
        }
        return dep;
    }

    public void setDep(List dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", page=" + page +
                ", dep=" + dep +
                ", data=" + data +
                ", pages=" + pages +
                ", count=" + count +
                ", length=" + length +
                ", start=" + start +
                '}';
    }
}
