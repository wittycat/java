package reflect.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

import reflect.IDemoBean;

/**
 * @author:chenxun
 * @2016年12月20日 上午12:09:58
 * @Theme:
 * @Reference:
 * @Descript:
 */
@SuppressWarnings("serial")
public class $Proxy11 extends Proxy implements IDemoBean, Serializable {
	private static Method m1;
	private static Method m3;
	private static Method m4;
	private static Method m2;
	private static Method m0;

	public $Proxy11(InvocationHandler paramInvocationHandler) {
		super(paramInvocationHandler);
	}

	public final boolean equals(Object paramObject)

	{
		try {
			return ((Boolean) this.h.invoke(this, m1,
					new Object[] { paramObject })).booleanValue();
		} catch (RuntimeException localRuntimeException) {
			throw localRuntimeException;
		} catch (Throwable localThrowable) {
			throw new UndeclaredThrowableException(localThrowable);
		}
	}

	public final String getField()

	{
		try {
			return (String) this.h.invoke(this, m3, null);
		} catch (RuntimeException localRuntimeException) {
			throw localRuntimeException;
		} catch (Throwable localThrowable) {
			throw new UndeclaredThrowableException(localThrowable);
		}
	}

	public final void setField(String paramString)

	{
		try {
			this.h.invoke(this, m4, new Object[] { paramString });
			return;
		} catch (RuntimeException localRuntimeException) {
			throw localRuntimeException;
		} catch (Throwable localThrowable) {
			throw new UndeclaredThrowableException(localThrowable);
		}
	}

	public final String toString()

	{
		try {
			return (String) this.h.invoke(this, m2, null);
		} catch (RuntimeException localRuntimeException) {
			throw localRuntimeException;
		} catch (Throwable localThrowable) {
			throw new UndeclaredThrowableException(localThrowable);
		}
	}

	public final int hashCode() {
		try {
			return ((Integer) this.h.invoke(this, m0, null)).intValue();
		} catch (RuntimeException localRuntimeException) {
			throw localRuntimeException;
		} catch (Throwable localThrowable) {
			throw new UndeclaredThrowableException(localThrowable);
		}
	}

	static {
		try {
			m1 = Class.forName("java.lang.Object").getMethod("equals",
					new Class[] { Class.forName("java.lang.Object") });
			m3 = Class.forName("reflect.IDemoBean").getMethod("getField",
					new Class[0]);
			m4 = Class.forName("reflect.IDemoBean").getMethod("setField",
					new Class[] { Class.forName("java.lang.String") });
			m2 = Class.forName("java.lang.Object").getMethod("toString",
					new Class[0]);
			m0 = Class.forName("java.lang.Object").getMethod("hashCode",
					new Class[0]);
		} catch (NoSuchMethodException localNoSuchMethodException) {
			throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
		} catch (ClassNotFoundException localClassNotFoundException) {
			throw new NoClassDefFoundError(
					localClassNotFoundException.getMessage());
		}
	}
}
