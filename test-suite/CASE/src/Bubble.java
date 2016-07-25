import java.awt.Canvas;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class Bubble extends Shape implements Serializable{
	public MainWindow next_level;
	boolean hasHierarchy=false;
	Module designModule;
	boolean hasModule=false;
	
	Ellipse2D.Double oval=new Ellipse2D.Double(0, 0, 0, 0);
	
	public Bubble(){
		data.setType("Bubble");
	}

	public Ellipse2D.Double getOval() {
		return oval;
	}
	public void setOval(Ellipse2D.Double oval) {
		this.oval = oval;
	}

	public Module getDesignModule() {
		return designModule;
	}

	public void setDesignModule(Module designModule) {
		this.designModule = designModule;
	}
	
	public boolean getHasModule(){
		return hasModule;
	}
	
	public void setHasModule(boolean bool){
		this.hasModule=bool;
	}
	
	public boolean hasHierarchy() {
		return hasHierarchy;
	}

	public void setHierarchy(boolean hasHierarchy) {
		this.hasHierarchy = hasHierarchy;
	}

}
