package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.TextContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TextContentMapper extends BaseMapper<TextContent> {


    List<TextContent> selectByIds(List<Integer> ids);

    int readAdd(Integer id);
}
