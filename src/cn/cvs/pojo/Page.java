package cn.cvs.pojo;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private int pageNo;
    private int pageSize=5;
    private int pageDataCount;
    private int pageCount;
    private List<AppInfo> appInfoList=new ArrayList<>();

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageDataCount() {
        return pageDataCount;
    }

    public void setPageDataCount(int pageDataCount) {
        this.pageDataCount = pageDataCount;
        pageCount = this.pageDataCount % pageSize == 0 ? this.pageDataCount / pageSize : (this.pageDataCount / pageSize) + 1;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<AppInfo> getAppInfoList() {
        return appInfoList;
    }

    public void setAppInfoList(List<AppInfo> appInfoList) {
        this.appInfoList = appInfoList;
    }
}
