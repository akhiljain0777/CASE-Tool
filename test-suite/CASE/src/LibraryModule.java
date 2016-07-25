import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class LibraryModule extends Shape implements Serializable {
	Rectangle2D.Double rect=new Rectangle2D.Double();

	public LibraryModule(){
		data.setType("LibraryModule");
	}
	
	public Rectangle2D.Double getRect() {
		return rect;
	}

	public void setRect(Rectangle2D.Double rect) {
		this.rect = rect;
	}
}
