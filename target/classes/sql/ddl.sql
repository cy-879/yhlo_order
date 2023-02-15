-- ----------------------------
-- 1、订单表
-- ----------------------------
drop table if exists order_info;
create table order_info (
  order_no    varchar(50)   not null comment '订单号',
  status      varchar(20)   comment '状态',
  crate_by    varchar(50)   comment '创建人',
  crate_time  datetime      comment '创建时间',
  primary key (order_no)
) engine=innodb comment = '订单表';
