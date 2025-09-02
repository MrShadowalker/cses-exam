package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.Rank;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RankMapper extends BaseMapper<Rank> {

    /**
     * 根据组合hash查询排名信息
     * @param combinationHash
     */
    Rank selectByCombinationHash(String combinationHash);

    /**
     * 查询排序总量
     */
    Integer selectTotalRank();
}
