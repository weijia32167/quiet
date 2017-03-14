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
     * 最小二乘解（OLS）：Ordinary Least Squares  假设高斯分布和最大似然思想推导而来
     * https://en.wikipedia.org/wiki/Ordinary_least_squares
     * Ax=b
     */
    public static final Matrix ols(Matrix x,Matrix y) {
        return x.transpose().times(x).inverse().times(x.transpose().times(y));
    }
    /**
     * 一范数
     * Feature Selection          假设拉普拉斯分布和最大似然思想推导而来
     * Interpretability
     **/
    public static final Matrix lasso(Matrix x,Matrix y) {
        return null;
    }
    /**
     * 用Ridge estimate的方式估计矩阵方程 Ax = b 中的 x
     * 用2-范数正则化OLS,可以有效防止过拟合
     **/
    public static final Matrix ridge(Matrix A, Matrix b, double lambda) {
        Matrix tMatrix = A.transpose();
        Matrix tempMatrix = tMatrix.times(A);
        Matrix iMatrix= Matrix.identity(tempMatrix.getRowDimension(), tempMatrix.getColumnDimension());
        return A.transpose().times(A).plus(iMatrix.times(lambda)).inverse().times(tMatrix).times(b);
    }


}
