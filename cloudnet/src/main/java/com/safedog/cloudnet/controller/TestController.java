package com.safedog.cloudnet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = {"Api-测试"}, description = "测试swagger类")
public class TestController {

    @GetMapping("test")
    @ApiOperation(value="商品新增")
    //正常业务时， 需要在category类里或者server层进行事务控制，控制层一般不进行业务控制的。
    //@Transactional(rollbackFor = Exception.class)
    //@RequestParam 接收页面中的请求的参数
    @ResponseBody
    public String test(@ApiParam(value = "用户a", required = true)
                       @RequestParam(value = "userName") String a){

        log.info("===={}", a);
        return "成功";
    }
}
