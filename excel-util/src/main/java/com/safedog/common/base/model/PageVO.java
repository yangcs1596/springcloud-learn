package com.safedog.common.base.model;

import lombok.Data;

import java.util.List;

/**
 * 用于分页查询的封装对象
 * @author chenxiaojian
 */
@Data
public class PageVO<T> {

	/**
	 * 默认的一页显示数量
	 */
	public static int DEFAULT_PAGE_SIZE = 10;
	/**
	 * 默认显示的第no页
	 */
	public static int DEFAULT_PAGE_NO = 1;

	/**
	 * 每页显示的行数
	 */
	private Integer pageSize;
	
	/**
	 * 当前在第几页
	 */
	private Integer pageNo;
	
	/**
	 * 总共的数量
	 */
	private Integer totalData;
	
	/**
	 * 总页数
	 */
	private Integer totalPage;
	
	/**
	 * 最终要显示的数据
	 */
	private List<T> data;
	
	public PageVO(){}

	public PageVO(int pageSize, int pageNo){
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	/**
	 * 对pageVO里的数据进行调整
	 * 用于进行分页查询之前，调整当前页码和查询条数
	 */
	public void init(){
		if(this.getPageNo() == null || this.getPageNo() < 1){
			//如果查询的当前页数小于1，则从第一页开始查询
			this.setPageNo(PageVO.DEFAULT_PAGE_NO);
		}
		if(this.getPageSize() == null || this.getPageSize() < 1){
			//如果查询当前页显示的条数小于1，则默认查询10条数据
			this.setPageSize(PageVO.DEFAULT_PAGE_SIZE);
		}
	}

	/**
	 * 调整各个参数
	 * 用于查询结束后，对各个参数进行调整
	 */
	public void adjustData(){
		if(totalData != null && totalData > 0){
			//数据总数大于0，如果能整除，直接相除得到页数，否则相除加一得到页数
			this.totalPage = totalData%pageSize==0 ?totalData/pageSize : (totalData/pageSize+1);
		}else{
			//总数不大于0，则设置总数位0，总的页数为1，以及当前页数为1
			this.totalPage = 1;
			this.totalData = 0;
			this.pageNo = 1;
			if(data != null && data.size() > 0){
				//如果查询出来的数据总数大于0，则修改总的数据数量为当前查询出的数据数量
				totalData = data.size();
			}
		}
		if(totalPage < pageNo) {
			//如果总的页数小于当前查询页数，则修改当前查询页数为总的页数
			this.pageNo = totalPage;
		}
	}

}
