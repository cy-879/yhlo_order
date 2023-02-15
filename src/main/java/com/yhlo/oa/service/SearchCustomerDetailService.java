package com.yhlo.oa.service;

import com.yhlo.oa.entity.*;

import java.util.List;

public interface SearchCustomerDetailService {

    public List<It_Knb1VO> queryKnb1List(String kunnr);

    public List<It_KnvvVO> queryKnvvList(String kunnr);

    public List<It_KnvpVO> queryKnvpList(String kunnr);

    public List<It_AdrcVO> queryAdrcList(String addrnumber);

    public List<It_Ztsd_012VO> queryZtsdList(String kunnr);


}
