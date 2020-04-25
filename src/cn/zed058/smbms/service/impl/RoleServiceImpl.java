package cn.zed058.smbms.service.impl;

import cn.zed058.smbms.dao.RoleMapper;
import cn.zed058.smbms.pojo.Role;
import cn.zed058.smbms.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
}
