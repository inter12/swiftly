package com.dianping.swiftly.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-17
 *  Time: 下午8:24
 * 
 * </pre>
 */
public class PageModel implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8802145055234572275L;

    /**
     * Total records size
     */
    private int               recordCount;

    /**
     * The number of records of per page
     */
    private int               pageSize;

    /**
     * Current page
     */
    private int               page             = 1;

    /**
     * Records
     */
    private List<?>           records;

    private String            sortField;

    private boolean           sortAsc          = true;

    /**
     * @return the recordCount
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * @param recordCount the recordCount to set
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the start
     */
    public int getNextStart() {
        if (page < 1) {
            return 1;
        }
        return (page - 1) * pageSize + (records != null ? records.size() : 0) + 1;
    }

    /**
     * @return the records
     */
    public List<?> getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(List<?> records) {
        this.records = records;
    }

    /**
     * Calculate page count
     */
    public int getPageCount() {
        if (recordCount == 0) {
            return 0;
        }

        return (recordCount + pageSize - 1) / pageSize;
    }

    /**
     * @return the sortField
     */
    public String getSortField() {
        return sortField;
    }

    /**
     * @param sortField the sortField to set
     */
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * @return the sortAsc
     */
    public boolean isSortAsc() {
        return sortAsc;
    }

    /**
     * @param sortAsc the sortAsc to set
     */
    public void setSortAsc(boolean sortAsc) {
        this.sortAsc = sortAsc;
    }
}
