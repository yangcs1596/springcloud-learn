package com.safedog.common.mybatis.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类基础控制器
 */
public abstract  class BaseController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    //todo:把通用性的操作封装到父类里面
}
