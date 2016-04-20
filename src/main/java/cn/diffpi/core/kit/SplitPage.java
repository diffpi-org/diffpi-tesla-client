package cn.diffpi.core.kit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.diffpi.kit.StrKit;

/**
 * 分页封装
 * 
 * @author 董华健
 */
public class SplitPage implements Serializable {

	private static final long serialVersionUID = -7914983945613661637L;

	private static int default_page_num = 1;
	private static int default_page_size = 20;
	/**
	 * 分页查询参数
	 */
	private Map<String, String> queryParam = new HashMap<String, String>();;// 查询条件
	private String orderColunm;// 排序条件
	private String orderMode;// 排序方式
	private int pageNumber = default_page_num;// 第几页
	private int pageSize = default_page_size;// 每页显示几多

	/**
	 * 分页结果住数据
	 */
	private List<?> list; // 当前页数据
	private int totalPage; // 总页数
	private int totalRow; // 总行数

	/**
	 * 分页显示辅助属性
	 */
	private int currentPageCount;// 当前页记录数量
	private boolean isFirst;// 是否第一页
	private boolean isLast;// 是否最后一页

	/**
	 * 分页计算
	 */
	public void compute() {
		if(totalRow == 0){
			getTotalPage();
		}
		
		this.currentPageCount = (null != this.list ? this.list.size() : 0);// 当前页记录数

		if (pageNumber == 1) {
			this.isFirst = true;
		} else {
			this.isFirst = false;
		}

		if (pageNumber == totalPage) {
			this.isLast = true;
		} else {
			this.isLast = false;
		}
	}

	public int getTotalPage() {
		if ((this.totalRow % this.pageSize) == 0) {
			this.totalPage = this.totalRow / this.pageSize;// 计算多少页
		} else {
			this.totalPage = this.totalRow / this.pageSize + 1;// 计算多少页
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Map<String, String> getQueryParam() {
		return queryParam;
	}

	public SplitPage addQueryParam(Map<String, String> queryParam) {
		this.queryParam.putAll(queryParam);
		return this;
	}
	public SplitPage addQueryParam(String key,String value) {
		if(StrKit.notBlank(value)){
			this.queryParam.put(key,value);
		}
		return this;
	}

	public String getOrderColunm() {
		return orderColunm;
	}

	public void setOrderColunm(String orderColunm) {
		this.orderColunm = orderColunm;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public int getPageNumber() {
		if (pageNumber <= 0) {
			pageNumber = default_page_num;
		}
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		if (pageSize <= 0) {
			pageSize = default_page_size;
		}
		if (pageSize > 200) {
			pageSize = default_page_size;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageCount() {
		return currentPageCount;
	}

	public void setCurrentPageCount(int currentPageCount) {
		this.currentPageCount = currentPageCount;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

}
