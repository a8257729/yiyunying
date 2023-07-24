package com.ztesoft.mobile.common.exception;

/**
 * <p>Title: UOS Flow工作流管理系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 中兴通讯运营支撑开发部</p>
 * @author UOS平台项目组
 * @version 1.0
 */
import com.zterc.uos.*;
import com.zterc.uos.exception.*;

/**
     * This class implements an exception which can wrapped a lower-level exception.
 * @version 1.0
 */
public class XMLDocException
    extends UOSException {

    /**
     * Creates a new XMLDocumentException wrapping another exception, and with a detail message.
     * @param message the detail message.
     * @param exception the wrapped exception.
     */
    public XMLDocException(String message, Exception exception) {
        super(new CommonError(CommonError.XML_ERROR),
              message,
              exception);
    }

    /**
     * Creates a XMLDocumentException with the specified detail message.
     * @param message the detail message.
     */
    public XMLDocException(String message) {
        super(new CommonError(CommonError.XML_ERROR),
              message);
    }

    /**
     * Creates a new XMLDocumentException wrapping another exception, and with no detail message.
     * @param exception the wrapped exception.
     */
    public XMLDocException(Exception exception) {
        super(new CommonError(CommonError.XML_ERROR),
              exception);
    }

}
