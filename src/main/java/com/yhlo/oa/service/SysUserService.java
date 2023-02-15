package com.yhlo.oa.service;

import java.util.List;
import com.yhlo.oa.entity.SysUser;
import com.yhlo.oa.entity.SysUserRole;

/**
 * 用户 业务层
 *
 * @author ruoyi
 */
public interface SysUserService
{
    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);




    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);



    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteUserByIds(String ids);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 保存亚辉龙用户信息
     * @param user 用户信息
     * @return 结果
     */
    public String addYhloUser(SysUser user);

    /**
     * 修改亚辉龙用户信息
     * @param user 用户信息
     * @return 结果
     */
    public String updateYhloUser(SysUser user);

    /**
     * @Author cy
     * @Description 获取用户列表
     * @Return
     * @Date 2022/12/14 14:29
     */
    public List<SysUser> getUserList();

    /**
     * @Author cy
     * @Description 校验用于是否存在
     * @Return
     * @Date 2022/12/14 16:14
     */
    public List<SysUser> checkUserExists(String loginName);






    /**
     * 修改用户密码信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int resetUserPwd(SysUser user);

    /**
     * 修改用户详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserInfo(SysUser user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);





    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);



    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserRoleGroup(Long userId);



    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    public int changeStatus(SysUser user);
}
