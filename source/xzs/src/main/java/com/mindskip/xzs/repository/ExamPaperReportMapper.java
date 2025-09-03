package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.ExamPaperReport;
import com.mindskip.xzs.domain.param.ExamPaperReportParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamPaperReportMapper extends BaseMapper<ExamPaperReport> {

    /**
     * 根据参数查询考试报告
     * @param examPaperReportParam
     * @return
     */
    ExamPaperReport selectByParams(ExamPaperReportParam examPaperReportParam);

}
