package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.enums.ExamPaperVersionEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mindskip.xzs.domain.enums.TargetTypeEnum.TYB;

public class QuestionSelectionStrategy {
    /**
     * 根据试卷类型获取对应的目标类型列表
     * <p>
     * 在试卷生成服务中使用
     * QuestionSelectionStrategy strategy = new QuestionSelectionStrategy();
     * List<TargetTypeEnum> targetTypes = strategy.getTargetTypesByPaperVersion(ExamPaperVersionEnum.BZB);
     * 然后根据targetTypes从数据库查询对应题目
     */
    public List<TargetTypeEnum> getTargetTypesByPaperVersion(ExamPaperVersionEnum paperType) {
        List<TargetTypeEnum> targetTypes = new ArrayList<>();
        switch (paperType) {
            case EXPERIENCE:
                targetTypes.add(TYB);
                break;
            case STANDARD:
                targetTypes.addAll(Arrays.asList(TargetTypeEnum.BZB, TargetTypeEnum.CY, TargetTypeEnum.GG));
                break;
            case TEENAGER:
                targetTypes.add(TargetTypeEnum.QSN);
                break;
            case CHILD:
                targetTypes.add(TargetTypeEnum.ET);
                break;
            default:
                throw new IllegalArgumentException("不支持的试卷类型: " + paperType);
        }
        return targetTypes;
    }
}