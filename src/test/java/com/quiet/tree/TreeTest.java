package com.quiet.tree;

import com.quiet.tree.data.TreeElementData;
import com.quiet.tree.node.ITreeElement;
import com.quiet.tree.node.TreeNodeElement;
import com.quiet.tree.node.TreeRootElement;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/26
 * Desc   :
 */
public class TreeTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TreeTest.class);
    private TreeRootElement root ;
    Map<String,BigDecimal> value = new HashMap<>();
    {
        value.put("a",new BigDecimal(Double.toString(100.0d)));
        value.put("b",new BigDecimal(Double.toString(200.0d)));
        TreeElementData data = new TreeElementData(value);
        data.addAccumulationFiled("request",0);
        data.addAccumulationFiled("response",0);
        root = new TreeRootElement(data,"root");
    }

    ITreeElement local = new TreeNodeElement(0.618d,"local");
    ITreeElement other = new TreeNodeElement(0.382d,"other");

    ITreeElement local_live_5 = new TreeNodeElement(0.1d,"local_live_5");
    ITreeElement local_vod_ts = new TreeNodeElement(0.2d,"local_vod_ts");
    ITreeElement local_live_4 = new TreeNodeElement(0.1d,"local_live_4");
    ITreeElement local_vod_mp4 = new TreeNodeElement(0.1d,"local_vod_mp4");
    ITreeElement local_live_3 = new TreeNodeElement(0.1d,"local_live_3");
    ITreeElement local_vod_p2p = new TreeNodeElement(0.1d,"local_vod_p2p");
    ITreeElement local_live_2 = new TreeNodeElement(0.1d,"local_live_2");
    ITreeElement local_vod_other = new TreeNodeElement(0.1d,"local_vod_other");
    ITreeElement local_live_1 = new TreeNodeElement(0.1d,"local_live_1");

    ITreeElement other_live_5 = new TreeNodeElement(0.2d,"other_live_5");
    ITreeElement other_vod_ts = new TreeNodeElement(0.1d,"other_vod_ts");
    ITreeElement other_live_4 = new TreeNodeElement(0.1d,"other_live_4");
    ITreeElement other_vod_mp4 = new TreeNodeElement(0.1d,"other_vod_mp4");
    ITreeElement other_live_3 = new TreeNodeElement(0.1d,"other_live_3");
    ITreeElement other_vod_p2p = new TreeNodeElement(0.1d,"other_vod_p2p");
    ITreeElement other_live_2 = new TreeNodeElement(0.1d,"other_live_2");
    ITreeElement other_vod_other = new TreeNodeElement(0.1d,"other_vod_other");
    ITreeElement other_live_1 = new TreeNodeElement(0.1d,"other_live_1");

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
        LOGGER.info(other_live_5.getChain().toString());
        LOGGER.info(other_vod_other.outputData().getContainer()+"");

        for(int i= 0 ; i < 100 ; i++ ){
            root.increment("other_live_1","response");
            if(i>0 && i % 10 == 0){
                root.backupData();
            }
        }
        LOGGER.info(root.getValue("local_vod_mp4","a").toString());


     /*   LOGGER.info(root.canService("other_live_1")+"");
        root.addResponse("other_live_1");
        LOGGER.info(root.canService("other_live_1")+"");
        root.addResponse("other_live_5");
        LOGGER.info(root.getResponse("other_live_1")+"");
        LOGGER.info(root.getResponse("other")+"");
        LOGGER.info(root.getResponse("root")+"");*/


    }



}
