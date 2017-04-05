package com.quiet.chain.validate;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/15
 * Desc   :
 */
public interface IValidateChain extends IValidate {

    void add(IValidate validate);

    void validate() throws ValidateException;

}
