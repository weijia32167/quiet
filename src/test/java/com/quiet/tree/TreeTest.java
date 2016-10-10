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
public class TreeTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TreeTest.class);

    private ITreeElement root  = new TreeRootElement<>(new DoubleData(100.0d));
    ITreeElement local = new TreeNodeElement(0.618d);
    ITreeElement other = new TreeNodeElement(0.382d);


    ITreeElement local_live_5 = new TreeNodeElement(0.1d);
    ITreeElement local_vod_ts = new TreeNodeElement(0.2d);
    ITreeElement local_live_4 = new TreeNodeElement(0.1d);
    ITreeElement local_vod_mp4 = new TreeNodeElement(0.1d);
    ITreeElement local_live_3 = new TreeNodeElement(0.1d);
    ITreeElement local_vod_p2p = new TreeNodeElement(0.1d);
    ITreeElement local_live_2 = new TreeNodeElement(0.1d);
    ITreeElement local_vod_other = new TreeNodeElement(0.1d);
    ITreeElement local_live_1 = new TreeNodeElement(0.1d);

    ITreeElement other_live_5 = new TreeNodeElement(0.2d);
    ITreeElement other_vod_ts = new TreeNodeElement(0.1d);
    ITreeElement other_live_4 = new TreeNodeElement(0.1d);
    ITreeElement other_vod_mp4 = new TreeNodeElement(0.1d);
    ITreeElement other_live_3 = new TreeNodeElement(0.1d);
    ITreeElement other_vod_p2p = new TreeNodeElement(0.1d);
    ITreeElement other_live_2 = new TreeNodeElement(0.1d);
    ITreeElement other_vod_other = new TreeNodeElement(0.1d);
    ITreeElement other_live_1 = new TreeNodeElement(0.1d);

    @Test
    public void test(){
        root.element(local).element(other);
        local.element(local_live_5).element(local_live_4).element(local_live_3).element(local_live_2).element(local_live_1).
              element(local_vod_ts).element(local_vod_mp4).element(local_vod_p2p).element(local_vod_other);

        other.element(other_live_5).element(other_live_4).element(other_live_3).element(other_live_2).element(other_live_1).
              element(other_vod_ts).element(other_vod_mp4).element(other_vod_p2p).element(other_vod_other);
        LOGGER.info(root.outputData().toString()+"");
        LOGGER.info(local.outputData().toString()+"");
        LOGGER.info(other.outputData().toString()+"");
        LOGGER.info(other_live_5.outputData().toString()+"");

    }



}
