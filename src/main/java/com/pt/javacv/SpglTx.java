package com.pt.javacv;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import javax.swing.*;

/**
 * @author nate-pt
 * @date 2021/7/27 15:57
 * @Since 1.8
 * @Description 视频过滤，添加特效
 */
public class SpglTx {

    public static void main(String[] args)  throws Exception{
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("E:\\BaiduNetdiskDownload\\19第十九讲：图的搜索.avi");
        grabber.start();

        // 获取视频源信息

        int width = grabber.getImageWidth(),height = grabber.getImageHeight();

        // 设置过滤内容
        FFmpegFrameFilter filter = new FFmpegFrameFilter("scale=800:600,transpose=cclock",width,height);
        filter.start();

        CanvasFrame canvasFrame = new CanvasFrame("视频过滤");
        canvasFrame.setAlwaysOnTop(true);
        canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Frame frame = null;


        while (canvasFrame.isShowing() && (frame = grabber.grabImage()) != null) {
            filter.push(frame);
            Frame frame1 = filter.pullImage();
            canvasFrame.showImage(frame1);
        }

        grabber.close();
        filter.close();
        canvasFrame.dispose();





    }
}
