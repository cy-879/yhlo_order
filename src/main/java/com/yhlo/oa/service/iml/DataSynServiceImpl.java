package com.yhlo.oa.service.iml;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.mapper.DataSynMapper;
import com.yhlo.oa.service.DataSynService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cy
 * @ClassName: It_MvkeServiceImpl
 * @Description:
 * @date 2022/5/2417:28
 */

@Service
public class DataSynServiceImpl implements DataSynService {

    @Resource
    private DataSynMapper dataMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    /**
     * @Author cy
     * @Description 获取同步参数列表
     * * @param 同步数据类型
     * @Return
     * @Date 2022/5/26 10:55
     */
    @Override
    public List<Item> getConfigItem(String ttype) {
        return dataMapper.getConfigItem(ttype);
    }

    /**
     * @Author cy
     * @Description 插入物料基本视图数据前，先查是否在本地数据库存在，如果存在就覆盖，每400条数据提交一次
     * @Return
     * @Date 2022/5/26 10:53
     */
    //批处理
    @Transactional
    @Override
    public String insertMara(List<It_MaraVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_MaraVO> maList= queryMaraListByParam(list.get(i));
                if(maList.size()>0){//表示数据库中已经存在了
                    for (It_MaraVO ma : maList) {
                        deleteMara(ma);//删除已有的
                    }
                }

                mapper.insertMara(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }


    @Override
    public String deleteMara(It_MaraVO im) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteMara(im);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }


    /**
     * @Author cy
     * @Description 通过指定条件查寻该物料基本视图接口的数据是否在本地数据库存在
     * @Date 2022/5/26 10:52
     */
    @Override
    public List<It_MaraVO> queryMaraListByParam(It_MaraVO im) {
        return dataMapper.queryMaraListByParam(im);
    }


    /**
     * @Author cy
     * @Description 通过唯一键和最后修改日期去查该物料基本视图接口的数据是否在本地数据库存在
     * * @param null
     * @Return
     * @Date 2022/5/26 10:56
     */
    @Override
    public List<It_MaraVO> checkMaraExist(It_MaraVO im) {
        return dataMapper.checkMaraExist(im);
    }


