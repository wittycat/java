package lang.enums;

import org.junit.Test;

/** 
 * @author：chenxun
 * 创建时间：2016年5月28日 下午11:48:04 
 * 参考：
 * 说明：
 */
public class TState {
	/**
	 * 枚举内置方法
	 * 
	 * 获取枚举项：values()
	 * 每个枚举项都是该枚举的一个实例
	 * 
	 * State.valueOf(枚举.class, "INIT") 或State.valueOf( "INIT")
	 * 名称必须与在此类型中声明枚举常量所用的标识符完全匹配
	 * IllegalArgumentException - 如果指定枚举类型不包含指定名称的常量，或者指定类对象不表示枚举类型 
	 * NullPointerException - 如果 enumType 或 name 为空
	 * 所以使用valueOf可以用try...catch 或参数加以校验
	 */
	@SuppressWarnings("static-access")
	@Test
    public void enumInnerMethod(){
		//枚举名称.values() 获取的是枚举实例的集合 和在增强for循环里面的最后的位置是集合一个意思
		//枚举项名称.values() 得到是枚举项的实例
    	for (State state :State.values()) { 
			System.out.println(state.values());
			State valueOf = State.valueOf(State.class, "INIT");
			System.out.println(valueOf.values());
		}
    }
	
	@Test
	public void enumConstructor(){
		for (TConstructorState state :TConstructorState.values()) {
			System.out.println(state.getDesc());
			System.out.println(state.ordinal());
		}
		
		System.out.println(TConstructorState.END.name());//获取枚举常量名称
		
		TConstructorState constructor = TConstructorState.getConstructor(0);
		System.out.println(constructor.getDesc());
	}
}
