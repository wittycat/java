package com.chenxun;
/** 
 * @author：chenxun
 * 创建时间：2016年10月4日 下午6:39:49 
 * 参考：
 * 说明：
 */
public class TestGc {
	static  int _1MB = 1024*1024;//1024字节*1024
    public static void main(String[] args) {
    	
    	 byte[] allocation1 = new byte [20*_1MB];
    	 byte[] allocation2 = new byte [2*_1MB];
    	 byte[] allocation3 = new byte [2*_1MB];
    	 byte[] allocation4 = new byte [4*_1MB];

    	 
    	 String threadName =Thread.currentThread().getName();
    	 System.out.format("%s: %s%n",threadName,"message");
    	 
    	 for (;;) {
			try {
				Thread.currentThread().sleep(30000);
				System.gc();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		 }
	}
}
