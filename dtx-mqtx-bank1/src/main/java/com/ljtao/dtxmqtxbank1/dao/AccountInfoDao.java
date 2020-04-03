package com.ljtao.dtxmqtxbank1.dao;

import com.ljtao.dtxmqtxbank1.entity.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

public interface AccountInfoDao {
    //@Update("update account_info set account_balance =#{amount} where account_no=#{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo,@Param("amount") Double amount);

    //@Select("select id,account_name accountName from account_info where id=#{id}")
    List<AccountInfo> getById(int id);

    int  addTx(String txNo);

    int isExistTx(String txNo);
}
