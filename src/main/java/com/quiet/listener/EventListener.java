package com.quiet.listener;

import java.util.EventObject;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/1/26
 * Desc   :
 */
public interface EventListener {
    public <Event extends EventObject> void trigger(Event event);
}
