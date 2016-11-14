package com.quiet.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/26
 * Desc   :
 */
public class StringChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    /*分隔符*/
    private ByteBuf delimiter;

    private StringEncoder stringEncoder;
    private StringDecoder stringDecoder;
    private LoggingHandler loggingHandler;
    private long readerIdleTime = 30;
    private long writerIdleTime = 30;
    private long allIdleTime = 60;
    private TimeUnit unit = TimeUnit.SECONDS;

    private ChannelHandler businessHandler;

    public StringChannelInitializer(String delimiter, String charsetString, ChannelHandler businessHandler) {
        Charset charset =  Charset.forName(charsetString);
        this.delimiter = Unpooled.copiedBuffer(delimiter.getBytes());
        stringEncoder = new StringEncoder(charset);
        stringDecoder = new StringDecoder(charset);
        this.businessHandler = businessHandler;
    }

    public StringChannelInitializer(String delimiter, String charsetString, LogLevel level, ChannelHandler businessHandler) {
        Charset charset =  Charset.forName(charsetString);
        this.delimiter = Unpooled.copiedBuffer(delimiter.getBytes());
        stringEncoder = new StringEncoder(charset);
        stringDecoder = new StringDecoder(charset);
        loggingHandler = new LoggingHandler(level);
        this.businessHandler = businessHandler;
    }

    public StringChannelInitializer(byte[] delimiter, String charsetString, LogLevel level, ChannelHandler businessHandler) {
        Charset charset =  Charset.forName(charsetString);
        this.delimiter = Unpooled.copiedBuffer(delimiter);
        stringEncoder = new StringEncoder(charset);
        stringDecoder = new StringDecoder(charset);
        if(level!=null){
            loggingHandler = new LoggingHandler(level);
        }
        this.businessHandler = businessHandler;
    }

    public StringChannelInitializer(byte[] delimiter, String charsetString, ChannelHandler businessHandler) {
        Charset charset =  Charset.forName(charsetString);
        this.delimiter = Unpooled.copiedBuffer(delimiter);
        stringEncoder = new StringEncoder(charset);
        stringDecoder = new StringDecoder(charset);
        this.businessHandler = businessHandler;
    }

    public StringChannelInitializer(String delimiter, String charsetString, LogLevel level, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit, ChannelHandler businessHandler) {
        Charset charset =  Charset.forName(charsetString);
        this.delimiter = Unpooled.copiedBuffer(delimiter.getBytes());
        stringEncoder = new StringEncoder(charset);
        stringDecoder = new StringDecoder(charset);
        loggingHandler = new LoggingHandler(level);
        this.readerIdleTime = readerIdleTime;
        this.writerIdleTime = writerIdleTime;
        this.allIdleTime = allIdleTime;
        this.unit = unit;
        this.businessHandler = businessHandler;
    }

    public StringChannelInitializer(String delimiter, String charsetString, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit, ChannelHandler businessHandler) {
        Charset charset =  Charset.forName(charsetString);
        this.delimiter = Unpooled.copiedBuffer(delimiter.getBytes());
        stringEncoder = new StringEncoder(charset);
        stringDecoder = new StringDecoder(charset);
        this.readerIdleTime = readerIdleTime;
        this.writerIdleTime = writerIdleTime;
        this.allIdleTime = allIdleTime;
        this.unit = unit;
        this.businessHandler = businessHandler;
    }


    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioSocketChannel.pipeline();
        pipeline.addLast("idle",new IdleStateHandler(readerIdleTime,writerIdleTime,allIdleTime, unit));
        pipeline.addLast("split",new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,delimiter));
        pipeline.addLast("encoder",stringEncoder);
        pipeline.addLast("decoder",stringDecoder);
        pipeline.addLast("business",businessHandler);
        if(loggingHandler != null){
            pipeline.addFirst("logger",loggingHandler);
        }
    }
}
