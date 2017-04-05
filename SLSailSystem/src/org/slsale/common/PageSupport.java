package org.slsale.common;

import java.util.ArrayList;
import java.util.List;

public class PageSupport {
	//分页工具
	private Integer totalCount = 0;//总记录数
	private Integer pageCount;     //总页数
	private Integer pageSize = 8;  //页面大小
	private Integer page =1; 	       //当前页
	private Integer num = 3;		   //当前页前显示的页数
	private List items = new ArrayList<>();//当前页列表
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		if (totalCount>0) {
			this.totalCount = totalCount;
			if (this.totalCount % this.pageSize == 0) {
				this.pageCount = this.totalCount / this.pageSize;
			}else if (this.totalCount % this.pageSize > 0) {
				this.pageCount = this.totalCount / this.pageSize + 1;
			}else {
				this.pageCount = 0;
			}
		}
		
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	//当前页前显示的页数
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	//获取前一页
	public  Integer getPrv(){		
		return page-1;
	}
	
	//获取后一页
	public  Integer getNext(){		
			return page+1;
	}
	//获取最后一页
	public  Integer getLast(){		
		return pageCount;
	}
	
	//判断是不是有前一页
	public boolean getIsPrv(){		
		return page>1;
	}
	
	//判断是不是有后一页
	public boolean getIsNext(){	
		if (pageCount!=null && pageCount>page) {
			return true;
		}
		return false;				
	}
	//当前页前页数列表
	public List<Integer> getPrevPages() {
		List<Integer> list = new ArrayList<Integer>();
		Integer _frontStart = 1;
		
		if (page > num) {
			_frontStart = page - num;
		}
		
		
		for (Integer i=_frontStart; i<page; i++) {
			list.add(i);
		}
		
		return list;
	}
	
	//当前页后页数列表
		public List<Integer> getNextPages(){
			List<Integer> list = new ArrayList<>();
			Integer next = null ;
			if (pageCount!=null) {
				if (num<pageCount && (page+num)<pageCount) {
					next = page +next; 
				}else {
					next = pageCount;
				}	
				for (int i = page+1; i <= next; i++) {
					list.add(i);
				}		
			}
			
			return list;
		}
	
}
