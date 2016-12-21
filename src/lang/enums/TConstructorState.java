package lang.enums;
/** 
 * @author：chenxun
 * 创建时间：2016年5月28日 下午11:46:36 
 * 参考：
 * 说明：
 */
public enum TConstructorState {
	//INIT  START 叫枚举常量名称
	INIT(1,"初始化"),   //每个实例项，初始化传入参数和枚举构造函数对应
	START(2,"开始"),
	END(3,"结束"),
	DEFAULT(4,"默认");

	
    /**
     * 枚举提供构造函数可以对枚举添加描述
     * @param index
     * @param desc
     */
	private TConstructorState( int index,String desc) {
		this.desc = desc;
		this.index = index;
	}
	
	private String desc;
	private int index;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
    
	public static TConstructorState getConstructor(int index){
		TConstructorState state = null;
		TConstructorState[] values = TConstructorState.values();
		for(TConstructorState constructorState :values){
			if(constructorState.getIndex() ==index){
				state =  constructorState;
			}
		}
		return state==null?TConstructorState.DEFAULT:state;
	}
	/**
	 * 判断是否有此枚举常量
	 * @param name
	 * @return
	 */
	public static Boolean contains(String name){
		TConstructorState[] values = TConstructorState.values();
		for(TConstructorState constructorState :values){
			if(name.equals(constructorState.name()));
			   return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
   
}
