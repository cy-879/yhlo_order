package com.yhlo.oa.service.iml;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.mapper.GetSapConfigTableInfoMapper;
import com.yhlo.oa.service.GetSapConfigTableInfoService;
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
 * @ClassName: GetSapConfigTableInfoServiceImpl
 * @Description:
 * @date 2022/5/1914:51
 */
@Service
public class GetSapConfigTableInfoServiceImpl implements GetSapConfigTableInfoService {

    @Resource
    private GetSapConfigTableInfoMapper gtMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    //批处理
    @Transactional
    @Override
    public String insertT001(List<T001VO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTOO1();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTOO1(list.get(i));
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
    public List<T001VO> queryT001List() {
        return null;
    }


    //批处理
    @Transactional
    @Override
    public String insertTvko(List<TvkoVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvko();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvko(list.get(i));
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


    @Transactional
    @Override
    public String insertTvkot(List<TvkotVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvkot();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvkot(list.get(i));
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


    @Transactional
    @Override
    public String insertTvkwz(List<TvkwzVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvkwz();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvkwz(list.get(i));
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

    @Transactional
    @Override
    public String insertTvkbt(List<TvkbtVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvkbt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvkbt(list.get(i));
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


    @Transactional
    @Override
    public String insertTvgrt(List<TvgrtVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvgrt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvgrt(list.get(i));
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


    @Transactional
    @Override
    public String insertTvkbz(List<TvkbzVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvkbz();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvkbz(list.get(i));
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


    @Transactional
    @Override
    public String insertTvbvk(List<TvbvkVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvbvk();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvbvk(list.get(i));
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

    @Transactional
    @Override
    public String insertTvstt(List<TvsttVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvstt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvstt(list.get(i));
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


    @Transactional
    @Override
    public String insertTvtwt(List<TvtwtVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvtwt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvtwt(list.get(i));
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

    @Transactional
    @Override
    public String insertTspat(List<TspatVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTspat();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTspat(list.get(i));
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


    @Transactional
    @Override
    public String insertTvzbt(List<TvzbtVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvzbt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvzbt(list.get(i));
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


    @Transactional
    @Override
    public String insertT042zt(List<T042ztVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT042zt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT042zt(list.get(i));
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


    @Transactional
    @Override
    public String insertT001w(List<T001wVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT001w();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT001w(list.get(i));
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


    @Transactional
    @Override
    public String insertT001l(List<T001lVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT001l();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT001l(list.get(i));
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


    @Transactional
    @Override
    public String insertT024e(List<T024eVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT024e();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT024e(list.get(i));
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


    @Transactional
    @Override
    public String insertV_024(List<V_024VO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteV_024();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertV_024(list.get(i));
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


    @Transactional
    @Override
    public String insertT134t(List<T134tVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT134t();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT134t(list.get(i));
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


    @Transactional
    @Override
    public String insertTwewt(List<TwewtVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTwewt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTwewt(list.get(i));
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


    @Transactional
    public String insertT023t(List<T023tVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT023t();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT023t(list.get(i));
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


    @Transactional
    @Override
    public String insertT179(List<T179VO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT179();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT179(list.get(i));
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


    @Transactional
    @Override
    public String insertT005u(List<T005uVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT005u();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT005u(list.get(i));
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


    @Transactional
    @Override
    public String insertT077x(List<T077xVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT077x();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT077x(list.get(i));
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


    @Transactional
    @Override
    public String insertT171t(List<T171tVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT171t();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT171t(list.get(i));
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


    @Transactional
    @Override
    public String insertT189t(List<T189tVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT189t();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT189t(list.get(i));
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


    @Transactional
    @Override
    public String insertCskt(List<CsktVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteCskt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertCskt(list.get(i));
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


    @Transactional
    @Override
    public String insertT005t(List<T005tVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteT005t();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertT005t(list.get(i));
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


    @Transactional
    @Override
    public String insertTvsbt(List<TvsbtVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvsbt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvsbt(list.get(i));
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


    @Transactional
    @Override
    public String insertTvaut(List<TvautVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvaut();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvaut(list.get(i));
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

    @Transactional
    @Override
    public String insertTvagt(List<TvagtVO> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GetSapConfigTableInfoMapper mapper = session.getMapper(GetSapConfigTableInfoMapper.class);
        String rs = "ok，操作成功";
        try {
            gtMapper.deleteTvagt();//清空该表所有的数据
            for (int i = 0; i < list.size(); i++) {
                mapper.insertTvagt(list.get(i));
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
    public List<Item> getConfigItem() {
        return gtMapper.getConfigItem();
    }

}
