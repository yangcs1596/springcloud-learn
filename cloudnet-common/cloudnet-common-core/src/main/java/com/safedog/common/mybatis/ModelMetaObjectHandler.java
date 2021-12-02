package com.safedog.common.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.safedog.common.identity.IdGenerator;
import com.safedog.common.web.RequestContext;
import com.safedog.common.web.RequestInfo;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static com.safedog.common.mybatis.entity.AbstractEntity.*;

/**
 * 自动填充模型数据
 *
 * @author liuyadu
 */
public class ModelMetaObjectHandler implements MetaObjectHandler {

    /**
     * 只根据是否是哪个注解
     * @param fieldName
     * @param fieldVal
     * @param metaObject
     * @param fieldFill
     * @return
     */
    @Override
    public boolean isFill(String fieldName, Object fieldVal, MetaObject metaObject, FieldFill fieldFill) {
        TableInfo tableInfo = metaObject.hasGetter("MP_OPTLOCK_ET_ORIGINAL") ? TableInfoHelper.getTableInfo(metaObject.getValue("MP_OPTLOCK_ET_ORIGINAL").getClass()) : TableInfoHelper.getTableInfo(metaObject.getOriginalObject().getClass());
        if (Objects.nonNull(tableInfo)) {
            Optional<TableFieldInfo> first = tableInfo.getFieldList().stream().filter((e) -> {
                return e.getProperty().equals(fieldName) && e.getPropertyType().isAssignableFrom(fieldVal.getClass());
            }).findFirst();
            if (first.isPresent()) {
                FieldFill fill = ((TableFieldInfo)first.get()).getFieldFill();
                return fill.equals(fieldFill) || FieldFill.INSERT_UPDATE.equals(fill);
            }
        }

        return false;
    }


    private final IdGenerator idGenerator;

    public ModelMetaObjectHandler(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public void insertFill(MetaObject metaObject) {

        Object id = getFieldValByName(ID, metaObject);
        if (id == null || "".equals(id)) {
            id = String.valueOf(idGenerator.nextId());
            setFieldValByName(ID, id, metaObject);
        }

        if (metaObject.hasSetter(SORT_NUM)) {
            setFieldValByName(SORT_NUM, Long.valueOf(0L), metaObject);
        }

        RequestInfo requestInfo = RequestContext.getRequestInfo();

        Object createdAppId = getFieldValByName(CREATE_APP_ID, metaObject);
        if (createdAppId == null) {
            setInsertFieldValByName(CREATE_APP_ID, requestInfo.getApplicationId(), metaObject);
        }

        Object createdByUser = getFieldValByName(CREATE_USER_ID, metaObject);
        if (createdByUser == null) {
            setInsertFieldValByName(CREATE_USER_ID, requestInfo.getUserId(), metaObject);
        }

        Object createdDate = getFieldValByName(CREATE_DATE, metaObject);
        if (createdDate == null) {
            setInsertFieldValByName(CREATE_DATE, new Date(), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        RequestInfo requestInfo = RequestContext.getRequestInfo();
        setUpdateFieldValByName(UPDATE_APP_ID, requestInfo.getApplicationId(), metaObject);
        setUpdateFieldValByName(UPDATE_USER_ID, requestInfo.getUserId(), metaObject);
        setUpdateFieldValByName(UPDATE_DATE, new Date(), metaObject);
    }

}
