package com.safedog.cloudnet.controller;

import com.safedog.cloudnet.dispose.model.ResultBody;
import com.safedog.common.redis.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Api-测试"}, description = "测试swagger类")
public class TestController {

    private final RedisUtils redisUtils;

    @Value("${demo}")
    private String userName;

    @GetMapping("test/{goodsName}")
    @ApiOperation(value="商品新增")
    //正常业务时， 需要在category类里或者server层进行事务控制，控制层一般不进行业务控制的。
    //@Transactional(rollbackFor = Exception.class)
    //@RequestParam 接收页面中的请求的参数
    @ResponseBody
    public String test(@PathVariable("goodsName") String goodsName){
        redisUtils.set("test", "test", 120);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        request.getSession().setAttribute("test", "123456");
        return userName + goodsName;
    }

    @GetMapping("testGet")
    @ResponseBody
    public ResultBody testGet(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String test = (String) request.getSession().getAttribute("test");
        return ResultBody.ofSuccess(test);
    }

    @GetMapping("testClear")
    @ResponseBody
    public void testClear(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
         request.getSession().removeAttribute("test");
    }

    @GetMapping("textException")
    @ResponseBody
    public void testException(){
        log.trace("这是track日志。。。");
        log.debug("这是debug日志。。。");
        //spring 默认设置的级别是info级别，没有指定级别的情况下，会使用spring默认的root级别（显示的是info级别的信息）
        log.info("这是info日志。。。");
        log.warn("这是warm日志。。。");
        log.error("这是error日志。。。");
    }
}
