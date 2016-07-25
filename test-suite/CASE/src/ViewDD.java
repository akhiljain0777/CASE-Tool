import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.JScrollBar;

public class ViewDD extends JFrame implements Serializable{

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ViewDD(DataDictionary dd) {
		String [][] data=new String[dd.getData().size()][3];
		for(int i=0;i<dd.getData().size();i++){
			data[i][0]=dd.getData().get(i).getName();
			data[i][1]=dd.getData().get(i).getDefinition();
			data[i][2]=dd.getData().get(i).getType();
		}
		
		setBounds(100, 100, 415, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		table = new JTable(data,
			new String[] {
				"Name", "Description", "Data Type"
			});
		JTableHeader header=table.getTableHeader();
		contentPane.setLayout(new BorderLayout());
		table.setBounds(10, 11, 379, 356);
		contentPane.add(table);
		contentPane.add(header, BorderLayout.NORTH);
		contentPane.add(table,BorderLayout.CENTER);
	}
}
