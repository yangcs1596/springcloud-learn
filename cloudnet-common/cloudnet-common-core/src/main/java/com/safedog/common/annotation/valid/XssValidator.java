package com.safedog.common.annotation.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ycs
 * @description
 * @date 2021/10/28 10:53
 */
public class XssValidator implements ConstraintValidator<Xss, Object> {
    @Override
    public void initialize(Xss constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        /**
         * 实现校验逻辑
         */
        return false;
    }
}
