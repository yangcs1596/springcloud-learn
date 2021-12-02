package com.safedog.cloudnet.controller.sys;

import com.safedog.cloudnet.entity.mysql.SysUser;
import com.safedog.cloudnet.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycs
 * @description
 * @date 2021/10/27 15:07
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sysUser")
@Api(tags = {"Api-系统用户"}, description = "系统用户")
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("addSysUser")
    @ApiOperation(value="新增系统用户")
    @ResponseBody
    public String addSysUser(){
        SysUser sysUser = new SysUser();
        sysUser.setName("李四");
        return sysUserService.save(sysUser) ? "success" : "failure";
    }
}
