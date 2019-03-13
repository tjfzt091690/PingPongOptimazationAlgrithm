package PingPong;

import java.util.ArrayList;

public class EdgeClass extends Edge{

	@Override
	public ArrayList<Double> touchCollision(ArrayList<Double> x) {
		ArrayList<Double> dirs=new ArrayList<Double>(x.size());
		for(int i=0;i<x.size();i++){
			if(x.get(i)>=-1.0 && x.get(i)<=1.0)
				dirs.add(1.0);
			else
				dirs.add(-1.0);
		}
		return dirs;
	}

}
