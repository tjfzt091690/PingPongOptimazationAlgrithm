package PingPong;

import java.util.ArrayList;
/*
 * 核心过程有两个，一个是自由落体；一个是碰撞过程，碰到边界或者地面，区别在碰到地面有能量损耗。
 * There are two core procedures, falling and collision. 
 * There are two types of collision, collision on edges and collision on ground,
 * while energe loss will occur in the latter collison.
 */
public class FallAndCollision {
	/*
	 * 深度复制
	 * A tool for deep copy.
	 */
	public static ArrayList<Double> copy(ArrayList<Double> input){
		ArrayList<Double> tmp=new ArrayList<Double>(input.size());
		for(int i=0;i<input.size();i++){
			tmp.add(input.get(i));
		}
		return tmp;
	}
	private ArrayList<Ball> balls;
	private Ground ground;
	private Edge edge;
	private Double gravity=9.8;
	private ArrayList<Double> ratioEdge=new ArrayList<Double>();
	private ArrayList<Double> ratioGround=new ArrayList<Double>();
	private Integer times=30;
	public FallAndCollision(ArrayList<Ball> balls,Ground ground,Edge edge,Double gravity,Integer times) throws Exception{
		if(balls==null || balls.size()==0)
			throw new Exception("参数异常");
		this.balls=balls;
		this.ground=ground;
		this.edge=edge;
		for(int i=0;i<balls.get(0).getLocation().size()-1;i++){
			/*
			 * 碰到地面有能量损耗。
			 * Energe loss will occur when balls collide on the ground.
			 */
			ratioEdge.add(1.0);
			ratioGround.add(balls.get(0).getRatio());
		}
		this.gravity=gravity;
		this.times=times;
	}
	/*
	 * 球的位置是[x1,x2,h]，对应的地面为[x1,x2,y]，这里求y。
	 * To get the height of the ground under the flying ball.
	 */
	private ArrayList<Double> getX(ArrayList<Double> input){
		ArrayList<Double> x=new ArrayList<Double>(input.size()-1);
		for(int i=0;i<input.size()-1;i++){
			x.add(input.get(i));
		}
		return x;
	}
	/*
	 * 每一步均判断是否到达边界。
	 * It is necessary to determine whether the edge has been reached.
	 */
	public void touchEdge(Ball ball){
		ArrayList<Double> ballLocation=ball.getLocation();
		ArrayList<Double> x=getX(ballLocation);
		ArrayList<Double> vEdge=edge.touchCollision(x);
		for(int i=0;i<x.size();i++){
			ballLocation.set(i, ballLocation.get(i)*vEdge.get(i));
		}
	}
	/*
	 * 每一步判断触地。
	 * It is necessary to determine whether the ball collides the ground.
	 */
	public boolean touchGround(Ball ball){
		ArrayList<Double> ballLocation=ball.getLocation();
		ArrayList<Double> x=getX(ballLocation);
		Double vValue=ground.getValue(x);
		/*
		 * 两个步骤：位置调整，将球的位置放在地上，理论上调整中能量守恒，实际上动能不变，位置调高；根据碰撞耗能来修正速度。
		 * Two steps:
		 * 1 If the ball is under ground,the set it's height to the height of the ground(set h=y).
		 * 2 Because of energy lose, the velocity vector needs to be adjusted. Here, decrease the kinetic energy and adjust directions of velocity to 45 degree.
		 */
		Double ballHeight=ballLocation.get(ballLocation.size()-1);
		ArrayList<Double> velocity=ball.getVelocity();
		Double ratio=ball.getRatio();
		boolean isTouch=false;
		if(ballHeight<vValue){
			isTouch=true;
			ballLocation.set(ballLocation.size()-1,vValue);
			ArrayList<Double> normal=ground.getNormal(x);
			Double nv=Math.sqrt(ball.getRatio()*(velocity.stream().map(xx->Math.pow(xx, 2.0)).reduce(0.0, (x1,x2)->x1+x2))/velocity.size());
			for(int i=0;i<velocity.size();i++){
				velocity.set(i, normal.get(i)/nv);
			}
		}
		return isTouch;
	}
	/*
	 * 核心优化：变步长，假设高度方向能够4个步长回到原地。
	 * Core idea: variable step size. Assuming that the ball will back to the origin height after 4 time steps.
	 */
	public Double getDeltaT(Ball ball){
		ArrayList<Double> vs=ball.getVelocity();
		return Math.max(vs.get(vs.size()-1)/2.0/gravity,0.001);// (2*v/g)/4=v/(2g)
	}
	/*
	 * xi=xi+vi*dt
	 * h=h+vh*dt-1/2*g*dt^2
	 * vh=vh-g*dt
	 */
	public void getNextPosition(Ball ball,Double dt){
		ArrayList<Double> vs=ball.getVelocity();
		ArrayList<Double> ps=ball.getLocation();
		for(int i=0;i<ps.size()-1;i++){
			ps.set(i, ps.get(i)+vs.get(i)*dt);
		}
		//height direction
		ps.set(ps.size()-1, ps.get(ps.size()-1)+vs.get(vs.size()-1)*dt-0.5*gravity*dt*dt);
		vs.set(vs.size()-1, vs.get(vs.size()-1)-gravity*dt);
	}
	/*
	 * 每一次循环的起始位置都是刚碰撞完毕后，第一次初始化的位置也是地面，且一定的向上速度。
	 * In each loop, the ball is on the ground and has a velocity and vh is positive.
	 */
	public void oneTime(Ball ball){
		Double dt=getDeltaT(ball);
		while(true){
			getNextPosition(ball,dt);
			touchEdge(ball);
			if(touchGround(ball))
				return;
		}
	}
	/*
	 * 单个球的过程，设置迭代次数。
	 * After 'times' iterations, get the h of the ball and return it as result.
	 */
	public Double getMin(Ball ball){
		for(int i=0;i<times;i++){
			oneTime(ball);
		}
		ArrayList<Double> lastLocation=ball.getLocation();
		return lastLocation.get(lastLocation.size()-1);
	}
	/*
	 * 所有的球。
	 * All balls.
	 */
	public void getMin(){
		for(int i=0;i<balls.size();i++){
			System.out.println(getMin(balls.get(i)));
		}
	}
	public static void main(String[] args) throws Exception {
		//min x1^2+x2^2
		Ground ground=new Ground(ps->{
			Double sum=0.0;
			for(int i=0;i<ps.size();i++){
				sum+=Math.pow(ps.get(i),2.0);
			}
			return sum;
		});
		ArrayList<Ball> balls=new ArrayList<Ball>();
		Double ratio=0.93;
		/*
		 * 初始化球的位置和速度。
		 * Initializing the locations and the velocities of the balls.
		 * All balls are on the ground.
		 */
		for(int i=0;i<20;i++){
			ArrayList<Double> location=new ArrayList<Double>(2);
			ArrayList<Double> velocity=new ArrayList<Double>(2);
			for(int j=0;j<2;j++){
				location.add(Math.random()*2.0-1.0);
				velocity.add(0.0);
			}
			location.add(ground.getValue(location));
			velocity.add(Math.random()*2.0);
			Ball ball=new Ball(location,velocity,ratio);
			balls.add(ball);
		}
		/*
		 * 边界，[-1.0,1.0]
		 * Edges.
		 */
		Edge edge=new EdgeClass();
		FallAndCollision fc=new FallAndCollision(balls,ground,edge,new Double(9.8),new Integer(30));
		fc.getMin();
	}

}
