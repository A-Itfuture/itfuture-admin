package com.itfuture.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itfuture.admin.entity.SysMenu;
import com.itfuture.admin.entity.SysRole;
import com.itfuture.admin.entity.SysUser;
import com.itfuture.admin.mapper.SysMenuMapper;
import com.itfuture.admin.mapper.SysRoleMapper;
import com.itfuture.admin.service.SysUserService;
import com.itfuture.admin.mapper.SysUserMapper;
import com.itfuture.admin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 王小虎
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-01-10 12:40:28
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        StringBuffer authority = new StringBuffer();
        //根据用户id获取所有角色
        List<SysRole> roleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>()
                .inSql("id", "SELECT role_id FROM sys_user_role WHERE user_id=" + userId));
        if(roleList.size()>0){
            String roleCodeStrs = roleList.stream().map(r -> "ROLE_" +
                    r.getCode()).collect(Collectors.joining(","));
            authority.append(roleCodeStrs);
        }

        // 遍历角色，获取所有菜单权限
        Set<String> menuCodeSet=new HashSet<String>();
        for(SysRole sysRole:roleList){
            List<SysMenu> sysMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                    .inSql("id", "SELECT menu_id FROM sys_role_menu WHERE role_id=" + sysRole.getId()));
            for(SysMenu sysMenu:sysMenuList){
                String perms=sysMenu.getPerms();
                if(StringUtil.isNotEmpty(perms)){
                    menuCodeSet.add(perms);
                }
            }
        }
        if(!menuCodeSet.isEmpty()){
            authority.append(",");
            String menuCodeStrs =
                    menuCodeSet.stream().collect(Collectors.joining(","));
            authority.append(menuCodeStrs);
        }
        System.out.println("authority:"+authority.toString());
        return authority.toString();
    }
}




