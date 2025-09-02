package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.PainPoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PainPointMapper extends BaseMapper<PainPoint> {

    /**
     * 根据科目和等级查询对应痛点
     * @param subject
     * @param level
     * @return
     */
    List<PainPoint> selectBySubjectAndLevel(Integer subject, Integer level);

    List<PainPoint> selectByIds(List<Integer> ids);

    List<String> selectContentByIds(List<Integer> ids);

}
