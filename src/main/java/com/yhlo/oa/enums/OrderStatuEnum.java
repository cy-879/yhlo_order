package com.yhlo.oa.enums;

/**
 * 订单状态
 */
public enum OrderStatuEnum {



    /** ----------------------------------以下是订单审批状态----------------------------------- **/
    /** 已取消 */

    ORDER_STATU_CANCEL {public String getName(){return "已取消";}},

    /** 待审批 */

    ORDER_STATU_PENDING {public String getName(){return "待审批";}},

    /** 已审批 */

    ORDER_STATU_BUSINESS {public String getName(){return "已审批";}},

    /** 退回 */

    ORDER_STATU_RETURN {public String getName(){return "退回";}},




   /** ----------------------------------以下是返利审批状态----------------------------------- **/

    /** 保存 */

    REBATE_STATU_SUBMIT {public String getName(){return "保存";}},

    /** 待审核 */

    REBATE_STATU_WAITCONFIRM {public String getName(){return "待审核";}},

    /** 已审核 */

    REBATE_STATU_REVIEWED {public String getName(){return "已审核";}},

    /** 已审核 */

    REBATE_STATU_CANCEL {public String getName(){return "已取消";}},



    /** ----------------------------------以下是交货状态----------------------------------- **/

    /** 未交货 */
    DELIVERY_STATU_UNDELIVERED {public String getName(){return "未交货";}},

    /** 部分交货 */
    DELIVERY_STATU_PARTIAL {public String getName(){return "部分交货";}},

    /** 完全交货 */
    DELIVERY_STATU_COMPLETELY {public String getName(){return "完全交货";}},



    ;





    public abstract String getName();

}
