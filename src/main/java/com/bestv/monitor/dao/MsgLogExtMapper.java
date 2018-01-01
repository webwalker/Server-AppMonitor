package com.bestv.monitor.dao;

import com.bestv.monitor.model.MsgLogExt;

public interface MsgLogExtMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(MsgLogExt record);

    int insertSelective(MsgLogExt record);

    MsgLogExt selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(MsgLogExt record);

    int updateByPrimaryKeyWithBLOBs(MsgLogExt record);

    int updateByPrimaryKey(MsgLogExt record);
}