package nio;

import collection.ComporeTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by chenxun on 2017/4/16.
 */
public class Channel {

    /**
     * 貌似 Channel 缓存一般设置2k比较合适
     */
    @Test
    public  void testChannel() {
        Long start = ComporeTime.start();
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream= null;
        try {
            fileInputStream = new FileInputStream("D:\\download\\jdk-6u43-windows-x64.exe");
            fileOutputStream = new FileOutputStream("d:\\" + System.currentTimeMillis() + ".exe");
            FileChannel fileInputStreamChannel = fileInputStream.getChannel();
            FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
            //堆内复制缓冲区：本质也是字节数组
            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
            while (fileInputStreamChannel.read(byteBuffer) != -1) {
                //准备从字节缓冲中读取数据
                byteBuffer.flip();
                fileOutputStreamChannel.write(byteBuffer);
                //clear方法将缓冲区清空，一般是在重新写缓冲区时调用
                byteBuffer.clear();
            }

        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream!=null)
                fileInputStream.close();
                if(fileOutputStream!=null)
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ComporeTime.getOutMinus(start);
    }

    @Test
    public  void testIo() {
        Long start = ComporeTime.start();
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream= null;
        try {
            fileInputStream = new FileInputStream("D:\\download\\jdk-6u43-windows-x64.exe");
            fileOutputStream = new FileOutputStream("d:\\" + System.currentTimeMillis() + ".exe");
            byte[] b = new byte[2048];
            int len;
            while ((len = fileInputStream.read(b) )!= -1) {
                fileOutputStream.write(b,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     ComporeTime.getOutMinus(start);
    }
}
