package com.yhlo.oa.service;


import com.yhlo.oa.entity.*;

import java.util.List;

public interface GetSapConfigTableInfoService {

    public List<Item> getConfigItem();

    public String insertT001(List<T001VO> list);
    public List<T001VO> queryT001List();


    public String insertTvko(List<TvkoVO> list);

    public String insertTvkot(List<TvkotVO> list);

    public String insertTvkwz(List<TvkwzVO> list);

    public String insertTvkbt(List<TvkbtVO> list);

    public String insertTvgrt(List<TvgrtVO> list);

    public String insertTvkbz(List<TvkbzVO> list);

    public String insertTvbvk(List<TvbvkVO> list);

    public String insertTvstt(List<TvsttVO> list);

    public String insertTvtwt(List<TvtwtVO> list);

    public String insertTspat(List<TspatVO> list);

    public String insertTvzbt(List<TvzbtVO> list);

    public String insertT042zt(List<T042ztVO> list);

    public String insertT001w(List<T001wVO> list);

    public String insertT001l(List<T001lVO> list);

    public String insertT024e(List<T024eVO> list);

    public String insertV_024(List<V_024VO> list);

    public String insertT134t(List<T134tVO> list);

    public String insertTwewt(List<TwewtVO> list);

    public String insertT023t(List<T023tVO> list);

    public String insertT179(List<T179VO> list);

    public String insertT005u(List<T005uVO> list);

    public String insertT077x(List<T077xVO> list);

    public String insertT171t(List<T171tVO> list);

    public String insertT189t(List<T189tVO> list);

    public String insertCskt(List<CsktVO> list);

    public String insertT005t(List<T005tVO> list);

    public String insertTvsbt(List<TvsbtVO> list);

    public String insertTvaut(List<TvautVO> list);

    public String insertTvagt(List<TvagtVO> list);

    public String insertT142t(List<T142tVO> list);

}
