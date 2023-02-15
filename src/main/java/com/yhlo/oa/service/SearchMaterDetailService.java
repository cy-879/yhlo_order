package com.yhlo.oa.service;

import com.yhlo.oa.entity.*;

import java.util.List;

public interface SearchMaterDetailService {

    public List<It_MvkeVO> queryMvkeList(String matnr);

    public List<It_MbewVO> queryMbewList(String matnr);

    public List<It_MarmVO> queryMarmList(String matnr);

    public List<It_MardVO> queryMardList(String matnr);

    public List<It_MarcVO> queryMarcList(String matnr);


}
