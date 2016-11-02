package com.quiet.tree;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/26
 * Desc   :
 */
public class TreeTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TreeTest.class);
    private TreeRootElement root = new TreeRootElement("root",120);
    @Test
    public void test(){
        root.child(0.618d,"local").child(0.382d,"other");
        root.getTreeElement("local").
                child(0.1d,"local_live_5").child(0.1d,"local_live_4").child(0.1d,"local_live_3").child(0.1d,"local_live_2").child(0.1d,"local_live_1").
                child(0.2d,"local_vod_ts").child(0.1d,"local_vod_mp4").child(0.1d,"local_vod_p2p").child(0.1d,"local_vod_other");
        root.getTreeElement("other").
                child(0.1d,"other_live_5").child(0.1d,"other_live_4").child(0.1d,"other_live_3").child(0.1d,"other_live_2").child(0.1d,"other_live_1").
                child(0.2d,"other_vod_ts").child(0.1d,"other_vod_mp4").child(0.1d,"other_vod_p2p").child(0.1d,"other_vod_other");

        root.addAccumulationFiled("response",0);
        root.setDivisible("allow",25);
        LOGGER.info(root.getDivisible("local_live_5","allow")+"");
        List<TreeNodeElement> treeNodeElements = new ArrayList<>(root.getLeafTreeElements().values());
        int size = treeNodeElements.size();
        Random random = new Random();
        ITreeElement tempTreeElement;
        for(int i= 0 ; i < 10000 ; i++ ){    //模拟一万次请求
            int randomInt = random.nextInt(size);
            tempTreeElement = treeNodeElements.get(randomInt);
            root.increment(tempTreeElement.getUniqueName(),"response");
            if(i>0 && i % 100 == 0){
                double randomDouble = random.nextDouble()*1000;
                BigDecimal b = new BigDecimal(randomDouble);
                double f1 = b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                root.backup();
                root.setDivisible("bandWidthRate",f1);
            }
        }
        LOGGER.info(root.getHistory("local_live_5").toString());
    }
}
