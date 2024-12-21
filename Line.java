import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Line {
	int sx=380,sy=180;
	int dx,dy;
	double length=100,angle_n=0;

	int dir=1;
	int state=0;
	Image hook_image=Toolkit.getDefaultToolkit().getImage("imgs/hook.png");
	
	Gamewin frame;
	Line(Gamewin fframe)
	{
		this.frame=fframe;
	}
	
	void jud_collision()
	{

		for(Object obj:this.frame.objlist)
		{
			if(dx>obj.x&&dx<obj.x+obj.width)
				if(dy>obj.y&&dy<obj.y+obj.height)
				{
					state=3;
					obj.is_catch=true;
				}
		}
			
	}
	
	void draw(Graphics g)
	{
		dx=(int)(sx+length*Math.cos(angle_n*Math.PI));

		dy=(int)(sy+length*Math.sin(angle_n*Math.PI));
		g.setColor(Color.red);

		g.drawLine(sx-1, sy, dx-1, dy);
		g.drawLine(sx, sy, dx, dy);
		g.drawLine(sx+1, sy, dx+1, dy);
		g.drawImage(hook_image, dx-36, dy, null);
	}
	void paintself(Graphics g)
	{
		jud_collision();
		if(state==0)
		{
			if(angle_n<0.1)
				dir=1;
			else if(angle_n>0.9)
				dir=-1;
			angle_n+=dir*0.005;
			draw(g);
		}
		else if(state==1)
		{
			if(length<650)
			{
				length+=10;
				draw(g);
			}
			else state=2;
		}
		else if(state==2)
		{
			if(length>100)
			{
				length-=10;
				draw(g);
			}
			else state=0;
		}
		else if(state==3)
		{
			int speed_change=0;
			if(length>100)
			{
				length-=10;
				draw(g);
				for(Object obj:this.frame.objlist)
				{
					if(obj.is_catch==true)
					{
						speed_change=obj.m;
						obj.x=dx-(int)(0.5*obj.width);
						obj.y=dy;
						if(length<=100)
						{
							state=0;
							obj.x=-150;
							obj.y=-150;
							obj.is_catch=false;
							Bg.score_sum+=obj.score;
							Bg.potion_use=false;
					
						}
						if(Bg.potion_use==true)
						{
							if(obj.type==1)
							{
								speed_change=1;
							}
							else 
							{
								state=2;
								obj.x=-150;
								obj.y=-150;
								obj.is_catch=false;
								Bg.potion_use=false;
							}
						}
						
					}
					
				}
			}
			try {
				Thread.sleep(speed_change);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	void regame()
	{
		angle_n=0;
		length=100;
	}
}
