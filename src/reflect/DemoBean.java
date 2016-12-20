package reflect;

import java.io.Serializable;

/** 
 * @author:chenxun
 * @2016年12月17日 下午11:32:06 
 * @Theme:
 * @Reference:
 * @Descript:
 */
public class DemoBean implements IDemoBean,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String field;
	
    public DemoBean() {
	}
    public DemoBean(String field) {
    	this.field = field;
	}

	public void setField(String field) {
		this.field = field;
		System.out.println("setField 参数："+field);
	}

	public String getField() {
		System.out.println("getField 执行了");
		return field;
	}
	
	@Override
	public String toString() {
		return "DemoBean [field=" + field + "]";
	}
	
}
