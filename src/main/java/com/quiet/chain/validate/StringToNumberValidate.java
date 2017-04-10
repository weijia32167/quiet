package com.quiet.chain.validate;

import com.quiet.chain.validate.core.ValidateException;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public class StringToNumberValidate extends NotNullValidate {

    protected final Class clazz;

    public StringToNumberValidate(String arg, String name, Class clazz) {
        super(arg, name);
        this.clazz = clazz;
    }

    @Override
    public void validate() throws ValidateException {
        super.validate();
        String className = clazz.getName();
        try {
            switch (className) {
                case "java.lang.Byte":
                    Byte.parseByte(arg);
                    break;
                case "java.lang.Integer":
                    Integer.parseInt(arg);
                    break;
                case "java.lang.Short":
                    Short.parseShort(arg);
                    break;
                case "java.lang.Long":
                    Long.parseLong(arg);
                    break;
                case "java.lang.Float":
                    Float.parseFloat(arg);
                    break;
                case "java.lang.Double":
                    Double.parseDouble(arg);
                    break;
                default:
                    ValidateException.throwException("Non expectation Class Type");
                    break;
            }
        } catch (NumberFormatException e) {
            ValidateException.throwException(name + "[" + arg + "]" + " " + Constant.ERROR_NOT_NUMBER);
        } catch (ValidateException e) {
            throw e;
        }
    }
}
