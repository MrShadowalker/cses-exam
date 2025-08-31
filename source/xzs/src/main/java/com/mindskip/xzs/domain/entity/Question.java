package com.mindskip.xzs.domain.entity;

import com.mindskip.xzs.domain.enums.SceneEnum;
import com.mindskip.xzs.domain.enums.SubjectEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 8826266720383164363L;

    private Integer id;

    /**
     * 服务对象
     * @see TargetTypeEnum#getCode()
     */
    private String targetType;

    /**
     * 环节
     * @see SubjectEnum#getCode()
     */
    private Integer subject;

    /**
     * 场景
     * @see SceneEnum#getCode()
     */
    private String scene;

    /**
     * 题目算分权重
     */
    private BigDecimal weight;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除状态
     */
    private Byte deleted;

}
