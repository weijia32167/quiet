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

    public StringToNumberValidate(String name, Class clazz) {
        super(name);
        this.clazz = clazz;
    }

    @Override
    public void validate() throws ValidateException {
        String className = clazz.getName();
        try {
            switch (className) {
                case "java.lang.Byte":
                    Byte.parseByte(name);
                    break;
                case "java.lang.Integer":
                    Integer.parseInt(name);
                    break;
                case "java.lang.Short":
                    Short.parseShort(name);
                    break;
                case "java.lang.Long":
                    Long.parseLong(name);
                    break;
                case "java.lang.Float":
                    Float.parseFloat(name);
                    break;
                case "java.lang.Double":
                    Double.parseDouble(name);
                    break;
                default:
                    ValidateException.throwException("Non expectation Class Type");
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ValidateException.throwException(e.getMessage());
        } catch (ValidateException e) {
            throw e;
        }
    }
}
