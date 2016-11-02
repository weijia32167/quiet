package com.quiet.math;

import Jama.Matrix;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/13
 * Desc   : 线性回归
 */
public final class LinearRegression {
    /**
     * 矩阵kx=y,利用最小二乘法求解k
     * 最小二乘解（OLS）：Ordinary Least Squares
     * https://en.wikipedia.org/wiki/Ordinary_least_squares
     * Ax=b
     */
    public static final Matrix OLS(Matrix x,Matrix y) {
        return x.transpose().times(x).inverse().times(x.transpose()).times(y);
    }
    /**
     * 用1-范数正则化OLS
     * Feature Selection
     * Interpretability
     **/
    public static final Matrix lasso(Matrix x,Matrix y) {
        return null;
    }
    /**
     *用2-范数正则化OLS,可以有效防止过拟合
     **/
    public static final Matrix ridge(Matrix x,Matrix y,double lambda) {
        Matrix tMatrix =  x.transpose();
        Matrix tempMatrix =tMatrix.times(x);
        Matrix iMatrix= Matrix.identity(tempMatrix.getRowDimension(), tempMatrix.getColumnDimension());
        return x.transpose().times(x).plus(iMatrix.times(lambda)).inverse().times(tMatrix).times(y);
    }

}
