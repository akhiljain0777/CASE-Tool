import java.io.Serializable;
import java.util.ArrayList;

public class DataFlowDiagram implements Serializable {
	private int level;
	ArrayList<Bubble> BubbleList=new ArrayList<Bubble>();
	ArrayList<Entity> EntityList=new ArrayList<Entity>();
	ArrayList<Module> ModuleList=new ArrayList<Module>();
	ArrayList<DataStore> DataStoreList=new ArrayList<DataStore>();
	ArrayList<LibraryModule> LibraryModuleList=new ArrayList<LibraryModule>();
	ArrayList<DFA>DFAList=new ArrayList<DFA>();
	ArrayList<DFA>DFA2List=new ArrayList<DFA>();
	ArrayList<CFA>CFAList=new ArrayList<CFA>();
	DataDictionary dataDictionary=new DataDictionary();
	
	public DataDictionary getDataDictionary() {
		return dataDictionary;
	}
	public void setDataDictionary(DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
	}
	public ArrayList<Bubble> getBubbleList() {
		return BubbleList;
	}
	public void setBubbleList(ArrayList<Bubble> bubbleList) {
		BubbleList = bubbleList;
	}
	public ArrayList<Entity> getEntityList() {
		return EntityList;
	}
	public void setEntityList(ArrayList<Entity> entityList) {
		EntityList = entityList;
	}
	public ArrayList<Module> getModuleList() {
		return ModuleList;
	}
	public void setModuleList(ArrayList<Module> moduleList) {
		ModuleList = moduleList;
	}
	public ArrayList<DataStore> getDataStoreList() {
		return DataStoreList;
	}
	public void setDataStoreList(ArrayList<DataStore> dataStoreList) {
		DataStoreList = dataStoreList;
	}
	public ArrayList<LibraryModule> getLibraryModuleList() {
		return LibraryModuleList;
	}
	public void setLibraryModuleList(ArrayList<LibraryModule> libraryModuleList) {
		LibraryModuleList = libraryModuleList;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public ArrayList<DFA> getDFAList() {
		return DFAList;
	}

	public void setDFAList(ArrayList<DFA> dFAList) {
		DFAList = dFAList;
	}

	public ArrayList<CFA> getCFAList() {
		return CFAList;
	}

	public void setCFAList(ArrayList<CFA> cFAList) {
		CFAList = cFAList;
	}

	public ArrayList<DFA> getDFA2List() {
		return DFA2List;
	}

	public void setDFA2List(ArrayList<DFA> dFA2List) {
		DFA2List = dFA2List;
	}

	
	
}
