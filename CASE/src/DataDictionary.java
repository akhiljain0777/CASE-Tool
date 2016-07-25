import java.io.Serializable;
import java.util.ArrayList;

public class DataDictionary implements Serializable {

	ArrayList<DataType> data=new ArrayList<DataType>(100);

	
	public ArrayList<DataType> getData() {
		return data;
	}

	public void setData(ArrayList<DataType> data) {
		this.data = data;
	}

	public void addDD(DataType dt){
		data.add(dt);
	}
	
	public void editDD(DataType dt1,DataType dt2){
		data.remove(dt1);
		data.add(dt2);
	}
	
	public void deleteDD(DataType dt){
		for(int i=0;i<data.size();i++){
			if(dt==data.get(i)){
				data.remove(i);
			}
		}
	}
	
	
}


