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
        try {
            String error = "abc";
            String nullObject = null;
            String object = "1234f";
            StringToNumberValidate validate = new StringToNumberValidate(object, "object", Float.class);
            validate.validate();
        } catch (ValidateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stringNumberRangeValidate() throws ValidateException {
        String error1 = "21";
        String error2 = "dsadsa";
        String nullObject = null;
        String object = "12f";
        StringNumberRangeValidate validate = new StringNumberRangeValidate(error1, "object", Float.class, 10, 20);
        validate.validate();
    }

    @Test
    public void numberRangeValidate() throws ValidateException {
        Number nullObject = null;
        Number object = 11d;
        Number error = 21d;
        NumberRangeValidate validate = new NumberRangeValidate("object", error, 10, 20);
        validate.validate();
    }

    @Test
    public void nullValidate() throws ValidateException {
        String error = null;
        String object = "";
        NotNullValidate validate = new NotNullValidate(object, "object");
        validate.validate();
    }

    @Test
    public void ipv4Validate() throws ValidateException {
        String error = "123";
        String obejct = "123.123.123.10";
        IPv4Validate validate = new IPv4Validate(obejct);
        validate.validate();
    }

}
