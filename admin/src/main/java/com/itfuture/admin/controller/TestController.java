package com.itfuture.admin.controller;

import com.itfuture.admin.entity.R;
import com.itfuture.admin.entity.SysUser;
import com.itfuture.admin.service.SysUserService;
import com.itfuture.admin.util.JwtUtils;
import com.itfuture.admin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2023/01/10 13:56
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private SysUserService  sysUserService;

    @GetMapping("/user/list")
    // @PreAuthorize("hasAnyAuthority('system:user:list')")//判断权限
    @PreAuthorize("hasRole('ROLE_common')")//判断角色
    public R userList(@RequestHeader(required = false) String token){
        if(StringUtil.isNotEmpty(token)){
            Map<String, Object> resultMap = new HashMap<>();
            List<SysUser> userList = sysUserService.list();
            resultMap.put("userList",userList);
            return R.ok(resultMap);
        }else {
            return R.error(401,"没有权限访问！");
        }
    }

    @GetMapping("/login")
    public R login(){
        String token = JwtUtils.genJwtToken("itfuture");
        return R.ok().put("token",token);
    }
}
