package com.yhlo.oa.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.It_MaraVO;
import com.yhlo.oa.mapper.MateriaMasterDataMapper;
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
public class MateriaMasterDataServiceImpl implements MateriaMasterDataService {

    @Resource
    private MateriaMasterDataMapper mapper;


    @Override
    public PageInfo<It_MaraVO> getMaraPageList(Integer currentPage, Integer pageSize,String matnr) {
        PageHelper.startPage(currentPage, pageSize);
        List<It_MaraVO> it_maraVOS = mapper.queryMaraList(matnr);
        return new PageInfo<>(it_maraVOS);
    }

    @Override
    public List<It_MaraVO> queryMaraList(String matnr) {
        return mapper.queryMaraList(matnr);
    }
}
