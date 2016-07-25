import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Arrow  implements Serializable {
	Shape head,tail;
	DataType data=new DataType();
	Graphics2D geom;	
	int startA,startB,endA,endB;
	
	
	public int getStartA() {
		return startA;
	}
	public void setStartA(int startA) {
		this.startA = startA;
	}
	public int getStartB() {
		return startB;
	}
	public void setStartB(int startB) {
		this.startB = startB;
	}
	public int getEndA() {
		return endA;
	}
	public void setEndA(int endA) {
		this.endA = endA;
	}
	public int getEndB() {
		return endB;
	}
	public void setEndB(int endB) {
		this.endB = endB;
	}
	public void setName(String name){
		data.setName(name);
	}
	public void setDescription(String description){
		data.setDefinition(description);
	}
	public void setType(String type){
		data.setType(type);
	}
	public DataType getData() {
		return data;
	}
	public void setData(DataType data) {
		this.data = data;
	}
}
