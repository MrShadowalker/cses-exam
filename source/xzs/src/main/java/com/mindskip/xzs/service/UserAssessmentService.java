package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.entity.UserAssessment;
import com.mindskip.xzs.domain.enums.AssessmentTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * 用户测评服务接口
 */
public interface UserAssessmentService extends BaseService<UserAssessment> {

    /**
     * 开始测评
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @return 测评记录ID，如果无法开始测评则返回null
     */
    Integer startAssessment(Integer userId, Integer assessmentType);

    /**
     * 完成测评
     *
     * @param assessmentId 测评记录ID
     * @param resultData 测评结果数据
     * @return 是否成功
     */
    boolean completeAssessment(Integer assessmentId, String resultData);

    /**
     * 发放测评次数
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @param count 发放次数
     * @return 是否成功
     */
    boolean grantAssessmentQuota(Integer userId, Integer assessmentType, Integer count);

    /**
     * 检查用户是否可以开始测评
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @return 检查结果信息
     */
    AssessmentCheckResult checkCanStartAssessment(Integer userId, Integer assessmentType);

    /**
     * 根据用户ID查询所有测评记录
     *
     * @param userId 用户ID
     * @return 测评记录列表
     */
    List<UserAssessment> getUserAssessments(Integer userId);

    /**
     * 根据用户ID和测评类型查询测评记录
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @return 测评记录列表
     */
    List<UserAssessment> getUserAssessmentsByType(Integer userId, Integer assessmentType);

    /**
     * 获取用户最近的测评记录
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @return 最近的测评记录
     */
    UserAssessment getLatestAssessment(Integer userId, Integer assessmentType);

    /**
     * 统计用户指定类型的测评次数
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @return 测评次数
     */
    int countUserAssessments(Integer userId, Integer assessmentType);

    /**
     * 统计用户指定类型在指定月份内的测评次数
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 测评次数
     */
    int countUserAssessmentsInMonth(Integer userId, Integer assessmentType, Date startDate, Date endDate);

    /**
     * 检查用户是否在指定月份内已进行过该类型测评
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @param date 检查的日期
     * @return 是否已进行过
     */
    boolean hasAssessedInMonth(Integer userId, Integer assessmentType, Date date);

    /**
     * 获取用户指定类型测评的上次测评时间
     *
     * @param userId 用户ID
     * @param assessmentType 测评类型
     * @return 上次测评时间，如果没有则返回null
     */
    Date getLastAssessmentTime(Integer userId, Integer assessmentType);

    /**
     * 测评检查结果类
     */
    class AssessmentCheckResult {
        private boolean canStart;
        private String message;
        private Date lastAssessmentTime;

        public AssessmentCheckResult(boolean canStart, String message) {
            this.canStart = canStart;
            this.message = message;
        }

        public AssessmentCheckResult(boolean canStart, String message, Date lastAssessmentTime) {
            this.canStart = canStart;
            this.message = message;
            this.lastAssessmentTime = lastAssessmentTime;
        }

        public boolean isCanStart() {
            return canStart;
        }

        public void setCanStart(boolean canStart) {
            this.canStart = canStart;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Date getLastAssessmentTime() {
            return lastAssessmentTime;
        }

        public void setLastAssessmentTime(Date lastAssessmentTime) {
            this.lastAssessmentTime = lastAssessmentTime;
        }
    }
}


