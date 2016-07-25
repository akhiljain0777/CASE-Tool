import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Shape implements Serializable {
	String color;
	DataType data=new DataType();
	int startA,startB,endA,endB;
	ArrayList<Arrow> arrowinList=new ArrayList<Arrow>(100);
	ArrayList<Arrow> arrowoutList=new ArrayList<Arrow>(100);
	
	public DataType getData() {
		return data;
	}
	public void setData(DataType data) {
		this.data = data;
	}
	public String getName() {
		return data.getName();
	}
	public void setName(String name) {
		data.setName(name);
	}
	public String getDescription() {
		return data.getDefinition();
	}
	public void setDescription(String description) {
		data.setDefinition(description);
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public ArrayList<Arrow> getArrowinList() {
		return arrowinList;
	}
	public void setArrowinList(ArrayList<Arrow> arrowinList) {
		this.arrowinList = arrowinList;
	}
	public ArrayList<Arrow> getArrowoutList() {
		return arrowoutList;
	}
	public void setArrowoutList(ArrayList<Arrow> arrowoutList) {
		this.arrowoutList = arrowoutList;
	}
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
	public String getType() {
		return data.type;
	}
	public void setType(String type) {
		data.setType(type);
	}
}
