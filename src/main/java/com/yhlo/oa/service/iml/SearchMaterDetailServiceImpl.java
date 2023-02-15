package com.yhlo.oa.service.iml;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.mapper.SearchMaterDetailMapper;
import com.yhlo.oa.service.SearchMaterDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cy
 * @ClassName: GeneralDataServiceImpl
 * @Description:
 * @date 2022/6/19:16
 */


@Service
public class SearchMaterDetailServiceImpl implements SearchMaterDetailService {

    @Resource
    private SearchMaterDetailMapper mapper;


    @Override
    public List<It_MvkeVO> queryMvkeList(String matnr) {
        return mapper.queryMvkeList(matnr);
    }

    @Override
    public List<It_MbewVO> queryMbewList(String matnr) {
        return mapper.queryMbewList(matnr);
    }

    @Override
    public List<It_MarmVO> queryMarmList(String matnr) {
        return mapper.queryMarmList(matnr);
    }

    @Override
    public List<It_MardVO> queryMardList(String matnr) {
        return mapper.queryMardList(matnr);
    }

    @Override
    public List<It_MarcVO> queryMarcList(String matnr) {
        return mapper.queryMarcList(matnr);
    }
}
