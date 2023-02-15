package com.yhlo.oa.service.iml;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.mapper.SearchCustomerDetailMapper;
import com.yhlo.oa.mapper.SearchMaterDetailMapper;
import com.yhlo.oa.service.SearchCustomerDetailService;
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
public class SearchCustomerDetailServiceImpl implements SearchCustomerDetailService {

    @Resource
    private SearchCustomerDetailMapper mapper;


    @Override
    public List<It_Knb1VO> queryKnb1List(String kunnr) {
        return mapper.queryKnb1List(kunnr);
    }

    @Override
    public List<It_KnvvVO> queryKnvvList(String kunnr) {
        return mapper.queryKnvvList(kunnr);
    }

    @Override
    public List<It_KnvpVO> queryKnvpList(String kunnr) {
        return mapper.queryKnvpList(kunnr);
    }

    @Override
    public List<It_AdrcVO> queryAdrcList(String addrnumber) {
        return mapper.queryAdrcList(addrnumber);
    }

    @Override
    public List<It_Ztsd_012VO> queryZtsdList(String kunnr) {
        return mapper.queryZtsdList(kunnr);
    }
}
