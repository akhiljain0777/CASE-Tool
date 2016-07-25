import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class DataFrame extends JFrame implements Serializable{

	JPanel contentPane;
	JTextField txtName;
	JTextField txtDescription;
	public JButton btnSave;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public DataFrame() {
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblName.setBounds(10, 11, 136, 35);
		contentPane.add(lblName);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescription.setBounds(10, 57, 136, 35);
		contentPane.add(lblDescription);
		
		txtName = new JTextField();
		txtName.setBounds(156, 11, 268, 35);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtDescription = new JTextField();
		txtDescription.setBounds(156, 57, 268, 71);
		contentPane.add(txtDescription);
		txtDescription.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.setContentAreaFilled(false);
		btnSave.setOpaque(false);
		btnSave.setFocusPainted(false);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnSave.setContentAreaFilled(true);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnSave.setContentAreaFilled(false);
			}
		});
		btnSave.setBackground(new Color(192, 192, 192));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnSave.setBounds(140, 139, 154, 36);
		contentPane.add(btnSave);
	}
}
