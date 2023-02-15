package com.yhlo.oa.service;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.It_But000VO;
import com.yhlo.oa.entity.It_Kna1VO;
import com.yhlo.oa.entity.It_MaraVO;

import java.util.List;

public interface CustomerMasterDataService {

    public PageInfo<It_Kna1VO> getKna1PageList(Integer currentPage, Integer pageSize, String kunnr, String name1);

    public List<It_Kna1VO> queryKna1List(String kunnr, String name1);
}
