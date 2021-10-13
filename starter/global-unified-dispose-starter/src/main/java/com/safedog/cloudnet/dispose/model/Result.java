package com.safedog.cloudnet.dispose.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.safedog.cloudnet.dispose.exception.error.CommonErrorCode;

import java.io.Serializable;

import static com.safedog.cloudnet.dispose.exception.error.CommonErrorCode.OK;

/**
 * 返回统一数据结构
 *
 */
public class Result<T> implements Serializable {

    private String requestId;
    /**
     * 服务器当前时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    /**
     * 成功数据
     */
    private T data;

    /**
     * 错误码
     */
    private String code = "0";

    /**
     * 错误描述
     */
    private String msg;

    @JSONField(serialize = false, deserialize = false)
    public boolean isOk() {
        return this.code.equals(OK.getCode());
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result() {
    }

    public Result(Long timestamp, T data, String code, String msg) {
        this.timestamp = timestamp;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static Result ofSuccess() {
        return ofSuccess(OK.getCode(), OK.getMessage(), null);
    }

    public static Result ofSuccess(Object data) {
        return ofSuccess(OK.getCode(), OK.getMessage(), data);
    }

    public static Result ofSuccess(String code, String msg, Object data) {
        Result result = new Result();
        result.setData(data);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result ofFail(String code, String msg) {
        return ofFail(code, msg, null);
    }

    public static Result ofFail(String code, String msg, Object data) {
        Result result = new Result();
        result.setData(data);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result ofFail(CommonErrorCode resultEnum) {
        return ofFail(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    /**
     * 获取 json
     * @return json
     */
    public String buildResultJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", this.requestId);
        jsonObject.put("code", this.code);
        jsonObject.put("timestamp", this.timestamp);
        jsonObject.put("msg", this.msg);
        jsonObject.put("data", this.data);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String toString() {
        return "Result{" +
                "requestId='" + requestId + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
