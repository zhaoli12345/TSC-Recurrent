package com.cdut.recurrent.config;

import com.cdut.current.common.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Configuration
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServiceResult handle(Exception e) {
        if (e instanceof RuntimeException) {
//            if (e.getCause() != null && e.getCause() instanceof DataException && e.getCause().getCause() != null ) {
//                return ServiceResult.failure(e.getCause().getCause().getMessage());
//            }
            RuntimeException exception = (RuntimeException) e;
            logger.error("【运行时异常】{}", e);
            return ServiceResult.failure(exception.getMessage());
        } else if(e instanceof MethodArgumentNotValidException validException){
            List<ObjectError> errors = validException.getBindingResult().getAllErrors();
            return ServiceResult.failure(errors.get(0).getDefaultMessage());
        } else {
            logger.error("【系统异常】{}", e);
            return ServiceResult.failure(e.getMessage());
        }
    }
}
