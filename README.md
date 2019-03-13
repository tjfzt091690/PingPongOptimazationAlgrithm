<h1>基于落体碰撞能量损耗过程的乒乓优化算法</h1>
<h1>Ping-pong Optimization Algorithm</h1>
<p>这个算法的核心有两个过程，落体和碰撞（碰到边界或者碰到地面，区别在于后者有能量损耗）。</p>
<p>There are two core procedures, falling and collision(collison on edges and collison on the ground. Energy lose occurs when ping-pong collides on the ground)</p>
<p>落体过程Falling</p>
<p>
<img src="http://chart.googleapis.com/chart?cht=tx&chl=\Large xi&=xi\+vi*dt" style="border:none;">
</p>
<p>
<img src="http://chart.googleapis.com/chart?cht=tx&chl=\Large h&=h\+vh*dt-1/2*g*dt^2" style="border:none;">
</p>
<p>
<img src="http://chart.googleapis.com/chart?cht=tx&chl=\Large hv&=hv-g*dt" style="border:none;">
</p>