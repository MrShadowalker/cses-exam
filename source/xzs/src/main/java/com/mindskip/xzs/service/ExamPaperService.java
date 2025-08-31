package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.dto.exam.ExamPaperDTO;
import com.mindskip.xzs.domain.dto.request.ExamPaperMakeRequest;
import com.mindskip.xzs.domain.entity.ExamPaper;

public interface ExamPaperService extends BaseService<ExamPaper> {

    /**
     * @param req 试卷生成请求
     * @return
     */
    ExamPaperDTO generate(ExamPaperMakeRequest req);

    /**
     * @return 模拟试卷
     */
    ExamPaperDTO mockGenerate();
}
