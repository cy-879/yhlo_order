package com.yhlo.oa.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.mapper.DataSynMapper;
import com.yhlo.oa.mapper.PublicDataMapper;
import com.yhlo.oa.mapper.RebatePolicyMapper;
import com.yhlo.oa.service.RebatePolicyService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @author cy
 * @ClassName: RebatePolicyService
 * @Description:
 * @date 2022/7/1814:06
 */

@Service
public class RebatePolicyServiceImpl implements RebatePolicyService {

    @Autowired
    private RebatePolicyMapper reMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertRebatePolicy(RebatePolicyVO rebatePolicyVO , List<RebatePolicyDetailVO> detailVOList ) {

        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        RebatePolicyMapper mapper = session.getMapper(RebatePolicyMapper.class);

        String rs = "ok，操作成功";
        try {
            reMapper.insertRebatePolicy(rebatePolicyVO);
            for (int i = 0; i < detailVOList.size(); i++) {
                mapper.insertRebatePolicyDetail(detailVOList.get(i));
                if (i % 50 == 0 || i == detailVOList.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }

    @Override
    public List<RebatePolicyVO> checkRebateNoExists(String rebateFormNo) {
        return reMapper.checkRebateNoExists(rebateFormNo);
    }

    @Override
    public List<RebatePolicyVO> queryRebatePolicyList(String rebateFormName,String name1,String status) {
        return reMapper.queryRebatePolicyList(rebateFormName,name1,status);
    }

    @Override
    public PageInfo<RebatePolicyVO> getRebatePolicyPageInfoList(Integer currentPage, Integer pageSize, String rebateFormName,String name1,String status) {
        PageHelper.startPage(currentPage, pageSize);
        List<RebatePolicyVO> list = reMapper.queryRebatePolicyList(rebateFormName,name1,status);
        return new PageInfo<>(list);
    }


    @Override
    public List<RebatePolicyVO> queryRebatePolicyToBeApprovedList(String rebateFormName, String name1) {
        return reMapper.queryRebatePolicyToBeApprovedList(rebateFormName,name1);
    }

    @Override
    public PageInfo<RebatePolicyVO> getRebatePolicyToBeApprovedList(Integer currentPage, Integer pageSize, String rebateFormName, String name1) {
        PageHelper.startPage(currentPage, pageSize);
        List<RebatePolicyVO> list = reMapper.queryRebatePolicyToBeApprovedList(rebateFormName,name1);
        return new PageInfo<>(list);
    }

    @Override
    public List<RebatePolicyDetailVO> queryRebateDetailList(String rebateFormNo) {
        return reMapper.getRebatePolicyDetailList(rebateFormNo);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String submitForApproval(List<RebatePolicyVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        RebatePolicyMapper mapper = session.getMapper(RebatePolicyMapper.class);
        String rs = "ok，操作成功";
        try {
            for (int i = 0; i < list.size(); i++) {
                mapper.submitForApproval(list.get(i));
                if (i % 50 == 0 || i == list.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateApprovalStatu(List<RebatePolicyVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        RebatePolicyMapper mapper = session.getMapper(RebatePolicyMapper.class);
        String rs = "ok，操作成功";
        try {
            for (int i = 0; i < list.size(); i++) {
                mapper.updateApprovalStatu(list.get(i));
                if (i % 50 == 0 || i == list.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }
}
