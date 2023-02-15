package com.yhlo.oa.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.It_But000VO;
import com.yhlo.oa.entity.It_Kna1VO;
import com.yhlo.oa.entity.It_MaraVO;
import com.yhlo.oa.mapper.CustomerMasterDataMapper;
import com.yhlo.oa.mapper.MateriaMasterDataMapper;
import com.yhlo.oa.service.CustomerMasterDataService;
import com.yhlo.oa.service.MateriaMasterDataService;
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
public class CustomerMasterDataServiceImpl implements CustomerMasterDataService {

    @Resource
    private CustomerMasterDataMapper mapper;


    @Override
    public PageInfo<It_Kna1VO> getKna1PageList(Integer currentPage, Integer pageSize, String kunnr, String name1) {
        PageHelper.startPage(currentPage, pageSize);
        List<It_Kna1VO> it_maraVOS = mapper.queryKna1List(kunnr,name1);
        return new PageInfo<>(it_maraVOS);
    }

    @Override
    public List<It_Kna1VO> queryKna1List(String kunnr, String name1) {
        return mapper.queryKna1List(kunnr,name1);
    }
}
