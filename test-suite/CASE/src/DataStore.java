import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class DataStore extends Shape implements Serializable {
	Rectangle2D.Double rect=new Rectangle2D.Double();
	
	public DataStore(){
		data.setType("DataStore");
	}

	public Rectangle2D.Double getRect() {
		return rect;
	}

	public void setRect(Rectangle2D.Double rect) {
		this.rect = rect;
	}
}
