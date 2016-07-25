import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import java.io.Serializable;

public class AssignList extends JFrame implements Serializable{

	private JPanel contentPane;
	public JComboBox comboBox;
	public JButton btnSelect;

	public AssignList(String[] names) {
		setBounds(100, 100, 450, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox(names);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(5, 5, 424, 29);
		contentPane.add(comboBox);
		
		btnSelect = new JButton("Select");
		btnSelect.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnSelect.setBounds(165, 204, 104, 29);
		contentPane.add(btnSelect);
	}
}
