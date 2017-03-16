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
    public static final Matrix olsRidge(Matrix A, Matrix b, double lambda) {
        Matrix tMatrix = A.transpose();
        Matrix tempMatrix = tMatrix.times(A);
        Matrix iMatrix= Matrix.identity(tempMatrix.getRowDimension(), tempMatrix.getColumnDimension());
        return A.transpose().times(A).plus(iMatrix.times(lambda)).inverse().times(tMatrix).times(b);
    }

    /**
     * 局部加权线性回归(Locally Weighted Regression)
     * 非参数学习算法:参数会随数据量增加而变化
     * 核函数：采用高斯函数
     */
    public static final Matrix lwlr(Matrix A, Matrix b, Number k) {
        int rowDimension = A.getRowDimension();
        Gaussian gaussian = Gaussian.getGaussianKernel(rowDimension, k);
        double[][] wArray = new double[rowDimension][rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            wArray[rowDimension - i - 1][rowDimension - i - 1] = gaussian.value(i);
        }
        Matrix w = new Matrix(wArray);
        Matrix tMatrix = A.transpose();
        Matrix tempMatrix = tMatrix.times(w).times(A);
        Matrix inverseMatrix = tempMatrix.inverse();
        return inverseMatrix.times(tMatrix).times(w).times(b);
    }

    /**
     * 局部加权线性回归(Locally Weighted Regression)
     * 非参数学习算法:参数会随数据量增加而变化
     * 核函数：采用高斯函数
     */
    public static final Matrix lwlrRidge(Matrix A, Matrix b, Number k, Number lambda) {
        int rowDimension = A.getRowDimension();
        Gaussian gaussian = Gaussian.getGaussianKernel(rowDimension, k);
        double[][] wArray = new double[rowDimension][rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            wArray[rowDimension - i - 1][rowDimension - i - 1] = gaussian.value(i);
        }
        Matrix w = new Matrix(wArray);
        Matrix tMatrix = A.transpose();
        Matrix tempMatrix = tMatrix.times(w).times(A);
        tempMatrix = tempMatrix.plus(Matrix.identity(tempMatrix.getRowDimension(), tempMatrix.getColumnDimension()).times(lambda.doubleValue()));
        Matrix inverseMatrix = tempMatrix.inverse();
        return inverseMatrix.times(tMatrix).times(w).times(b);
    }




}
