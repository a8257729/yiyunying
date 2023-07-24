package com.ztesoft.eoms.exception;

/**
 *
 * <p>Title: 电子运维项目组</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ztesoft</p>
 * @author li.youbing
 * @version 1.0
 */

import com.zterc.uos.*;
import com.zterc.uos.exception.CommonError;

public class RequiredException
    extends UOSException {

    /**
     * 带附加信息的构造方法
     * 默认异常代码是BSError.REQUIRED
     * @param message String 附加信息
     */
    public RequiredException(String message) {
        super(new CommonError(CommonError.DATA_REQUIRED),
              message);
    }
}
