<h1>基于落体碰撞能量损耗过程的乒乓优化算法</h1>
<h4>Ping-pong Optimization Algorithm</h4>
<h3>这个算法的核心有两个过程，落体和碰撞（碰到边界或者碰到地面，区别在于后者有能量损耗）。</h3>
<h4>There are two core procedures, falling and collision(collison on edges and collison on the ground. Energy lose occurs when ping-pong collides on the ground)</h4>
<h4>落体过程Falling</h4>
<p>xi=xi+vi*dt</p>
<p>h=h+vh*dt-1/2*g*dt^2</p>
<p>hv=hv-g*dt</p>
<h4>碰撞过程Collision</h4>
<p>边界collision on the edges</p>
<p></p>
<p>地面collision on the ground</p>
<p></p>