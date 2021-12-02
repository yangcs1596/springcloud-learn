//package com.safedog.cloudnet.controller.login;
//
//import cn.dev33.satoken.stp.StpUtil;
//import com.safedog.cloudnet.dispose.model.ResultBody;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author ycs
// * @description
// * @date 2021/10/26 20:55
// */
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@Api(tags = {"api-登录"}, description = "登录接口")
//public class LoginController {
//
//    /**
//     * 注意请求要改成post
//     * @return
//     */
//    @ApiOperation(value="登录用户")
//    @PostMapping("/login")
//    public String doLogin() {
////        // 从数据库中查询数据进行比对
////        String username = user.getUsername();
////        String password = user.getPassword();
////        Result<Boolean> result = userService.selectByUserNameAndPassword(username, password);
////        if (result.getData()) {
////            //当前用户登录
////            StpUtil.setLoginId(username);
////            Result<User> result1 = userService.selectByUserName(username);
////            List<Permission> permission = userDao.findPermissionByUserName(username);
////            User user1 = result1.getData();
////            //设置权限
////            user1.setAuthorities(permission);
////            //设置token值
////            user1.setSatoken(StpUtil.getTokenValue());
////            result1.setData(user1);
////            return result1;
////        }else {
////            return Result.failure(101,"登录失败");
////        }
//        StpUtil.login("张三");
//        StpUtil.getSession().set("auth", "张三");
//        return StpUtil.getTokenValueByLoginId(StpUtil.getLoginId());
//    }
//    @ApiOperation(value="登出")
//    @GetMapping("/logout")
//    public ResultBody<String> logout() {
//        StpUtil.logout();
//        return ResultBody.ofSuccess(
//                StpUtil.getLoginId().toString() + "登出成功");
//    }
//}
