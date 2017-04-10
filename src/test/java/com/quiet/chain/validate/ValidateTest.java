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
        String a = "21";
        StringNumberRangeValidate validate = new StringNumberRangeValidate(a, "a", Float.class, 10, 20);
        validate.validate();
    }

    @Test
    public void nullValidate() throws ValidateException {
        String a = null;
        NotNullValidate validate = new NotNullValidate(a, "a");
        validate.validate();
    }

    @Test
    public void ipv4Validate() throws ValidateException {
        String ipV4 = "123";
        IPv4Validate validate = new IPv4Validate(ipV4);
        validate.validate();
    }

}
