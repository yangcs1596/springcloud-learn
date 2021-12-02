//package com.safedog.cloudnet.config.web;
//
//import cn.dev33.satoken.stp.StpInterface;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @program: fire
// * @description: 用户登录赋予相应权限
// * @author: fbl
// * @create: 2021-08-31 13:07
// *
// * https://mp.weixin.qq.com/s/9azf5U8a7i9rR7sNTioPbg
// **/
//@Component
//public class StpInterfaceImpl implements StpInterface {
////    @Autowired
////    UserMapper userMapper;
////
////    @Autowired
////    UserRoleMapper userRoleMapper;
////
////    @Autowired
////    RoleMapper roleMapper;
////
////    @Autowired
////    PermissionMapper permissionMapper;
////
////    @Autowired
////    RolePermissionMapper rolePermissionMapper;
//    @Override
//    public List<String> getPermissionList(Object userId, String s) {
////        // 用户存在，查找角色
////        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
////        userRoleQueryWrapper.eq("user_id", userId);
////        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
////
////        // 角色查找权限
////        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
////        rolePermissionQueryWrapper.in("role_id", userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
////        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);
////
////        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
////        permissionQueryWrapper.in("id", rolePermissions.stream().map(RolePermission::getPermissionId).distinct().collect(Collectors.toList()));
////        List<SysPermission> sysPermissions = permissionMapper.selectList(permissionQueryWrapper);
////
////        List<String> permissions = sysPermissions.stream().map(SysPermission::getCode).distinct().collect(Collectors.toList());
////        return permissions;
//        return null;
//
//    }
//
//    @Override
//    public List<String> getRoleList(Object userId, String s) {
////        // 用户存在，查找角色
////        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<UserRole>();
////        userRoleQueryWrapper.eq("user_id", userId);
////        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
////
////        // 查询角色
////        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<SysRole>().in("id", userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
////        List<SysRole> sysRoles = roleMapper.selectList(sysRoleQueryWrapper);
////        List<String> roleNames = sysRoles.stream().map(SysRole::getRoleName).distinct().collect(Collectors.toList());
////        return roleNames;
//        return null;
//    }
//}
