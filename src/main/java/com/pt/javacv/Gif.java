package com.pt.javacv;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * @author nate-pt
 * @date 2021/7/27 15:30
 * @Since 1.8
 * @Description gif图片制作
 */
public class Gif {

    public static void main(String[] args) throws Exception, UnsupportedEncodingException, FileNotFoundException, org.bytedeco.javacv.FrameRecorder.Exception {
        String input="E:\\BaiduNetdiskDownload\\19第十九讲：图的搜索.avi";
        String output="eguid.gif";
        transToGif(input,300,150,25,output);
    }


    /**
     * 更简单的转gif动态图
     * @param input
     * @param width
     * @param height
     * @param frameRate
     * @param output
     */
    public static void transToGif(String input,Integer width,Integer height,Integer frameRate,String output) throws FileNotFoundException, Exception, org.bytedeco.javacv.FrameRecorder.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(input);
        grabber.start();

        if(width==null||height==null) {
            width=grabber.getImageWidth();
            height=grabber.getImageHeight();
        }

        //gif录制器
        FFmpegFrameRecorder recorder =new FFmpegFrameRecorder(output,width,height,0);
        //packed RGB 1:2:1,  8bpp, (msb)1R 2G 1B(lsb)
        recorder.setPixelFormat(avutil.AV_PIX_FMT_RGB4_BYTE);//设置像素格式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_GIF);//设置录制的视频/图片编码
        if(frameRate!=null) {
            recorder.setFrameRate(frameRate);//设置帧率
        }
        recorder.start();


        CanvasFrame canvas = new CanvasFrame("转换gif中屏幕预览");// 新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        Frame frame = null;

        // 只抓取图像画面
        for (;(frame = grabber.grabImage()) != null;) {
            try {
                //录制
                recorder.record(frame);
                //显示画面
                canvas.showImage(frame);
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }

        recorder.close();//close包含stop和release方法
        grabber.close();//close包含stop和release方法
        canvas.dispose();
    }
}
