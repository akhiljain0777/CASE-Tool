import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChooseFile extends JFrame implements Serializable{

	private JPanel contentPane;

	private	JFileChooser chooser;
	private String loc;
	int returnval;

	private String filename;
	
	public ChooseFile(int index) {
		setBounds(100, 100, 427, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		chooser=new JFileChooser();
		FileNameExtensionFilter filter=new FileNameExtensionFilter("DFD(.ser)","ser");
		chooser.setFileFilter(filter);
		chooser.setBounds(5, 5, 406, 407);
		contentPane.add(chooser);
		if(index==0){
			returnval=chooser.showSaveDialog(this);
			chooser.setDialogTitle("Save as");
			setTitle("Save as");
		}
		else{
			returnval=chooser.showOpenDialog(this);
			chooser.setDialogTitle("Open");
			setTitle("Open");
		}
		if(returnval==JFileChooser.APPROVE_OPTION){
			setVisible(false);
			loc=chooser.getSelectedFile().getAbsolutePath();
			filename=chooser.getSelectedFile().getName();
		}
	}

	public String getFilename() {
		return filename;
	}

	public String getLoc() {
		return loc;
	}

	public int getReturnval() {
		return returnval;
	}

}
