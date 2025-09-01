package com.mindskip.xzs.viewmodel.wx.share;

import lombok.Data;

@Data
public class ShareStatisticsResponseVM {
    
    /**
     * 总分享次数
     */
    private Integer totalShares;

    /**
     * 新用户分享次数
     */
    private Integer newUserShares;

    /**
     * 老用户分享次数
     */
    private Integer oldUserShares;
}
