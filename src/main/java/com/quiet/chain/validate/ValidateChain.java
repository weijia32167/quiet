package com.quiet.chain.validate;

import java.util.LinkedHashSet;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/15
 * Desc   :
 */
public class ValidateChain implements IValidateChain {

    private LinkedHashSet<IValidate> validates;

    public ValidateChain() {
        this.validates = new LinkedHashSet<>();
    }

    public void add(IValidate validate) {
        this.validates.add(validate);
    }

    @Override
    public void validate() throws ValidateException {
        if (validates != null && validates.size() != 0) {
            for (IValidate validate : validates) {
                validate.validate();
            }
        }
    }
}
