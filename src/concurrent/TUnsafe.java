package concurrent;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import sun.misc.Unsafe;

/**
 * @Author:chenxun
 * @2017年1月14日 下午9:58:21
 * @Theme:
 * @Reference:
 * http://ifeve.com/sun-misc-unsafe/
 * http://blog.csdn.net/wz408/article/details/41986731
 * @Descript:
 * 1.关于Unsafe的并发性。compareAndSwap方法是原子的，并且可用来实现高性能的、无锁的数据结构。
 *   可能存在ABA问题、指令重排序等
 * 2.	大致过程如下：
 * 		有一些状态
 * 		创建它的副本
 * 		修改它
 * 		执行CAS
 * 		如果失败，重复尝试
 */
public  class TUnsafe {
	
	public volatile int a = 0;
	
	public  int getA() {
		return a;
	}

	public  void setA(int a) {
		this.a = a;
	}
	
	static class ModThread extends Thread {
		
		private TUnsafe tUnsafe;
		
		public ModThread(TUnsafe tUnsafe) {
			this.tUnsafe = tUnsafe;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(3);
				System.out.format("异步线程加1%n");
				tUnsafe.setA(tUnsafe.getA()+1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
		/**
		 * 由于java本身处于安全问题 不能直接访问  必须修改访问权限
		 * Exception in thread "main" java.lang.SecurityException: Unsafe
		 */
		// Unsafe unsafe = Unsafe.getUnsafe();
		// 返回4或8,代表是32位还是64位操作系统。  
		// System.out.println(unsafe.addressSize());
		
		try {
			
			//获取字段 修改访问权限
			Field f = Unsafe.class.getDeclaredField("theUnsafe");// Internal reference
			f.setAccessible(true);
			//获得实例
			Unsafe unsafe = (Unsafe) f.get(null);
			//System.out.println(unsafe.addressSize());

			TUnsafe bean = new TUnsafe();
			Field declaredField = bean.getClass().getDeclaredField("a");
			
			System.out.println(bean.getA());
			
			//等待5秒  然后把字段加1  让cas 更新自旋一会
			new ModThread(bean).start();
			
			//获取实例字段的偏移地址
		    long objectFieldOffset = unsafe.objectFieldOffset(declaredField);
		    
		    //自旋次数
		    int count = 0;
			while (true) {
				 count++;
				 boolean r = unsafe.compareAndSwapInt(bean, objectFieldOffset, 1, 5);
				 if(r)
					 break;
				 
			};
			System.out.format("自旋次数count=%d,a=%d%n", count,bean.getA());
			
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
