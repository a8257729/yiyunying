package com.ztesoft.mobile.v2.service.common;

import com.ztesoft.mobile.v2.core.Result;

/**
 * User: heisonyee
 * Date: 13-3-12
 * Time: 上午11:23
 */
public interface MobileMenuService {

    /** 根据员工ID、职位ID和系统类型获取授权的菜单分类 */
    public Result queryMenuCatalog(Long staffId, Long jobId, Long defaultJobId, Integer osType);

    /** 根据员工ID、职位ID和系统类型获取授权的二级菜单分类 */
    public Result querySubMenu(Long staffId, Long jobId, Long defaultJobId, Integer osType);

    public Result queryMenu(Long staffId, Long jobId, Long defaultJobId, Integer osType);

}
