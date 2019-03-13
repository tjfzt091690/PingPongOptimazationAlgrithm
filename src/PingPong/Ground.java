package PingPong;

import java.util.ArrayList;
import java.util.function.Function;

public class Ground {
	/*
	 * function是地形函数，输入[x1,x2]，输出y。
	 * y=function(x1,x2)
	 */
	private Function<ArrayList<Double>,Double> function;
	public Ground(Function<ArrayList<Double>,Double> function){
		this.function=function;
	}
	//return y
	public Double getValue(ArrayList<Double> x){
		return function.apply(x);
	}
	/*
	 * 简单起见，地形的法向量全部为45度，45度角仰望星空。这里做归一化处理。
	 * Simply, The normal angles are 45 degree, and the vector is normalized.
	 */
	public ArrayList<Double> getNormal(ArrayList<Double> x){
		Double v0=getValue(x);
		Double sqrt=1.0/Math.sqrt(x.size()+1);
		ArrayList<Double> normalVector=new ArrayList<Double>(x.size()+1);
		for(int i=0;i<x.size();i++){
			ArrayList<Double> xi=FallAndCollision.copy(x);
			xi.set(i, xi.get(i)+0.01);
			Double vi=getValue(xi);
			normalVector.add((vi>v0?-1.0:1.0)*sqrt);
		}
		normalVector.add(-1.0*sqrt);
		return normalVector;
	}
}