    /**
     * @Author cy
     * @Description 插入销售物料视图数据前，先查是否在本地数据库存在，如果存在就覆盖，每400条数据提交一次
     * @Return
     * @Date 2022/5/26 10:53
     */
    //批处理
    @Transactional
    @Override
    public String insertMvke(List<It_MvkeVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_MvkeVO> mvList= queryMvkeListByParam(list.get(i));
                if(mvList.size()>0){//表示数据库中已经存在了
                    for (It_MvkeVO mv : mvList) {
                        deleteMvke(mv);//删除已有的
                    }
                }

                mapper.insertMvke(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * @Author cy
     * @Description 查询数据列表
     * * @param null
     * @Return
     * @Date 2022/5/26 10:56
     */
    @Override
    public List<It_MvkeVO> queryMvkeList() {
        return dataMapper.queryMvkeList();
    }


    @Override
    public String deleteMvke(It_MvkeVO im) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteMvke(im);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * @Author cy
     * @Description 通过指定条件查寻该数据是否在本地数据库存在
     * @Date 2022/5/26 10:52
     */
    @Override
    public List<It_MvkeVO> queryMvkeListByParam(It_MvkeVO im) {
        return dataMapper.queryMvkeListByParam(im);
    }

    /**
     * @Author cy
     * @Description 通过唯一键和最后修改日期去查是否在本地数据库存在
     * * @param null
     * @Return
     * @Date 2022/5/26 10:56
     */
    @Override
    public List<It_MvkeVO> checkExist(It_MvkeVO im) {
        return dataMapper.checkExist(im);
    }


    /**
     * @Author cy
     * @Description 插入物料评估类型数据前，先查是否在本地数据库存在，如果存在就覆盖，每400条数据提交一次
     * @Return
     * @Date 2022/5/26 10:53
     */
    //批处理
    @Transactional
    @Override
    public String insertMbew(List<It_MbewVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_MbewVO> mbList = queryMbewListByParam(list.get(i));
                if(mbList.size()>0){//表示数据库中已经存在了
                    for (It_MbewVO mb : mbList) {
                        deleteMbew(mb);//删除已有的
                    }
                }

                mapper.insertMbew(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteMbew(It_MbewVO im) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteMbew(im);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_MbewVO> queryMbewListByParam(It_MbewVO im) {
        return dataMapper.queryMbewListByParam(im);
    }

    @Override
    public List<It_MbewVO> checkMbewExist(It_MbewVO im) {
        return dataMapper.checkMbewExist(im);
    }


    @Transactional
    @Override
    public String insertMarm(List<It_MarmVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_MarmVO> maList = queryMarmListByParam(list.get(i));
                if(maList.size()>0){//表示数据库中已经存在了
                    for (It_MarmVO ma : maList) {
                        deleteMarm(ma);//删除已有的
                    }
                }

                mapper.insertMarm(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteMarm(It_MarmVO im) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteMarm(im);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_MarmVO> queryMarmListByParam(It_MarmVO im) {
        return dataMapper.queryMarmListByParam(im);
    }

    @Override
    public List<It_MarmVO> checkMarmExist(It_MarmVO im) {
        return dataMapper.checkMarmExist(im);
    }


    @Transactional
    @Override
    public String insertMard(List<It_MardVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_MardVO> maList = queryMardListByParam(list.get(i));
                if(maList.size()>0){//表示数据库中已经存在了
                    for (It_MardVO ma : maList) {
                        deleteMard(ma);//删除已有的
                    }
                }

                mapper.insertMard(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteMard(It_MardVO im) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteMard(im);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_MardVO> queryMardListByParam(It_MardVO im) {
        return dataMapper.queryMardListByParam(im);
    }

    @Override
    public List<It_MardVO> checkMardExist(It_MardVO im) {
        return dataMapper.checkMardExist(im);
    }


    @Transactional
    @Override
    public String insertMarc(List<It_MarcVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_MarcVO> maList = queryMarcListByParam(list.get(i));
                if(maList.size()>0){//表示数据库中已经存在了
                    for (It_MarcVO ma : maList) {
                        deleteMarc(ma);//删除已有的
                    }
                }

                mapper.insertMarc(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteMarc(It_MarcVO im) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteMarc(im);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_MarcVO> queryMarcListByParam(It_MarcVO im) {
        return dataMapper.queryMarcListByParam(im);
    }

    @Override
    public List<It_MarcVO> checkMarcExist(It_MarcVO im) {
        return dataMapper.checkMarcExist(im);
    }


    @Transactional
    @Override
    public String insertBut000(List<It_But000VO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_But000VO> ibList = queryBut000ListByParam(list.get(i));
                if(ibList.size()>0){//表示数据库中已经存在了
                    for (It_But000VO ib : ibList) {
                        deleteBut000(ib);//删除已有的
                    }
                }

                mapper.insertBut000(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteBut000(It_But000VO ib) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteBut000(ib);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_But000VO> queryBut000ListByParam(It_But000VO ib) {
        return dataMapper.queryBut000ListByParam(ib);
    }

    @Override
    public List<It_But000VO> checkBut000Exist(It_But000VO ib) {
        return dataMapper.checkBut000Exist(ib);
    }


    @Transactional
    @Override
    public String insertKna1(List<It_Kna1VO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_Kna1VO> ikList = queryKna1ListByParam(list.get(i));
                if(ikList.size()>0){//表示数据库中已经存在了
                    for (It_Kna1VO ik : ikList) {
                        deleteKna1(ik);//删除已有的
                    }
                }

                mapper.insertKna1(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteKna1(It_Kna1VO ik) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteKna1(ik);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_Kna1VO> queryKna1ListByParam(It_Kna1VO ik) {
        return dataMapper.queryKna1ListByParam(ik);
    }

    @Override
    public List<It_Kna1VO> checkKna1Exist(It_Kna1VO ik) {
        return dataMapper.checkKna1Exist(ik);
    }



    @Transactional
    @Override
    public String insertKnb1(List<It_Knb1VO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_Knb1VO> ikList = queryKnb1ListByParam(list.get(i));
                if(ikList.size()>0){//表示数据库中已经存在了
                    for (It_Knb1VO ik : ikList) {
                        deleteKnb1(ik);//删除已有的
                    }
                }

                mapper.insertKnb1(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteKnb1(It_Knb1VO ik) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteKnb1(ik);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_Knb1VO> queryKnb1ListByParam(It_Knb1VO ik) {
        return dataMapper.queryKnb1ListByParam(ik);
    }

    @Override
    public List<It_Knb1VO> checkKnb1Exist(It_Knb1VO ik) {
        return dataMapper.checkKnb1Exist(ik);
    }


    @Transactional
    @Override
    public String insertKnvv(List<It_KnvvVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_KnvvVO> ikList = queryKnvvListByParam(list.get(i));
                if(ikList.size()>0){//表示数据库中已经存在了
                    for (It_KnvvVO ik : ikList) {
                        deleteKnvv(ik);//删除已有的
                    }
                }

                mapper.insertKnvv(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteKnvv(It_KnvvVO ik) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteKnvv(ik);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_KnvvVO> queryKnvvListByParam(It_KnvvVO ik) {
        return dataMapper.queryKnvvListByParam(ik);
    }

    @Override
    public List<It_KnvvVO> checkKnvvExist(It_KnvvVO ik) {
        return dataMapper.checkKnvvExist(ik);
    }


    @Transactional
    @Override
    public String insertKnvp(List<It_KnvpVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_KnvpVO> ikList = queryKnvpListByParam(list.get(i));
                if(ikList.size()>0){//表示数据库中已经存在了
                    for (It_KnvpVO ik : ikList) {
                        deleteKnvp(ik);//删除已有的
                    }
                }

                mapper.insertKnvp(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteKnvp(It_KnvpVO ik) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteKnvp(ik);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_KnvpVO> queryKnvpListByParam(It_KnvpVO ik) {
        return dataMapper.queryKnvpListByParam(ik);
    }

    @Override
    public List<It_KnvpVO> checkKnvpExist(It_KnvpVO ik) {
        return dataMapper.checkKnvpExist(ik);
    }


    @Transactional
    @Override
    public String insertAdrc(List<It_AdrcVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_AdrcVO> iaList = queryAdrcListByParam(list.get(i));
                if(iaList.size()>0){//表示数据库中已经存在了
                    for (It_AdrcVO ia : iaList) {
                        deleteAdrc(ia);//删除已有的
                    }
                }

                mapper.insertAdrc(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteAdrc(It_AdrcVO ia) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteAdrc(ia);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_AdrcVO> queryAdrcListByParam(It_AdrcVO ia) {
        return dataMapper.queryAdrcListByParam(ia);
    }

    @Override
    public List<It_AdrcVO> checkAdrcExist(It_AdrcVO ia) {
        return dataMapper.checkAdrcExist(ia);
    }


    @Transactional
    @Override
    public String insertZtsd_012(List<It_Ztsd_012VO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DataSynMapper mapper = session.getMapper(DataSynMapper.class);
        String rs = "ok，操作成功";
        try {

            for (int i = 0; i < list.size(); i++) {
                List<It_Ztsd_012VO> izList = queryZtsd_012ListByParam(list.get(i));
                if(izList.size()>0){//表示数据库中已经存在了
                    for (It_Ztsd_012VO iz : izList) {
                        deleteZtsd_012(iz);//删除已有的
                    }
                }

                mapper.insertZtsd_012(list.get(i));
                if (i % 400 == 0 || i == list.size() - 1) {
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public String deleteZtsd_012(It_Ztsd_012VO iz) {
        String rs = "ok，操作成功";
        try {
            dataMapper.deleteZtsd_012(iz);
        }catch (Exception e){
            rs = "error,操作失败"+e.getMessage().toString();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<It_Ztsd_012VO> queryZtsd_012ListByParam(It_Ztsd_012VO iz) {
        return dataMapper.queryZtsd_012ListByParam(iz);
    }

    @Override
    public List<It_Ztsd_012VO> checkZtsd_012Exist(It_Ztsd_012VO iz) {
        return dataMapper.checkZtsd_012Exist(iz);
    }


}
