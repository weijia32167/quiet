package matrix;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/7
 * Desc   :
 */
public class MartixTest {

    public static final Logger logger = LoggerFactory.getLogger(MartixTest.class);

   /* private double[][] data = {{3,4},{4,5}};*/
    private double[][] data = {{3,3},{4.5}};
    private Matrix matrix = Matrix.constructWithCopy(data);

    @Before
    public void printMatrix(){
        matrix.print(10,2);
    }

    @Test
    public void norm(){
        double norm1 = matrix.norm1();      //1范数（列和范数）：矩阵每列元素绝对值求和，取最大的那个值
        double norm2 = matrix.norm2();      //2范数(谱范数)：
        double normF = matrix.normF();      //Frobenius范数：
        double normInf = matrix.normInf();  //Infinity范数(行和范数)：矩阵每行元素绝对值求和，取最大的那个值
        logger.info("norm1:"+norm1);
        logger.info("norm2:"+norm2);
        logger.info("normF:"+normF);
        logger.info("normInf:"+normInf);
    }

    /**
     * 矩阵的cond，通常用来描述矩阵的稳定性,小于1表示比较稳定，反之表示不稳定。
     * cond(A) = ||A|| * ||A的逆矩阵||
     * ||A||表示A的范数，Jamas使用matrix.cond()调用的是2范数求解
     */
    @Test
    public void cond(){
        double cond = matrix.cond();
        logger.info("cond:"+cond);
    }

    /**
     * 方阵A的特征值分解
     * Av = dv,其中x是特征向量，m是特征值
     * A = vdv'
     */
    @Test
    public void eig(){
        EigenvalueDecomposition eigenvalueDecomposition = matrix.eig();
        Matrix  d = eigenvalueDecomposition.getD();     //特征值对角矩阵
        Matrix  v = eigenvalueDecomposition.getV();     //特征矩阵
        d.print(10,2);
        v.print(10,2);
        v.times(d).times(v.inverse()).print(10,1);       //A = vdv'
        v.times(d).times(v.transpose()).print(10,1);       //A = vdv'
        v.times(v.transpose()).print(10,10);
    }



}
