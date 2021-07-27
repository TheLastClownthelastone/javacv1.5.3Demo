package com.pt.javacv;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

/**
 * @author nate-pt
 * @date 2021/7/27 16:10
 * @Since 1.8
 * @Description
 */
public class Ico {
    public static void main(String[] args) throws Exception{
        createIco("rtmp://58.200.131.2:1935/livetv/cctv1","eguid.ico",256,256);
    }

    /**
     * 制作ico图标
     * @param url 支持各种视频源和图片地址
     * @param ico ico生成地址
     * @param width 宽度，不超过256
     * @param height 高度，不超过256
     */
    public static void createIco(String url,String ico,int width,int height) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url);

        grabber.start();

        FFmpegFrameRecorder recorder=new FFmpegFrameRecorder(ico,0);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_BMP);
        //ico支持bmp和png两种图片编码方式
        /**ico使用bmp编码情况下支持的像素格式
         1bit               pal8
         4bit               pal8
         8bit               pal8
         16bit              rgb555le
         24bit              bgr24
         32bit              bgra
         */
        recorder.setPixelFormat(avutil.AV_PIX_FMT_BGRA);//设置像素格式
//		recorder.setPixelFormat(avutil.AV_PIX_FMT_BGR24);//设置像素格式
//		recorder.setPixelFormat(avutil.AV_PIX_FMT_RGB555LE);//设置像素格式
        //像素宽高都不能超过256，width<=256&&height<=256

        recorder.setImageWidth(width>256?256:width);
        recorder.setImageHeight(height>256?256:height);
        recorder.start();

        Frame frame = null;
        // 只采集画面
        if((frame = grabber.grabImage())!=null) {
            // 显示画面
            recorder.record(frame);
        }

        recorder.close();
        grabber.close();
    }
}
