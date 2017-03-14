package com.quiet.math;

import Jama.Matrix;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/1/17
 * Desc   : 线性回归测试
 */
public class LinearRegressionTest {

    public static final Logger logger = LoggerFactory.getLogger(LinearRegressionTest.class);

    private Matrix A;
    private Matrix b;

    private double lambda = 15;

    @Before
    public void init() {
        double[][] _A = {{3, 4, 5, 6, 7, 9.2}, {4, 5, 10, 2, 3, 7}, {3, 4, 8, 6, 7, 8}, {4, 5, 19, 2, 3, 7}};
        double[][] _b = {{3}, {1}, {2}, {3}};
        A = Matrix.constructWithCopy(_A);
        b = Matrix.constructWithCopy(_b);
        logger.info("Matrix A:");
        A.print(10, 2);
        logger.info("Matrix b:");
        b.print(10, 2);
    }

    @Test
    public void ridgeTest() {
        Matrix x = LinearRegression.ridge(A, b, lambda);
        logger.info("Ridge result Matrix x:");
        x.print(10, 2);
    }

}
