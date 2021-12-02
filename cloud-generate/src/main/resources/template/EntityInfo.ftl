
package com.notarycloud.provider.${modelName}.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.notarycloud.common.mybatis.base.entity.AbstractEntity;
import com.notarycloud.common.utils.ModelBeanTransform;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModelProperty;
<#list attrs as attr>
        <#if attr.type == 'Date'>
import java.util.Date;
            <#break>
        </#if>
</#list>
<#list attrs as attr>
    <#if attr.type == 'boolean'>
import com.baomidou.mybatisplus.annotation.TableField;
        <#break>
    </#if>
</#list>



@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${tableName}")
public class ${tableNameFU} extends AbstractEntity implements ModelBeanTransform {

<#list attrs as attr>
    @ApiModelProperty(value = "${attr.comment}")
    private ${attr.type} ${attr.humpName};
</#list>

}