package com.safedog.cloudnet.generate.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("列描述")
public class ColDesc {
	@ApiModelProperty("列名")
	public String colName;
	@ApiModelProperty("列驼峰名")
	public String colHumpName;
	@ApiModelProperty("列驼峰 首字母大写")
	public String colHumpNameFU;
	@ApiModelProperty("字段类型")
	public int sqlType;
	@ApiModelProperty("大小")
	public Integer size;
	@ApiModelProperty("数字")
	public Integer digit;
	@ApiModelProperty("备注")
	public String remark ;

	public ColDesc(String colName,int sqlType,Integer size,Integer digit,String remark){
		this.colName = colName;
		this.colHumpName = StringKit.deCodeUnderlined(colName);
		this.colHumpNameFU = StringKit.toUpperCaseFirstOne(this.colHumpName);
		this.sqlType = sqlType;
		this.size = size;
		this.digit = digit;
		this.remark = remark;
		
	}

}
