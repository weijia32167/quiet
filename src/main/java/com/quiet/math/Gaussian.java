package com.quiet.math;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/15
 * Desc   : 计算高斯函数值(Gaussian function)
 * wiki   :<href>https://en.wikipedia.org/wiki/Gaussian_function<href/>
 * 函数图像:<href>https://en.wikipedia.org/wiki/File:Normal_Distribution_PDF.svg<href/>
 * 高斯函数特征是 :离对称轴越近则 返回值越大
 */
public final class Gaussian {

    /**
     * 跟曲线的高度相关
     */
    private final Number a;
    /**
     * 图像关于X轴对称,b是曲线在图像中心的X轴坐标。表达的是数学期望
     */
    private final Number b;
    /**
     * 半峰全宽。表达的是数学方差
     */
    private final Number c;

    public Gaussian(Number a, Number b, Number c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double value(Number x) {
        return a.doubleValue() * Math.pow(Math.E, -Math.pow(x.doubleValue() - b.doubleValue(), 2) / (2 * Math.pow(c.doubleValue(), 2)));
    }

    /**
     * 机器学习：高斯核函数
     *
     * @param b 在b处取到最大值
     * @param c
     */
    public static final Gaussian getGaussianKernel(Number b, Number c) {
        return new Gaussian(1d, b, c);
    }


}
