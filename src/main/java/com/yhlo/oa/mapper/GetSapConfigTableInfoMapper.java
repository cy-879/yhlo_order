package com.yhlo.oa.mapper;


import com.yhlo.oa.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetSapConfigTableInfoMapper {

   public List<Item> getConfigItem();

   public int insertTOO1(T001VO to);
   public void deleteTOO1();
   public int insertTOO1ByBatch(List<T001VO> itemList);
   public List<T001VO> queryT001List();


   public int insertTvko(TvkoVO tv);
   public void deleteTvko();

   public int insertTvkot(TvkotVO tv);
   public void deleteTvkot();

   public int insertTvkwz(TvkwzVO tv);
   public void deleteTvkwz();

   public int insertTvkbt(TvkbtVO tv);
   public void deleteTvkbt();

   public int insertTvgrt(TvgrtVO tv);
   public void deleteTvgrt();

   public int insertTvkbz(TvkbzVO tv);
   public void deleteTvkbz();

   public int insertTvbvk(TvbvkVO tv);
   public void deleteTvbvk();

   public int insertTvstt(TvsttVO tv);
   public void deleteTvstt();

   public int insertTvtwt(TvtwtVO tv);
   public void deleteTvtwt();

   public int insertTspat(TspatVO tv);
   public void deleteTspat();

   public int insertTvzbt(TvzbtVO tv);
   public void deleteTvzbt();

   public int insertT042zt(T042ztVO t0);
   public void deleteT042zt();

   public int insertT001w(T001wVO t0);
   public void deleteT001w();

   public int insertT001l(T001lVO t0);
   public void deleteT001l();

   public int insertT024e(T024eVO t0);
   public void deleteT024e();

   public int insertV_024(V_024VO t0);
   public void deleteV_024();

   public int insertT134t(T134tVO t0);
   public void deleteT134t();

   public int insertTwewt(TwewtVO t0);
   public void deleteTwewt();

   public int insertT023t(T023tVO t0);
   public void deleteT023t();

   public int insertT179(T179VO t0);
   public void deleteT179();

   public int insertT005u(T005uVO t0);
   public void deleteT005u();

   public int insertT077x(T077xVO t0);
   public void deleteT077x();

   public int insertT171t(T171tVO t1);
   public void deleteT171t();

   public int insertT189t(T189tVO t1);
   public void deleteT189t();

   public int insertCskt(CsktVO cs);
   public void deleteCskt();

   public int insertT005t(T005tVO t0);
   public void deleteT005t();

   public int insertTvsbt(TvsbtVO tv);
   public void deleteTvsbt();

   public int insertTvaut(TvautVO tv);
   public void deleteTvaut();

   public int insertTvagt(TvagtVO tv);
   public void deleteTvagt();


}
