package com.quiet.chain.validate;

import com.quiet.chain.validate.core.ValidateException;
import org.junit.Test;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public class ValidateTest {

    @Test
    public void stringToNumberValidate() throws ValidateException {
        String a = "18";
        StringNumberRangeValidate validate = new StringNumberRangeValidate(a, Float.class, 10, 20);
        validate.validate();
    }

}
