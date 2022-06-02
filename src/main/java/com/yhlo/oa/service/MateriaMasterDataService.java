package com.yhlo.oa.service;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.It_MaraVO;

import java.util.List;

public interface MateriaMasterDataService {

    public PageInfo<It_MaraVO> geteMaraPageList(Integer currentPage, Integer pageSize,String matnr);
    public List<It_MaraVO> queryMaraList(String matnr);
}
