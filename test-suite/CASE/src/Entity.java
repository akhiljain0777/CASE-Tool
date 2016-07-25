import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Entity extends Shape implements Serializable {
	Rectangle2D.Double rect=new Rectangle2D.Double();

	public Entity(){
		data.setType("Entity");
	}
	
	public Rectangle2D.Double getRect() {
		return rect;
	}

	public void setRect(Rectangle2D.Double rect) {
		this.rect = rect;
	}
}
