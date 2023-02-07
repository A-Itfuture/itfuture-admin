package com.itfuture.admin.service;

import com.itfuture.admin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 王小虎
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-01-10 12:40:28
*/
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);
}
