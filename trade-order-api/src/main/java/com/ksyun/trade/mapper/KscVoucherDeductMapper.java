package com.ksyun.trade.mapper;

import com.ksyun.trade.pojo.KscVoucherDeduct;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【ksc_voucher_deduct】的数据库操作Mapper
* @createDate 2023-07-22 22:56:22
* @Entity com.ksyun.trade.pojo.KscVoucherDeduct
*/
@Mapper
public interface KscVoucherDeductMapper {

    //插入一条数据
    int insert(KscVoucherDeduct kscVoucherDeduct);

}
