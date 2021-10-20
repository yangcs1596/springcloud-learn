package com.safedog.cloudnet.controller;

import com.safedog.cloudnet.dispose.model.ResultBody;
import com.safedog.common.redis.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("test")
    @ApiOperation(value="商品新增")
    //正常业务时， 需要在category类里或者server层进行事务控制，控制层一般不进行业务控制的。
    //@Transactional(rollbackFor = Exception.class)
    //@RequestParam 接收页面中的请求的参数
    @ResponseBody
    public String test(){
        redisUtils.set("test", "test", 120);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        request.getSession().setAttribute("test", "123456");
        return userName;
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
}
