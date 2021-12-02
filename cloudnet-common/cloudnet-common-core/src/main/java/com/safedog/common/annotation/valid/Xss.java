package com.safedog.common.annotation.valid;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author ycs
 * @description 自定义注解校验器
 * @date 2021/10/28 10:18
 */
@Target({ElementType.FIELD})
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {XssValidator.class})
public @interface Xss {
}
