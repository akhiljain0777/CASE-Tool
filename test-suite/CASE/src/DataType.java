import java.io.Serializable;

public class DataType  implements Serializable {
	String name,definition,type;
	
	public DataType(){
		name=definition=type=null;
	}
	
	public DataType(String n,String description,String typ){
		name=n;
		definition= description;
		type=typ;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
