package com.quiet.tree;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/26
 * Desc   :
 */
public class ITreeElementTest{

    public static final Logger LOGGER = LoggerFactory.getLogger(ITreeElementTest.class);

    private ITreeElement<Object> root  = new TreeElement<>(null);
    ITreeElement local = new TreeElement(null);
    ITreeElement other = new TreeElement(null);

    ITreeElement local_live_5 = new TreeElement(null);
    ITreeElement local_vod_ts = new TreeElement(null);
    ITreeElement local_live_4 = new TreeElement(null);
    ITreeElement local_vod_mp4 = new TreeElement(null);
    ITreeElement local_live_3 = new TreeElement(null);
    ITreeElement local_vod_p2p = new TreeElement(null);
    ITreeElement local_live_2 = new TreeElement(null);
    ITreeElement local_vod_other = new TreeElement(null);
    ITreeElement local_live_1 = new TreeElement(null);

    ITreeElement other_live_5 = new TreeElement(null);
    ITreeElement other_vod_ts = new TreeElement(null);
    ITreeElement other_live_4 = new TreeElement(null);
    ITreeElement other_vod_mp4 = new TreeElement(null);
    ITreeElement other_live_3 = new TreeElement(null);
    ITreeElement other_vod_p2p = new TreeElement(null);
    ITreeElement other_live_2 = new TreeElement(null);
    ITreeElement other_vod_other = new TreeElement(null);
    ITreeElement other_live_1 = new TreeElement(null);

    @Test
    public void test(){
        root.element(local).element(other);
        local.element(local_live_5).element(local_live_4).element(local_live_3).element(local_live_2).element(local_live_1).
              element(local_vod_ts).element(local_vod_mp4).element(local_vod_p2p).element(local_vod_other);

        other.element(other_live_5).element(other_live_4).element(other_live_3).element(other_live_2).element(other_live_1).
              element(other_vod_ts).element(other_vod_mp4).element(other_vod_p2p).element(other_vod_other);
        LOGGER.info(root.getPriority()+"");
        LOGGER.info(local.getPriority()+"");
        LOGGER.info(other.getPriority()+"");


    }



}
