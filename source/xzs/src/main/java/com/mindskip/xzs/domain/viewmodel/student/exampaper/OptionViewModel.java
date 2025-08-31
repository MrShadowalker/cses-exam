package com.mindskip.xzs.domain.viewmodel.student.exampaper;

import lombok.Data;

/**
 * @author Shadowalker
 */
@Data
public class OptionViewModel {

    /**
     * 选项ID
     */
    private Integer optionId;

    /**
     * 选项编号
     * 需要前端映射为选项前缀
     */
    private Integer order;

    /**
     * 选项内容
     */
    private String content;

}
