package PingPong;

import java.util.ArrayList;

public class Ball {
	private ArrayList<Double> location;
	private ArrayList<Double> velocity;
	private Double ratio;
	/*
	 * location包含自变量数值和因变量数值，格式为[x1,x2,y]。
	 * The variable location is like [x1,x2,y], where xi is an independent variable
	 * and y is the dependent variable.
	 * 
	 * velocity包含自变量方向和因变量方向（高度方向），格式为[v1,v2,vy]。
	 * The variable location is like [v1,v2,vy], where vi is the velocity in xi direction
	 * and vy is the velocity in y direction.
	 * 
	 * ratio是碰撞系数，碰撞后的能量变更为原来的ratio倍数，即损失1-ratio。
	 * (1-ratio) is energy lose ratio.
	 */
	public Ball(ArrayList<Double> location,ArrayList<Double> velocity,Double ratio){
		this.location=location;
		this.velocity=velocity;
		this.ratio=ratio;
	}
	public Ball(ArrayList<Double> location){
		this.location=location;
		ArrayList<Double> tVelocity=new ArrayList<Double>(location.size());
		for(int i=0;i<location.size();i++){
			tVelocity.add(0.0);
		}
		this.velocity=tVelocity;
		this.ratio=0.95;
	}
	public ArrayList<Double> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<Double> location) {
		this.location = location;
	}
	public ArrayList<Double> getVelocity() {
		return velocity;
	}
	public void setVelocity(ArrayList<Double> velocity) {
		this.velocity = velocity;
	}
	public Double getRatio() {
		return ratio;
	}
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	
}
