package com.safedog.cloudnet.generate.utils;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * 表描述
 */
public class TableDesc{
	//保持大写
	private String name;
	
	private String metaName;
	
	private String humpName;
	/**
	 * 首字母大写
	 */
	private String humpNameFU;
	
	// 默认为id，列明采用小写
	private String idName="id";
	
	private String metaIdName;
	
	private String remark = null;
	// 采用大写,为了方便查询
	private Set<String> cols = new LinkedHashSet<String>();
	
	private Set<String> metaCols = new LinkedHashSet<String>();
	
	
	//table 列的详细描述
	private Map<String,ColDesc> colsDetail = new LinkedHashMap<String,ColDesc>();
	
	
	public TableDesc(String name,String remark){
		this.name = name.toUpperCase();
		this.metaName = name;
		
		this.humpName = StringKit.deCodeUnderlined(name);
		this.humpNameFU = StringKit.toUpperCaseFirstOne(this.humpName);
		
		this.remark = remark;
	}
	
	public boolean containCol(String col){
		return cols .contains(col.toUpperCase());
	}
	
	public void addCols(ColDesc col){
		colsDetail.put(col.colName, col);
		
		cols.add(col.colName.toUpperCase());
		metaCols.add(col.colName);
	}
	
	public ColDesc getColDesc(String name){
		return colsDetail.get(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.humpName = StringKit.deCodeUnderlined(name);
		this.humpNameFU = StringKit.toUpperCaseFirstOne(this.humpName);
	}

	public Set<String> getMetaCols() {
		return metaCols;
	}

	public void setMetaCols(Set<String> metaCols) {
		this.metaCols = metaCols;
	}

	public String getMetaName() {
		return metaName;
	}

	public void setMetaName(String metaName) {
		this.metaName = metaName;
		this.humpName = StringKit.deCodeUnderlined(metaName);
		this.humpNameFU = StringKit.toUpperCaseFirstOne(this.humpName);
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName.toUpperCase();
		this.metaIdName = idName;
	}

	public Set<String> getCols() {
		return cols;
	}

	public void setCols(Set<String> cols) {
		this.cols = cols;
	}

	public String getMetaIdName() {
		return metaIdName;
	}
	

	
	public String getRemark() {
		return remark;
	}

	public String getHumpName() {
		return humpName;
	}

	public String getHumpNameFU() {
		return humpNameFU;
	}
}
	
