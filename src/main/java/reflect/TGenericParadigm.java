package reflect;

import java.util.List;

/** 
 * @author：chenxun
 * 创建时间：2016年6月12日 下午10:43:22 
 * 参考：Erasure of method method1(List<Integer>) is the same as another
 *      method in type GenericParadigm
 * 说明：泛型在编译期有效，在运行期将被擦除
 */
public class TGenericParadigm {

	public TGenericParadigm() {
		
	}
	
	public void method1(List<String> list){
		
	}
	/*public void method1(List<Integer> list){
		
	}*/

}
