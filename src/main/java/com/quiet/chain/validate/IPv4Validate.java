package com.quiet.chain.validate;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/15
 * Desc   :
 */
public class IPv4Validate extends RegularExpressionValidate {

    public IPv4Validate(String ipV4) {
        super(ipV4, "ip", Constant.IPV4, "ip[" + ipV4 + "] " + Constant.ERROR_IPV4);
    }

    public IPv4Validate(String ipV4, String errorMessageIfNotIPv4) {
        super(ipV4, "ip", Constant.IPV4, errorMessageIfNotIPv4);
    }
}
