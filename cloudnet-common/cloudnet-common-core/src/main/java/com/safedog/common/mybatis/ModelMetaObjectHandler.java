package com.safedog.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.safedog.common.identity.IdGenerator;
import com.safedog.common.web.RequestContext;
import com.safedog.common.web.RequestInfo;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

import static com.safedog.common.mybatis.entity.AbstractEntity.*;

/**
 * 自动填充模型数据
 *
 * @author liuyadu
 */
public class ModelMetaObjectHandler implements MetaObjectHandler {

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
