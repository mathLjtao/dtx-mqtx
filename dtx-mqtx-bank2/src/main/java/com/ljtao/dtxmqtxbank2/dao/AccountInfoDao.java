package com.ljtao.dtxmqtxbank2.dao;

import com.ljtao.dtxmqtxbank2.entity.AccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountInfoDao {
    //@Update("update account_info set account_balance =#{amount} where account_no=#{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);

    //@Select("select id,account_name accountName from account_info where id=#{id}")
    List<AccountInfo> getById(int id);

    int  addTx(String txNo);

    int isExistTx(String txNo);

}
