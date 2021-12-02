package com.safedog.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author ycs
 * @description
 * @date 2021/8/27 11:46
 */
@Data
public abstract class AbstractEntity extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ID = "id";
    public static final String SORT_NUM = "sortNum";
    public static final String CREATE_APP_ID = "createdAppId";
    public static final String CREATE_USER_ID = "createdUser";
    public static final String CREATE_DATE = "createdDate";
    public static final String UPDATE_APP_ID = "updatedAppId";
    public static final String UPDATE_USER_ID = "updatedUser";
    public static final String UPDATE_DATE = "updatedDate";

    @TableId(type = IdType.INPUT)
    private String id;

    public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createdUser;


    /**
     * 排序
     */
    @TableField(fill = FieldFill.INSERT)
    protected Long sortNum;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createdDate;

    /**
     * 创建APPid
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createdAppId;


    /**
     * 最后更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    protected String updatedUser;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    protected Date updatedDate;


    /**
     * 最后更新appId
     */
    @TableField(fill = FieldFill.UPDATE)
    protected String updatedAppId;

    /**
     * 删除标记
     */
    @TableLogic(value = "0", delval = "1")
    protected boolean deleted = false;

    /**
     * 删除人
     */
    @Deprecated
    @TableField(select = false)
    protected String deletedUser;

    /**
     * 删除时间
     */
    @Deprecated
    @TableField(select = false)
    protected Date deletedDate;

    /**
     * 备注记录
     */
    @Deprecated
    @TableField(select = false)
    protected String logRecord;

    /**
     * 版本号
     */
    @Version
    protected long recordVersion = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
