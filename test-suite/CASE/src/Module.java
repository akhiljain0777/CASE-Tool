import java.awt.Canvas;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Module extends Shape implements Serializable {
	ArrayList<Bubble> Bubbles=new ArrayList<Bubble>();
	Canvas next_level;
	Module prev_level;
	Rectangle2D.Double rect=new Rectangle2D.Double();
	
	public Module(){
		data.setType("Module");
	}

	public Rectangle2D.Double getRect() {
		return rect;
	}

	public void setRect(Rectangle2D.Double rect) {
		this.rect = rect;
	}
	
	public ArrayList<Bubble> getBubbles() {
		return Bubbles;
	}

	public void setBubbles(ArrayList<Bubble> bubbles) {
		this.Bubbles = bubbles;
	}
}