package com.quiet.tree;

import Jama.Matrix;
import com.quiet.math.LinearRegression;
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
    private IRoot root = new TreeRootElement("root",5);
    @Test
    public void test(){
        root.child(0.618d,"local").child(0.382d,"other");
        root.getTreeElement("local").
                /*child(0.1d,"local_live_5").child(0.1d,"local_live_4").child(0.1d,"local_live_3").child(0.1d,"local_live_2").child(0.1d,"local_live_1").*/
                child(0.8d,"local_vod_ts").child(0.1d,"local_vod_mp4").child(0.05d,"local_vod_p2p").child(0.05d,"local_vod_other");
        root.getTreeElement("other").
                /*child(0.1d,"other_live_5").child(0.1d,"other_live_4").child(0.1d,"other_live_3").child(0.1d,"other_live_2").child(0.1d,"other_live_1").*/
                child(0.8d,"other_vod_ts").child(0.1d,"other_vod_mp4").child(0.05d,"other_vod_p2p").child(0.05d,"other_vod_other");

        root.initAccumulation("response");
        root.setDivisible("allow",25);
        List<TreeNodeElement> treeNodeElements = new ArrayList<>(root.getLeafTreeElements().values());
        int size = treeNodeElements.size();
        Random random = new Random();
        ITreeElement tempTreeElement;
        for(int i= 0 ; i < 10000 ; i++ ){    //模拟一万次业务请求，调度发送给CDN边缘节点响应
            int randomInt = random.nextInt(size);
            tempTreeElement = treeNodeElements.get(randomInt);
            root.increment(tempTreeElement.getUniqueName(),"response",random.nextInt(10)+1);
            if(i>0 && i % 10 == 0){
                double randomDouble = random.nextDouble()*100;
                BigDecimal b = new BigDecimal(randomDouble);
                double f1 = b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
                root.setDivisible("bandWidthRate",f1);
                root.initAccumulation("response");
            }
        }
        List<Double> rootBandWidthRate = root.getDivisibleHistory("root","bandWidthRate");
        LOGGER.info(rootBandWidthRate.toString());
        Double[] root_bandwidthRate_all = new Double[rootBandWidthRate.size()];
        root_bandwidthRate_all = rootBandWidthRate.toArray(root_bandwidthRate_all);
        Double bandWidthRate[][] = new Double[1][8];
        bandWidthRate[0] = root_bandwidthRate_all;


        List<Double> local_vod_ts_response_avg_history = root.getAverageAccumulationHistory("local_vod_ts","response");
        List<Double> local_vod_mp4_response_avg_history =  root.getAverageAccumulationHistory("local_vod_mp4","response");
        List<Double> local_vod_p2p_response_avg_history =  root.getAverageAccumulationHistory("local_vod_p2p","response");
        List<Double> local_vod_other_response_avg_history =  root.getAverageAccumulationHistory("local_vod_other","response");

        List<Double> other_vod_ts_response_avg_history =root.getAverageAccumulationHistory("other_vod_ts","response");
        List<Double> other_vod_mp4_response_avg_history =root.getAverageAccumulationHistory("other_vod_mp4","response");
        List<Double> other_vod_p2p_response_avg_history =root.getAverageAccumulationHistory("other_vod_p2p","response");
        List<Double> other_vod_other_response_avg_history = root.getAverageAccumulationHistory("other_vod_other","response");

        LOGGER.info(local_vod_ts_response_avg_history.toString());
        LOGGER.info(local_vod_mp4_response_avg_history.toString());
        LOGGER.info(local_vod_p2p_response_avg_history.toString());
        LOGGER.info(local_vod_other_response_avg_history.toString());

        LOGGER.info(other_vod_ts_response_avg_history.toString());
        LOGGER.info(other_vod_mp4_response_avg_history.toString());
        LOGGER.info(other_vod_p2p_response_avg_history.toString());
        LOGGER.info(other_vod_other_response_avg_history.toString());

        Double[] local_vod_ts_response_avg = new Double[local_vod_ts_response_avg_history.size()];
        Double[] local_vod_mp4_response_avg = new Double[local_vod_mp4_response_avg_history.size()];
        Double[] local_vod_p2p_response_avg = new Double[local_vod_p2p_response_avg_history.size()];
        Double[] local_vod_other_response_avg = new Double[local_vod_other_response_avg_history.size()];

        Double[] other_vod_ts_response_avg = new Double[other_vod_ts_response_avg_history.size()];
        Double[] other_vod_mp4_response_avg = new Double[other_vod_mp4_response_avg_history.size()];
        Double[] other_vod_p2p_response_avg = new Double[other_vod_p2p_response_avg_history.size()];
        Double[] other_vod_other_response_avg = new Double[other_vod_other_response_avg_history.size()];

        local_vod_ts_response_avg = local_vod_ts_response_avg_history.toArray(local_vod_ts_response_avg);
        local_vod_mp4_response_avg = local_vod_mp4_response_avg_history.toArray(local_vod_mp4_response_avg);
        local_vod_p2p_response_avg = local_vod_p2p_response_avg_history.toArray(local_vod_p2p_response_avg);
        local_vod_other_response_avg = local_vod_other_response_avg_history.toArray(local_vod_other_response_avg);


        other_vod_ts_response_avg = other_vod_ts_response_avg_history.toArray(other_vod_ts_response_avg);
        other_vod_mp4_response_avg = other_vod_mp4_response_avg_history.toArray(other_vod_mp4_response_avg);
        other_vod_p2p_response_avg = other_vod_p2p_response_avg_history.toArray(other_vod_p2p_response_avg);
        other_vod_other_response_avg = other_vod_other_response_avg_history.toArray(other_vod_other_response_avg);

        Double response[][] = new Double[8][];
        response[0] = local_vod_ts_response_avg;
        response[1] = local_vod_mp4_response_avg;
        response[2] = local_vod_p2p_response_avg;
        response[3] = local_vod_other_response_avg;

        response[4] = other_vod_ts_response_avg;
        response[5] = other_vod_mp4_response_avg;
        response[6] = other_vod_p2p_response_avg;
        response[7] = other_vod_other_response_avg;

        Matrix bandWidthRateMatrix = toMatrix(bandWidthRate);
        Matrix businessResponseMatrix = toMatrix(response);
        businessResponseMatrix.print(10,2);
        bandWidthRateMatrix.print(10,2);
        Matrix olsMatrix = LinearRegression.ols(businessResponseMatrix,bandWidthRateMatrix);
        double norm1 = businessResponseMatrix.norm1();
        double norm2 = businessResponseMatrix.norm2();
        LOGGER.info(norm1+":"+norm2);
        Matrix result = LinearRegression.lasso(businessResponseMatrix,bandWidthRateMatrix);
        olsMatrix.print(10,2);
    }

    public static final Matrix toMatrix(Double[][] value){
        double[][] temp = new double[value.length][];
        for(int i = 0 ; i < value.length ; i++){
            temp[i] = new double[value[i].length];
            for(int j =0 ; j < value[i].length ; j++){
                temp[i][j] = value[i][j];
            }
        }
        return new Matrix(temp).transpose();
    }

}
