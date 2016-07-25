import java.awt.BasicStroke;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Canvas;
import javax.swing.JButton;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainWindow extends JFrame{

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	public MainWindow previous;
	private DataFlowDiagram dfd=new DataFlowDiagram();
	Canvas 	canvas = new Canvas();
	Canvas canvas2 = new Canvas();
	int type=-1;
	int startA,startB,endA,endB,movedA,movedB;
	int endi,endtype;
	Bubble bubble;
	Entity entity;
	DataStore dataStore;
	DFA dataFlow;
	Module module;
	LibraryModule lm;
	CFA controlFlow;
	DFA dataFlow2;
	private JPanel panel = new JPanel();
	private JPanel panel2 = new JPanel();
	private JButton btnEntity;
	private JButton btnBubble;
	private JButton btnDS;
	private JButton btnDFA;
	private JButton btnModule;
	private JButton btnLM;
	private JButton btnCFA;
	private JButton btnDFA2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainWindow frame=new MainWindow();
		frame.setVisible(true);
		frame.previous=null;
	}

	public MainWindow() {
		setTitle("Untitled.ser - CASE Tool");
		setForeground(UIManager.getColor("Button.background"));
		setBackground(UIManager.getColor("Button.background"));
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screensize);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JTabbedPane source=(JTabbedPane)arg0.getSource();
				if(source.getSelectedIndex()==0)drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
				else drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
				type=-1;
				btnModule.setSelected(false);
				btnLM.setSelected(false);
				btnDFA2.setSelected(false);
				btnCFA.setSelected(false);
				btnDFA.setSelected(false);
				btnBubble.setSelected(false);
				btnEntity.setSelected(false);
				btnDS.setSelected(false);
			}
		});
		tabbedPane.setBorder(null);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setOpaque(true);
		tabbedPane.setBackground(UIManager.getColor("Button.background"));
		tabbedPane.setBounds(0, 30, 1360, 709 );
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBorder(null);
		desktopPane.setAlignmentY(Component.TOP_ALIGNMENT);
		desktopPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		desktopPane.setBackground(UIManager.getColor("Button.background"));
		desktopPane.setBounds(0, 0, screensize.width, 30);
		contentPane.add(desktopPane);
		desktopPane.setLayout(null);

		JButton btnNew = new JButton("");
		btnNew.setBorder(null);
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNew.setContentAreaFilled(true);
			}
			public void mouseExited(MouseEvent arg0) {
				btnNew.setContentAreaFilled(false);
			}
		});
 		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newButtonClicked();
			}
		});

		
		btnNew.setBackground(new Color(192, 192, 192));
		btnNew.setAlignmentY(0.0f);
		btnNew.setLocation(0, 0);
		btnNew.setMinimumSize(new Dimension(30, 30));
		btnNew.setMaximumSize(new Dimension(30, 30));
		btnNew.setPreferredSize(new Dimension(30, 30));
		btnNew.setSize(new Dimension(30, 30));
		btnNew.setFocusPainted(false);
		btnNew.setContentAreaFilled(false);
		desktopPane.add(btnNew);
		btnNew.setIcon(new ImageIcon(MainWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/File.gif")));
		btnNew.setToolTipText("New");
		
		JButton btnOpen = new JButton("");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openButtonClicked();
			}
		});
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnOpen.setContentAreaFilled(true);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnOpen.setContentAreaFilled(false);
			}
		});
		btnOpen.setBorder(null);
		btnOpen.setBackground(new Color(192, 192, 192));
		btnOpen.setLocation(30, 0);
		btnOpen.setIcon(new ImageIcon(MainWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		btnOpen.setToolTipText("Open");
		btnOpen.setSize(new Dimension(30, 30));
		btnOpen.setPreferredSize(new Dimension(30, 30));
		btnOpen.setOpaque(false);
		btnOpen.setMinimumSize(new Dimension(30, 30));
		btnOpen.setMaximumSize(new Dimension(30, 30));
		btnOpen.setFocusPainted(false);
		btnOpen.setContentAreaFilled(false);
		desktopPane.add(btnOpen);
		
		JButton btnSave = new JButton("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveButtonClicked();
			}
		});
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
		btnSave.setBorder(null);
		btnSave.setBackground(new Color(192, 192, 192));
		btnSave.setLocation(60, 0);
		btnSave.setIcon(new ImageIcon(MainWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		btnSave.setToolTipText("Save");
		btnSave.setSize(new Dimension(30, 30));
		btnSave.setPreferredSize(new Dimension(30, 30));
		btnSave.setOpaque(false);
		btnSave.setMinimumSize(new Dimension(30, 30));
		btnSave.setMaximumSize(new Dimension(30, 30));
		btnSave.setFocusPainted(false);
		btnSave.setContentAreaFilled(false);
		desktopPane.add(btnSave);
		
		JButton btnPrint = new JButton("");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printButtonClicked();
			}
		});
		btnPrint.setBorder(null);
		btnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnPrint.setIcon(new ImageIcon("icons\\Print2.png"));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnPrint.setIcon(new ImageIcon("icons\\Print.png"));
			}
		});
		btnPrint.setIcon(new ImageIcon("icons\\Print.png"));
		btnPrint.setToolTipText("Print");
		btnPrint.setSize(new Dimension(30, 30));
		btnPrint.setPreferredSize(new Dimension(30, 30));
		btnPrint.setOpaque(false);
		btnPrint.setMinimumSize(new Dimension(30, 30));
		btnPrint.setMaximumSize(new Dimension(30, 30));
		btnPrint.setFocusPainted(false);
		btnPrint.setContentAreaFilled(false);
		btnPrint.setBackground(new Color(192, 192, 192));
		btnPrint.setBounds(90, 0, 30, 30);
		desktopPane.add(btnPrint);
		
		JButton btnDD = new JButton("");
		btnDD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewDD dd=new ViewDD(dfd.getDataDictionary());
				dd.setVisible(true);
			}
		});
		btnDD.setBorder(null);
		btnDD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnDD.setIcon(new ImageIcon("icons\\DataDictionary2.png"));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnDD.setIcon(new ImageIcon("icons\\DataDictionary.png"));
			}
		});
		btnDD.setIcon(new ImageIcon("icons\\DataDictionary.png"));
		btnDD.setToolTipText("View Data Dictionary");
		btnDD.setSize(new Dimension(30, 30));
		btnDD.setPreferredSize(new Dimension(30, 30));
		btnDD.setOpaque(false);
		btnDD.setMinimumSize(new Dimension(30, 30));
		btnDD.setMaximumSize(new Dimension(30, 30));
		btnDD.setFocusPainted(false);
		btnDD.setContentAreaFilled(false);
		btnDD.setBackground(new Color(192, 192, 192));
		btnDD.setBounds(120, 0, 30, 30);
		desktopPane.add(btnDD);
		
		JButton btnCBE = new JButton("");
		btnCBE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkBE();
			}
		});
		btnCBE.setBorder(null);
		btnCBE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnCBE.setIcon(new ImageIcon("icons\\BalancingErrors2.png"));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnCBE.setIcon(new ImageIcon("icons\\BalancingErrors.png"));
			}
		});
		btnCBE.setIcon(new ImageIcon("icons\\BalancingErrors.png"));
		btnCBE.setToolTipText("Check Balancing Errors");
		btnCBE.setSize(new Dimension(30, 30));
		btnCBE.setPreferredSize(new Dimension(30, 30));
		btnCBE.setOpaque(false);
		btnCBE.setMinimumSize(new Dimension(30, 30));
		btnCBE.setMaximumSize(new Dimension(30, 30));
		btnCBE.setFocusPainted(false);
		btnCBE.setContentAreaFilled(false);
		btnCBE.setBackground(new Color(192, 192, 192));
		btnCBE.setBounds(150, 0, 30, 30);
		desktopPane.add(btnCBE);
		
		JButton btnBack = new JButton("");
		btnBack.setIcon(new ImageIcon(MainWindow.class.getResource("/com/sun/javafx/scene/control/skin/caspian/fxvk-capslock-button.png")));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goBack();
			}
		});
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnBack.setContentAreaFilled(true);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnBack.setContentAreaFilled(false);
			}
		});
		btnBack.setToolTipText("Up one level in hierarchy");
		btnBack.setSize(new Dimension(30, 30));
		btnBack.setPreferredSize(new Dimension(30, 30));
		btnBack.setOpaque(false);
		btnBack.setMinimumSize(new Dimension(30, 30));
		btnBack.setMaximumSize(new Dimension(30, 30));
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorder(null);
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.setBounds(180, 0, 30, 30);
		desktopPane.add(btnBack);
		contentPane.add(tabbedPane);
		
		panel.setBorder(null);
		tabbedPane.addTab("Structured Analysis", null, panel, null);
		panel.setLayout(null);
		
		JDesktopPane desktopPane2 = new JDesktopPane();
		desktopPane2.setBorder(null);
		desktopPane2.setBounds(0, 0, 30, 681);
		panel.add(desktopPane2);
		desktopPane2.setAlignmentY(Component.TOP_ALIGNMENT);
		desktopPane2.setAlignmentX(Component.LEFT_ALIGNMENT);
		desktopPane2.setBackground(UIManager.getColor("Button.background"));
		desktopPane2.setLayout(null);
		
		btnBubble = new JButton("");
		btnBubble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				type=1;
			}
		});
		btnBubble.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnBubble.setIcon(new ImageIcon("icons\\Bubble2.png"));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnBubble.setIcon(new ImageIcon("icons\\Bubble.png"));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBubble.setSelected(true);
				btnEntity.setSelected(false);
				btnDFA.setSelected(false);
				btnDS.setSelected(false);
			}
		});
		btnBubble.setSelectedIcon(new ImageIcon("icons\\Bubble2.png"));
		btnBubble.setBorder(null);
		btnBubble.setIcon(new ImageIcon("icons\\Bubble.png"));
		btnBubble.setBackground(new Color(192, 192, 192));
		btnBubble.setLocation(0, 0);
		btnBubble.setContentAreaFilled(false);
		btnBubble.setToolTipText("Create Bubble");
		btnBubble.setSize(new Dimension(30, 30));
		btnBubble.setPreferredSize(new Dimension(30, 30));
		btnBubble.setMinimumSize(new Dimension(30, 30));
		btnBubble.setMaximumSize(new Dimension(30, 30));
		btnBubble.setFocusPainted(false);
		btnBubble.setSelected(false);
		desktopPane2.add(btnBubble);
		
		btnEntity = new JButton("");
		btnEntity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type=2;
			}
		});
		btnEntity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnEntity.setIcon(new ImageIcon(("icons/Entity2.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnEntity.setIcon(new ImageIcon(("icons/Entity.png")));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBubble.setSelected(false);
				btnEntity.setSelected(true);
				btnDFA.setSelected(false);
				btnDS.setSelected(false);
			}
		});
		btnEntity.setSelectedIcon(new ImageIcon(("icons/Entity2.png")));
		btnEntity.setBorder(null);
		btnEntity.setBackground(new Color(192, 192, 192));
		btnEntity.setLocation(0, 30);
		btnEntity.setIcon(new ImageIcon(("icons/Entity.png")));
		btnEntity.setToolTipText("Create Entity");
		btnEntity.setSize(new Dimension(30, 30));
		btnEntity.setPreferredSize(new Dimension(30, 30));
		btnEntity.setMinimumSize(new Dimension(30, 30));
		btnEntity.setMaximumSize(new Dimension(30, 30));
		btnEntity.setFocusPainted(false);
		btnEntity.setContentAreaFilled(false);
		desktopPane2.add(btnEntity);
		
		btnDS = new JButton("");
		btnDS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type=3;
			}
		});
		btnDS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnDS.setIcon(new ImageIcon(("icons/DataStore2.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnDS.setIcon(new ImageIcon(("icons/DataStore.png")));
			}
			
			public void mouseClicked(MouseEvent arg0) {
				btnBubble.setSelected(false);
				btnEntity.setSelected(false);
				btnDFA.setSelected(false);
				btnDS.setSelected(true);
			}
		});
		btnDS.setSelectedIcon(new ImageIcon(("icons/DataStore2.png")));
		btnDS.setBorder(null);
		btnDS.setBackground(new Color(192, 192, 192));
		btnDS.setLocation(0, 60);
		btnDS.setIcon(new ImageIcon(("icons/DataStore.png")));
		btnDS.setToolTipText("Create Data Store");
		btnDS.setSize(new Dimension(30, 30));
		btnDS.setPreferredSize(new Dimension(30, 30));
		btnDS.setMinimumSize(new Dimension(30, 30));
		btnDS.setMaximumSize(new Dimension(30, 30));
		btnDS.setFocusPainted(false);
		btnDS.setContentAreaFilled(false);
		desktopPane2.add(btnDS);
		
		btnDFA = new JButton("");
		btnDFA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				type=4;
			}
		});
		btnDFA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnDFA.setIcon(new ImageIcon(("icons/DataFlowArrow2.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnDFA.setIcon(new ImageIcon(("icons/DataFlowArrow.png")));
			}
			
			public void mouseClicked(MouseEvent arg0) {
				btnBubble.setSelected(false);
				btnEntity.setSelected(false);
				btnDFA.setSelected(true);
				btnDS.setSelected(false);
			}
		});
		btnDFA.setSelectedIcon(new ImageIcon(("icons/DataFlowArrow2.png")));
		btnDFA.setBorder(null);
		btnDFA.setBackground(new Color(192, 192, 192));
		btnDFA.setIcon(new ImageIcon(("icons/DataFlowArrow.png")));
		btnDFA.setLocation(0, 90);
		btnDFA.setToolTipText("Create Data Flow Arrow");
		btnDFA.setSize(new Dimension(30, 30));
		btnDFA.setPreferredSize(new Dimension(30, 30));
		btnDFA.setMinimumSize(new Dimension(30, 30));
		btnDFA.setMaximumSize(new Dimension(30, 30));
		btnDFA.setFocusPainted(false);
		btnDFA.setContentAreaFilled(false);
		desktopPane2.add(btnDFA);
		
		canvas.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(30, 0, 1325, 681);
		panel.add(canvas);
		
		panel2.setBorder(null);
		tabbedPane.addTab("Structured Design", null, panel2, null);
		panel2.setLayout(null);
		
		JDesktopPane desktopPane3 = new JDesktopPane();
		desktopPane3.setBorder(null);
		desktopPane3.setBounds(0, 0, 30, 681);
		panel2.add(desktopPane3);
		desktopPane3.setAlignmentY(Component.TOP_ALIGNMENT);
		desktopPane3.setAlignmentX(Component.LEFT_ALIGNMENT);
		desktopPane3.setBackground(UIManager.getColor("Button.background"));
		desktopPane3.setLayout(null);
		
		btnModule = new JButton("");
		btnModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type=5;
			}
		});
		btnModule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnModule.setIcon(new ImageIcon(("icons/Module2.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnModule.setIcon(new ImageIcon(("icons/Module.png")));
			}
			
			public void mouseClicked(MouseEvent arg0) {
				btnModule.setSelected(true);
				btnLM.setSelected(false);
				btnDFA2.setSelected(false);
				btnCFA.setSelected(false);
			}
		});
		btnModule.setSelectedIcon(new ImageIcon(("icons/Module2.png")));
		btnModule.setBorder(null);
		btnModule.setBackground(new Color(192, 192, 192));
		btnModule.setLocation(0, 0);
		btnModule.setContentAreaFilled(false);
		btnModule.setIcon(new ImageIcon(("icons/Module.png")));
		btnModule.setToolTipText("Create Module");
		btnModule.setSize(new Dimension(30, 30));
		btnModule.setPreferredSize(new Dimension(30, 30));
		btnModule.setMinimumSize(new Dimension(30, 30));
		btnModule.setMaximumSize(new Dimension(30, 30));
		btnModule.setFocusPainted(false);
		desktopPane3.add(btnModule);
		
		btnLM = new JButton("");
		btnLM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type=6;
			}
		});
		btnLM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnLM.setIcon(new ImageIcon(("icons/LibraryModule2.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnLM.setIcon(new ImageIcon(("icons/LibraryModule.png")));
			}
			
			public void mouseClicked(MouseEvent arg0) {
				btnModule.setSelected(false);
				btnLM.setSelected(true);
				btnDFA2.setSelected(false);
				btnCFA.setSelected(false);
			}
		});
		btnLM.setSelectedIcon(new ImageIcon(("icons/LibraryModule2.png")));
		btnLM.setBorder(null);
		btnLM.setBackground(new Color(192, 192, 192));
		btnLM.setLocation(0, 30);
		btnLM.setIcon(new ImageIcon(("icons/LibraryModule.png")));
		btnLM.setToolTipText("Create Library Module");
		btnLM.setSize(new Dimension(30, 30));
		btnLM.setPreferredSize(new Dimension(30, 30));
		btnLM.setMinimumSize(new Dimension(30, 30));
		btnLM.setMaximumSize(new Dimension(30, 30));
		btnLM.setFocusPainted(false);
		btnLM.setContentAreaFilled(false);
		desktopPane3.add(btnLM);
		
		btnCFA = new JButton("");
		btnCFA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type=7;
			}
		});
		btnCFA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnCFA.setIcon(new ImageIcon(("icons/ControlFlowArrow2.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnCFA.setIcon(new ImageIcon(("icons/ControlFlowArrow.png")));
			}
			
			public void mouseClicked(MouseEvent arg0) {
				btnModule.setSelected(false);
				btnLM.setSelected(false);
				btnDFA2.setSelected(false);
				btnCFA.setSelected(true);
			}
		});
		btnCFA.setSelectedIcon(new ImageIcon(("icons/ControlFlowArrow2.png")));
		btnCFA.setBorder(null);
		btnCFA.setBackground(new Color(192, 192, 192));
		btnCFA.setLocation(0, 60);
		btnCFA.setIcon(new ImageIcon(("icons/ControlFlowArrow.png")));
		btnCFA.setToolTipText("Create Control Flow Arrow");
		btnCFA.setSize(new Dimension(30, 30));
		btnCFA.setPreferredSize(new Dimension(30, 30));
		btnCFA.setMinimumSize(new Dimension(30, 30));
		btnCFA.setMaximumSize(new Dimension(30, 30));
		btnCFA.setFocusPainted(false);
		btnCFA.setContentAreaFilled(false);
		desktopPane3.add(btnCFA);
		
		btnDFA2 = new JButton("");
		btnDFA2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type=8;
			}
		});
		btnDFA2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnDFA2.setIcon(new ImageIcon(("icons/DataFlowArrow2.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnDFA2.setIcon(new ImageIcon(("icons/DataFlowArrow.png")));
			}
			public void mouseClicked(MouseEvent arg0) {
				btnModule.setSelected(false);
				btnLM.setSelected(false);
				btnDFA2.setSelected(true);
				btnCFA.setSelected(false);
			}
		});
		btnDFA2.setSelectedIcon(new ImageIcon(("icons/DataFlowArrow2.png")));
		btnDFA2.setBorder(null);
		btnDFA2.setBackground(new Color(192, 192, 192));
		btnDFA2.setIcon(new ImageIcon(("icons/DataFlowArrow.png")));
		btnDFA2.setLocation(0, 90);
		btnDFA2.setToolTipText("Create Data Flow Arrow");
		btnDFA2.setSize(new Dimension(30, 30));
		btnDFA2.setPreferredSize(new Dimension(30, 30));
		btnDFA2.setMinimumSize(new Dimension(30, 30));
		btnDFA2.setMaximumSize(new Dimension(30, 30));
		btnDFA2.setFocusPainted(false);
		btnDFA2.setContentAreaFilled(false);
		desktopPane3.add(btnDFA2);
		
		canvas2.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		canvas2.setBackground(Color.WHITE);
		canvas2.setBounds(30, 0, 1325, 681);
		panel2.add(canvas2);
		
		
		
		
		canvas.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent evt){
				if(SwingUtilities.isRightMouseButton(evt)){
					
				}
				else{
					startA=evt.getX();
					startB=evt.getY();
				}
			}
			public void mouseReleased(MouseEvent evt2){
				if(SwingUtilities.isRightMouseButton(evt2)){
					endA=evt2.getX();
					endB=evt2.getY();
					rightMouseClicked(evt2,(Graphics2D)canvas.getGraphics());
				}
				else{
					endA=evt2.getX();
					endB=evt2.getY();
					Graphics2D g=(Graphics2D)canvas.getGraphics();
					g.setStroke(new BasicStroke(4));
					g.setColor(Color.BLUE);
					if(type==1)drawBubble(g);
					else if(type==2)drawEntity(g);
					else if(type==3)drawDataStore(g);
					else if(type==4)drawDFA(g);
				}
			}
			public void mouseEntered(MouseEvent evt3){
				drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
			}
		});
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt4){
				drawSystemAnalysis(dfd,0,(Graphics2D)canvas.getGraphics());
			}
		});
		canvas2.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent evt){
				if(SwingUtilities.isRightMouseButton(evt)){
					
				}
				else{
					startA=evt.getX();
					startB=evt.getY();
				}
			}
			public void mouseReleased(MouseEvent evt2){
				if(SwingUtilities.isRightMouseButton(evt2)){
					endA=evt2.getX();
					endB=evt2.getY();
					rightMouseClicked2(evt2,(Graphics2D)canvas2.getGraphics());
				}
				
				else{
					endA=evt2.getX();
					endB=evt2.getY();
					Graphics2D g2=(Graphics2D)canvas2.getGraphics();
					g2.setStroke(new BasicStroke(4));
					g2.setColor(Color.BLUE);
					if(type==5)drawModule(g2);
					else if(type==6)drawLM(g2);
					else if(type==7)drawCFA(g2);
					else if(type==8)drawDFA2(g2);
				}
			}
		});
		canvas2.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt4){
				drawSystemDesign(dfd,0,(Graphics2D)canvas2.getGraphics());
			}
		});
	}
	
	
	public void newButtonClicked(){
		int result=JOptionPane.showOptionDialog(null,"Do you want to save the changes ?","CASE Tool",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
		if(result==0){
			saveButtonClicked();
			setVisible(false);
			MainWindow frame=new MainWindow();
			frame.setVisible(true);
			frame.previous=null;
		}
		else if(result==1){
			setVisible(false);
			MainWindow frame=new MainWindow();
			frame.setVisible(true);
			frame.previous=null;
		}
	}
	
	public void openButtonClicked(){
		int result=JOptionPane.showOptionDialog(null,"Do you want to save the changes ?","CASE Tool",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
		if(result==0){
			saveButtonClicked();
			ChooseFile choose=new ChooseFile(1);
			choose.setTitle("Open");
			if(choose.getReturnval()==JFileChooser.APPROVE_OPTION){
				try {
					ObjectInputStream instream=new ObjectInputStream(new FileInputStream(choose.getLoc()));
					setVisible(false);
					MainWindow frame=new MainWindow();
					frame.setVisible(true);
					frame.dfd=(DataFlowDiagram)instream.readObject();
					frame.previous=null;
					frame.setTitle(choose.getFilename());
					frame.drawSystemAnalysis(frame.dfd,1,(Graphics2D)canvas.getGraphics());
					instream.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"No such file exist");	
				}
				catch (ClassNotFoundException e) {
				}
			}
		}
		else if(result==1){
			ChooseFile choose=new ChooseFile(1);
			choose.setTitle("Open");
			if(choose.getReturnval()==JFileChooser.APPROVE_OPTION){
				try {
					ObjectInputStream instream=new ObjectInputStream(new FileInputStream(choose.getLoc()));
					setVisible(false);
					MainWindow frame=new MainWindow();
					frame.setVisible(true);
					frame.dfd=(DataFlowDiagram)instream.readObject();
					frame.previous=null;
					frame.setTitle(choose.getFilename());
					frame.drawSystemAnalysis(frame.dfd,1,(Graphics2D)canvas.getGraphics());
					instream.close();
				
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"No such file exist");	
				}
				catch (ClassNotFoundException e) {
				}
			}
		}
	}
	
	public void saveButtonClicked(){
		ChooseFile choose=new ChooseFile(0);
		choose.setTitle("Save");
		if(choose.getReturnval()==JFileChooser.APPROVE_OPTION){
			save(this,choose.getLoc());
			setTitle(choose.getFilename());
		}
	}

	public void save(MainWindow f,String filename){
		if(f.previous!=null){
			save(f.previous,filename);
			return;
		}
		else{
			try {
				ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(filename+".ser"));
				out.writeObject(f.dfd); 
				this.setVisible(false);
				MainWindow frame=new MainWindow();
				frame.setVisible(true);
				frame.dfd=this.dfd;
				frame.previous=this.previous;
				frame.drawSystemAnalysis(frame.dfd,1,(Graphics2D)canvas.getGraphics());
				out.close();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
	}
	
	public void printButtonClicked(){
		PrinterJob job = PrinterJob.getPrinterJob();
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PageFormat pf = job.pageDialog(aset);
        job.setPrintable(new Printable(){
	    	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
	    		if(page>0){
	    			return NO_SUCH_PAGE;
	    		}
    			Graphics2D g2d=(Graphics2D)g;
    	        g2d.translate(pf.getImageableX(), pf.getImageableY());
    	        if(tabbedPane.getSelectedIndex()==0)drawSystemAnalysis2(dfd,0,g2d);
    	        else drawSystemDesign2(dfd,0,g2d);
    			return Printable.PAGE_EXISTS;
	    	}
		});
        if (job.printDialog(aset)) {
            try {
            	job.print(aset);
            } catch (PrinterException ex) {
             /* The job did not successfully complete */
            }
        }
	}
	
	public void drawBubble(Graphics2D g){
		int temp;
		if(startA>endA){
			temp=startA;
			startA=endA;
			endA=temp;
		}
		if(startB>endB){
			temp=startB;
			startB=endB;
			endB=temp;
		}
		bubble=new Bubble();
		bubble.setStartA(startA);
		bubble.setStartB(startB);
		bubble.setEndA(endA);
		bubble.setEndB(endB);
		bubble.setOval(new Ellipse2D.Double(startA, startB, endA-startA, endB-startB));
		g.draw(bubble.getOval());
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				String description=dataFrame.txtDescription.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					bubble.setName(name);
					bubble.setDescription(description);
					dfd.getBubbleList().add(bubble);
					dfd.getDataDictionary().addDD(bubble.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(bubble.getName(), (int)(bubble.getOval().getCenterX()), (int)(bubble.getOval().getCenterY()));
					g.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public void drawEntity(Graphics2D g){
		int temp;
		if(startA>endA){
			temp=startA;
			startA=endA;
			endA=temp;
		}
		if(startB>endB){
			temp=startB;
			startB=endB;
			endB=temp;
		}
		entity=new Entity();
		entity.setStartA(startA);
		entity.setStartB(startB);
		entity.setEndA(endA);
		entity.setEndB(endB);
		entity.setRect(new Rectangle2D.Double((startA), startB, endA-startA, endB-startB));
		g.draw(entity.getRect());
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				String description=dataFrame.txtDescription.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					entity.setName(name);
					entity.setDescription(description);
					dfd.getEntityList().add(entity);
					dfd.getDataDictionary().addDD(entity.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(entity.getName(), (int)(entity.getRect().getCenterX()), (int)(entity.getRect().getCenterY()));
					g.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public void drawDataStore(Graphics2D g){
		int temp;
		if(startA>endA){
			temp=startA;
			startA=endA;
			endA=temp;
		}
		if(startB>endB){
			temp=startB;
			startB=endB;
			endB=temp;
		}
		dataStore=new DataStore();
		dataStore.setStartA(startA);
		dataStore.setStartB(startB);
		dataStore.setEndA(endA);
		dataStore.setEndB(endB);
		dataStore.setRect(new Rectangle2D.Double((startA), startB, endA-startA, endB-startB));
		g.drawLine(dataStore.getStartA(), dataStore.getStartB(), dataStore.getEndA(), dataStore.getStartB());
		g.drawLine(dataStore.getStartA(), dataStore.getStartB(), dataStore.getStartA(), dataStore.getEndB());
		g.drawLine(dataStore.getStartA(), dataStore.getEndB(), dataStore.getEndA(), dataStore.getEndB());
		g.drawLine((3*dataStore.getStartA()+dataStore.getEndA())/4, dataStore.getStartB(),(3*dataStore.getStartA()+dataStore.getEndA())/4,dataStore.getEndB());
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				String description=dataFrame.txtDescription.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					dataStore.setName(name);
					dataStore.setDescription(description);
					dfd.getDataStoreList().add(dataStore);
					dfd.getDataDictionary().addDD(dataStore.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(dataStore.getName(), (int)(dataStore.getRect().getCenterX()), (int)(dataStore.getRect().getCenterY()));
					g.setColor(Color.BLUE);
				}
			}
		});
	}

	public void drawDFA1(Graphics2D g){
		int starti=-1,endi=-1,starttype=0,endtype=0;
		for(int i=0;i<dfd.getBubbleList().size();i++){
			if(dfd.getBubbleList().get(i).getOval().contains(startA, startB)){
				starti=i;
				starttype=1;
				break;
			}
		}
		if(starttype==0){
			for(int i=0;i<dfd.getEntityList().size();i++){
				if(dfd.getEntityList().get(i).getRect().contains(startA, startB)){
					starti=i;
					starttype=2;
					break;
				}
			}
		}
		if(starttype==0){
			for(int i=0;i<dfd.getDataStoreList().size();i++){
				if(dfd.getDataStoreList().get(i).getRect().contains(startA, startB)){
					starti=i;
					starttype=3;
					break;
				}
			}
		}
		for(int i=0;i<dfd.getBubbleList().size();i++){
			if(dfd.getBubbleList().get(i).getOval().contains(endA, endB)){
				endi=i;
				endtype=1;
				break;
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getEntityList().size();i++){
				if(dfd.getEntityList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=2;
					break;
				}
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getDataStoreList().size();i++){
				if(dfd.getDataStoreList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=3;
					break;
				}
			}
		}
		dataFlow=new DFA();
		if(starttype==endtype&&starti==endi){
			if(starttype==0)return;
			if(starttype==1){
				double[] arrayPt=new double[4];
				arrayPt=jEllipse(dfd.getBubbleList().get(starti).getOval(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(arrayPt[0]));
				dataFlow.setStartB((int)(arrayPt[1]));
				dataFlow.setEndA((int)(arrayPt[2]));
				dataFlow.setEndB((int)(arrayPt[3]));
				dfd.getBubbleList().get(starti).getArrowoutList().add(dataFlow);
				dfd.getBubbleList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==2){
				dataFlow.setStartA(startA);
				dataFlow.setStartB((int)(dfd.getEntityList().get(starti).getRect().getY()));
				dataFlow.setEndA(endA);
				dataFlow.setEndB((int)(dfd.getEntityList().get(starti).getRect().getY()+dfd.getEntityList().get(starti).getRect().getHeight()));
				dfd.getEntityList().get(starti).getArrowoutList().add(dataFlow);
				dfd.getEntityList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==3){
				dataFlow.setStartA(startA);
				dataFlow.setStartB((int)(dfd.getDataStoreList().get(starti).getRect().getY()));
				dataFlow.setEndA(endA);
				dataFlow.setEndB((int)(dfd.getDataStoreList().get(starti).getRect().getY()+dfd.getDataStoreList().get(starti).getRect().getHeight()));
				dfd.getDataStoreList().get(starti).getArrowoutList().add(dataFlow);
				dfd.getDataStoreList().get(endi).getArrowinList().add(dataFlow);
			}
		}
		else{
			if(starttype==1){
				Point2D.Double pt=iEllipse(dfd.getBubbleList().get(starti).getOval(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(pt.getX()));
				dataFlow.setStartB((int)(pt.getY()));
				dfd.getBubbleList().get(starti).getArrowoutList().add(dataFlow);
			}
			if(endtype==1){
				Point2D.Double pt=iEllipse(dfd.getBubbleList().get(endi).getOval(),startA,startB,endA,endB);
				dataFlow.setEndA((int)(pt.getX()));
				dataFlow.setEndB((int)(pt.getY()));
				dfd.getBubbleList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==2){
				Point2D.Double pt=iRectangle(dfd.getEntityList().get(starti).getRect(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(pt.getX()));
				dataFlow.setStartB((int)(pt.getY()));
				dfd.getEntityList().get(starti).getArrowoutList().add(dataFlow);
			}
			if(endtype==2){
				Point2D.Double pt=iRectangle(dfd.getEntityList().get(endi).getRect(),startA,startB,endA,endB);
				dataFlow.setEndA((int)(pt.getX()));
				dataFlow.setEndB((int)(pt.getY()));
				dfd.getEntityList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==3){
				Point2D.Double pt=iRectangle(dfd.getDataStoreList().get(starti).getRect(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(pt.getX()));
				dataFlow.setStartB((int)(pt.getY()));
				dfd.getDataStoreList().get(starti).getArrowoutList().add(dataFlow);
			}
			if(endtype==3){
				Point2D.Double pt=iRectangle(dfd.getDataStoreList().get(endi).getRect(),startA,startB,endA,endB);
				dataFlow.setEndA((int)(pt.getX()));
				dataFlow.setEndB((int)(pt.getY()));
				dfd.getDataStoreList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==0){
				dataFlow.setStartA(startA);
				dataFlow.setStartB(startB);
				dataFlow.hasTail=false;
			}
			if(endtype==0){
				dataFlow.setEndA(endA);
				dataFlow.setEndB(endB);
				dataFlow.hasHead=false;
			}
		}
		g.setStroke(new BasicStroke(2));
		g.drawLine(dataFlow.getStartA(), dataFlow.getStartB(), dataFlow.getEndA(), dataFlow.getEndB());
		double slope=(double)(dataFlow.getEndB()-dataFlow.getStartB())/(double)(dataFlow.getEndA()-dataFlow.getStartA());
		double thita=Math.atan(slope);
		if(dataFlow.getEndA()<dataFlow.getStartA())thita+=Math.PI;
		g.drawLine(dataFlow.getEndA(),dataFlow.getEndB(), (int)(dataFlow.getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(dataFlow.getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
		g.drawLine(dataFlow.getEndA(),dataFlow.getEndB(), (int)(dataFlow.getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(dataFlow.getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
		g.setStroke(new BasicStroke(4));
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					dataFlow.setData(new DataType(dataFrame.txtName.getText(),dataFrame.txtDescription.getText(),"DFA"));
					dfd.getDFAList().add(dataFlow);
					dfd.getDataDictionary().addDD(dataFlow.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(dataFlow.getData().getName(), (dataFlow.getStartA()+dataFlow.getEndA())/2,(dataFlow.getStartB()+dataFlow.getEndB())/2);
					g.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public void drawDFA(Graphics2D g){
		if(this.previous!=null)drawDFA1(g);
		int starti=-1,endi=-1,starttype=0,endtype=0;
		for(int i=0;i<dfd.getBubbleList().size();i++){
			if(dfd.getBubbleList().get(i).getOval().contains(startA, startB)){
				starti=i;
				starttype=1;
				break;
			}
		}
		if(starttype==0){
			for(int i=0;i<dfd.getEntityList().size();i++){
				if(dfd.getEntityList().get(i).getRect().contains(startA, startB)){
					starti=i;
					starttype=2;
					break;
				}
			}
		}
		if(starttype==0){
			for(int i=0;i<dfd.getDataStoreList().size();i++){
				if(dfd.getDataStoreList().get(i).getRect().contains(startA, startB)){
					starti=i;
					starttype=3;
					break;
				}
			}
		}
		if(starttype==0)return;
		for(int i=0;i<dfd.getBubbleList().size();i++){
			if(dfd.getBubbleList().get(i).getOval().contains(endA, endB)){
				endi=i;
				endtype=1;
				break;
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getEntityList().size();i++){
				if(dfd.getEntityList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=2;
					break;
				}
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getDataStoreList().size();i++){
				if(dfd.getDataStoreList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=3;
					break;
				}
			}
		}
		if(endtype==0)return;
		dataFlow=new DFA();
		if(starttype==endtype&&starti==endi){
			if(starttype==1){
				double[] arrayPt=new double[4];
				arrayPt=jEllipse(dfd.getBubbleList().get(starti).getOval(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(arrayPt[0]));
				dataFlow.setStartB((int)(arrayPt[1]));
				dataFlow.setEndA((int)(arrayPt[2]));
				dataFlow.setEndB((int)(arrayPt[3]));
				dfd.getBubbleList().get(starti).getArrowoutList().add(dataFlow);
				dfd.getBubbleList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==2){
				dataFlow.setStartA(startA);
				dataFlow.setStartB((int)(dfd.getEntityList().get(starti).getRect().getY()));
				dataFlow.setEndA(endA);
				dataFlow.setEndB((int)(dfd.getEntityList().get(starti).getRect().getY()+dfd.getEntityList().get(starti).getRect().getHeight()));
				dfd.getEntityList().get(starti).getArrowoutList().add(dataFlow);
				dfd.getEntityList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==3){
				dataFlow.setStartA(startA);
				dataFlow.setStartB((int)(dfd.getDataStoreList().get(starti).getRect().getY()));
				dataFlow.setEndA(endA);
				dataFlow.setEndB((int)(dfd.getDataStoreList().get(starti).getRect().getY()+dfd.getDataStoreList().get(starti).getRect().getHeight()));
				dfd.getDataStoreList().get(starti).getArrowoutList().add(dataFlow);
				dfd.getDataStoreList().get(endi).getArrowinList().add(dataFlow);
			}
		}
		else{
			if(starttype==1){
				Point2D.Double pt=iEllipse(dfd.getBubbleList().get(starti).getOval(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(pt.getX()));
				dataFlow.setStartB((int)(pt.getY()));
				dfd.getBubbleList().get(starti).getArrowoutList().add(dataFlow);
			}
			if(endtype==1){
				Point2D.Double pt=iEllipse(dfd.getBubbleList().get(endi).getOval(),startA,startB,endA,endB);
				dataFlow.setEndA((int)(pt.getX()));
				dataFlow.setEndB((int)(pt.getY()));
				dfd.getBubbleList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==2){
				Point2D.Double pt=iRectangle(dfd.getEntityList().get(starti).getRect(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(pt.getX()));
				dataFlow.setStartB((int)(pt.getY()));
				dfd.getEntityList().get(starti).getArrowoutList().add(dataFlow);
			}
			if(endtype==2){
				Point2D.Double pt=iRectangle(dfd.getEntityList().get(endi).getRect(),startA,startB,endA,endB);
				dataFlow.setEndA((int)(pt.getX()));
				dataFlow.setEndB((int)(pt.getY()));
				dfd.getEntityList().get(endi).getArrowinList().add(dataFlow);
			}
			if(starttype==3){
				Point2D.Double pt=iRectangle(dfd.getDataStoreList().get(starti).getRect(),startA,startB,endA,endB);
				dataFlow.setStartA((int)(pt.getX()));
				dataFlow.setStartB((int)(pt.getY()));
				dfd.getDataStoreList().get(starti).getArrowoutList().add(dataFlow);
			}
			if(endtype==3){
				Point2D.Double pt=iRectangle(dfd.getDataStoreList().get(endi).getRect(),startA,startB,endA,endB);
				dataFlow.setEndA((int)(pt.getX()));
				dataFlow.setEndB((int)(pt.getY()));
				dfd.getDataStoreList().get(endi).getArrowinList().add(dataFlow);
			}
		}
		g.setStroke(new BasicStroke(2));
		g.drawLine(dataFlow.getStartA(), dataFlow.getStartB(), dataFlow.getEndA(), dataFlow.getEndB());
		double slope=(double)(dataFlow.getEndB()-dataFlow.getStartB())/(double)(dataFlow.getEndA()-dataFlow.getStartA());
		double thita=Math.atan(slope);
		if(dataFlow.getEndA()<dataFlow.getStartA())thita+=Math.PI;
		g.drawLine(dataFlow.getEndA(),dataFlow.getEndB(), (int)(dataFlow.getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(dataFlow.getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
		g.drawLine(dataFlow.getEndA(),dataFlow.getEndB(), (int)(dataFlow.getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(dataFlow.getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
		g.setStroke(new BasicStroke(4));
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					dataFlow.setData(new DataType(dataFrame.txtName.getText(),dataFrame.txtDescription.getText(),"DFA"));
					dfd.getDFAList().add(dataFlow);
					dfd.getDataDictionary().addDD(dataFlow.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(dataFlow.getData().getName(), (dataFlow.getStartA()+dataFlow.getEndA())/2,(dataFlow.getStartB()+dataFlow.getEndB())/2);
					g.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public void drawModule(Graphics2D g){
		int temp;
		if(startA>endA){
			temp=startA;
			startA=endA;
			endA=temp;
		}
		if(startB>endB){
			temp=startB;
			startB=endB;
			endB=temp;
		}
		module=new Module();
		module.setStartA(startA);
		module.setStartB(startB);
		module.setEndA(endA);
		module.setEndB(endB);
		module.setRect(new Rectangle2D.Double((startA), startB, endA-startA, endB-startB));
		g.draw(module.getRect());
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				String description=dataFrame.txtDescription.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					module.setName(name);
					module.setDescription(description);
					dfd.getModuleList().add(module);
					dfd.getDataDictionary().addDD(module.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(module.getName(), (int)(module.getRect().getCenterX()), (int)(module.getRect().getCenterY()));
					g.setColor(Color.BLUE);
				}
			}
		});
	}

	public void drawLM(Graphics2D g){
		int temp;
		if(startA>endA){
			temp=startA;
			startA=endA;
			endA=temp;
		}
		if(startB>endB){
			temp=startB;
			startB=endB;
			endB=temp;
		}
		lm=new LibraryModule();
		lm.setStartA(startA);
		lm.setStartB(startB);
		lm.setEndA(endA);
		lm.setEndB(endB);
		g.setStroke(new BasicStroke(1));
		g.draw(new Rectangle2D.Double((startA+2), startB+2, endA-startA-4, endB-startB-4));
		lm.setRect(new Rectangle2D.Double((startA), startB, endA-startA, endB-startB));
		g.draw(lm.getRect());
		g.setStroke(new BasicStroke(4));
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				String description=dataFrame.txtDescription.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					lm.setName(name);
					lm.setDescription(description);
					dfd.getLibraryModuleList().add(lm);
					dfd.getDataDictionary().addDD(lm.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(lm.getName(), (int)(lm.getRect().getCenterX()), (int)(lm.getRect().getCenterY()));
					g.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public void drawCFA(Graphics2D g){
		int starti=-1,endi=-1,starttype=0,endtype=0;
		for(int i=0;i<dfd.getModuleList().size();i++){
			if(dfd.getModuleList().get(i).getRect().contains(startA, startB)){
				starti=i;
				starttype=1;
				break;
			}
		}
		if(starttype==0){
			for(int i=0;i<dfd.getLibraryModuleList().size();i++){
				if(dfd.getLibraryModuleList().get(i).getRect().contains(startA, startB)){
					starti=i;
					starttype=2;
					break;
				}
			}
		}
		if(starttype==0)return;
		for(int i=0;i<dfd.getModuleList().size();i++){
			if(dfd.getModuleList().get(i).getRect().contains(endA, endB)){
				endi=i;
				endtype=1;
				break;
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getLibraryModuleList().size();i++){
				if(dfd.getLibraryModuleList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=2;
					break;
				}
			}
		}
		if(endtype==0)return;
		controlFlow=new CFA();
		controlFlow.setEndA(endA);
		controlFlow.setEndB(endB);
		if(starttype==endtype&&starti==endi){
			if(starttype==1){
				controlFlow.setStartA(startA);
				controlFlow.setStartB((int)(dfd.getModuleList().get(starti).getRect().getY()));
				controlFlow.setEndA(endA);
				controlFlow.setEndB((int)(dfd.getModuleList().get(starti).getRect().getY()+dfd.getModuleList().get(starti).getRect().getHeight()));
				dfd.getModuleList().get(starti).getArrowoutList().add(controlFlow);
				dfd.getModuleList().get(endi).getArrowinList().add(controlFlow);
			}
			if(starttype==2){
				controlFlow.setStartA(startA);
				controlFlow.setStartB((int)(dfd.getLibraryModuleList().get(starti).getRect().getY()));
				controlFlow.setEndA(endA);
				controlFlow.setEndB((int)(dfd.getLibraryModuleList().get(starti).getRect().getY()+dfd.getLibraryModuleList().get(starti).getRect().getHeight()));
				dfd.getLibraryModuleList().get(starti).getArrowoutList().add(controlFlow);
				dfd.getLibraryModuleList().get(endi).getArrowinList().add(controlFlow);
			}
		}
		else{
			if(starttype==1){
				Point2D.Double pt=iRectangle(dfd.getModuleList().get(starti).getRect(),startA,startB,endA,endB);
				controlFlow.setStartA((int)(pt.getX()));
				controlFlow.setStartB((int)(pt.getY()));
				dfd.getModuleList().get(starti).getArrowoutList().add(controlFlow);
			}
			if(endtype==1){
				Point2D.Double pt=iRectangle(dfd.getModuleList().get(endi).getRect(),startA,startB,endA,endB);
				controlFlow.setEndA((int)(pt.getX()));
				controlFlow.setEndB((int)(pt.getY()));
				dfd.getModuleList().get(endi).getArrowinList().add(controlFlow);
			}
			if(starttype==2){
				Point2D.Double pt=iRectangle(dfd.getLibraryModuleList().get(starti).getRect(),startA,startB,endA,endB);
				controlFlow.setStartA((int)(pt.getX()));
				controlFlow.setStartB((int)(pt.getY()));
				dfd.getLibraryModuleList().get(starti).getArrowoutList().add(controlFlow);
			}
			if(endtype==2){
				Point2D.Double pt=iRectangle(dfd.getLibraryModuleList().get(endi).getRect(),startA,startB,endA,endB);
				controlFlow.setEndA((int)(pt.getX()));
				controlFlow.setEndB((int)(pt.getY()));
				dfd.getLibraryModuleList().get(endi).getArrowinList().add(controlFlow);
			}
		}
		g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));		
		g.drawLine(controlFlow.getStartA(), controlFlow.getStartB(), controlFlow.getEndA(), controlFlow.getEndB());
		double slope=(double)(controlFlow.getEndB()-controlFlow.getStartB())/(double)(controlFlow.getEndA()-controlFlow.getStartA());
		double thita=Math.atan(slope);
		if(controlFlow.getEndA()<controlFlow.getStartA())thita+=Math.PI;
		g.setStroke(new BasicStroke(2));
		g.drawLine(controlFlow.getEndA(),controlFlow.getEndB(), (int)(controlFlow.getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(controlFlow.getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
		g.drawLine(controlFlow.getEndA(),controlFlow.getEndB(), (int)(controlFlow.getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(controlFlow.getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
		g.setStroke(new BasicStroke(4));
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					controlFlow.setData(new DataType(dataFrame.txtName.getText(),dataFrame.txtDescription.getText(),"CFA"));
					dfd.getCFAList().add(controlFlow);
					dfd.getDataDictionary().addDD(controlFlow.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(controlFlow.getData().getName(), (controlFlow.getStartA()+controlFlow.getEndA())/2,(controlFlow.getStartB()+controlFlow.getEndB())/2);
					g.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public void drawDFA2(Graphics2D g){
		int starti=-1,endi=-1,starttype=0,endtype=0;
		for(int i=0;i<dfd.getModuleList().size();i++){
			if(dfd.getModuleList().get(i).getRect().contains(startA, startB)){
				starti=i;
				starttype=1;
				break;
			}
		}
		if(starttype==0){
			for(int i=0;i<dfd.getLibraryModuleList().size();i++){
				if(dfd.getLibraryModuleList().get(i).getRect().contains(startA, startB)){
					starti=i;
					starttype=2;
					break;
				}
			}
		}
		if(starttype==0)return;
		for(int i=0;i<dfd.getModuleList().size();i++){
			if(dfd.getModuleList().get(i).getRect().contains(endA, endB)){
				endi=i;
				endtype=1;
				break;
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getLibraryModuleList().size();i++){
				if(dfd.getLibraryModuleList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=2;
					break;
				}
			}
		}
		if(endtype==0)return;
		dataFlow2=new DFA();
		dataFlow2.setEndA(endA);
		dataFlow2.setEndB(endB);
		if(starttype==endtype&&starti==endi){
			if(starttype==1){
				dataFlow2.setStartA(startA);
				dataFlow2.setStartB((int)(dfd.getModuleList().get(starti).getRect().getY()));
				dataFlow2.setEndA(endA);
				dataFlow2.setEndB((int)(dfd.getModuleList().get(starti).getRect().getY()+dfd.getModuleList().get(starti).getRect().getHeight()));
				dfd.getModuleList().get(starti).getArrowoutList().add(dataFlow2);
				dfd.getModuleList().get(endi).getArrowinList().add(dataFlow2);
			}
			if(starttype==2){
				dataFlow2.setStartA(startA);
				dataFlow2.setStartB((int)(dfd.getLibraryModuleList().get(starti).getRect().getY()));
				dataFlow2.setEndA(endA);
				dataFlow2.setEndB((int)(dfd.getLibraryModuleList().get(starti).getRect().getY()+dfd.getLibraryModuleList().get(starti).getRect().getHeight()));
				dfd.getLibraryModuleList().get(starti).getArrowoutList().add(dataFlow2);
				dfd.getLibraryModuleList().get(endi).getArrowinList().add(dataFlow2);
			}
		}
		else{
			if(starttype==1){
				Point2D.Double pt=iRectangle(dfd.getModuleList().get(starti).getRect(),startA,startB,endA,endB);
				dataFlow2.setStartA((int)(pt.getX()));
				dataFlow2.setStartB((int)(pt.getY()));
				dfd.getModuleList().get(starti).getArrowoutList().add(dataFlow2);
			}
			if(endtype==1){
				Point2D.Double pt=iRectangle(dfd.getModuleList().get(endi).getRect(),startA,startB,endA,endB);
				dataFlow2.setEndA((int)(pt.getX()));
				dataFlow2.setEndB((int)(pt.getY()));
				dfd.getModuleList().get(endi).getArrowinList().add(dataFlow2);
			}
			if(starttype==2){
				Point2D.Double pt=iRectangle(dfd.getLibraryModuleList().get(starti).getRect(),startA,startB,endA,endB);
				dataFlow2.setStartA((int)(pt.getX()));
				dataFlow2.setStartB((int)(pt.getY()));
				dfd.getLibraryModuleList().get(starti).getArrowoutList().add(dataFlow2);
			}
			if(endtype==2){
				Point2D.Double pt=iRectangle(dfd.getLibraryModuleList().get(endi).getRect(),startA,startB,endA,endB);
				dataFlow2.setEndA((int)(pt.getX()));
				dataFlow2.setEndB((int)(pt.getY()));
				dfd.getLibraryModuleList().get(endi).getArrowinList().add(dataFlow2);
			}
		}
		g.setStroke(new BasicStroke(2));
		g.drawLine(dataFlow2.getStartA(), dataFlow2.getStartB(), dataFlow2.getEndA(), dataFlow2.getEndB());
		double slope=(double)(dataFlow2.getEndB()-dataFlow2.getStartB())/(double)(dataFlow2.getEndA()-dataFlow2.getStartA());
		double thita=Math.atan(slope);
		if(dataFlow2.getEndA()<dataFlow2.getStartA())thita+=Math.PI;
		g.drawLine(dataFlow2.getEndA(),dataFlow2.getEndB(), (int)(dataFlow2.getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(dataFlow2.getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
		g.drawLine(dataFlow2.getEndA(),dataFlow2.getEndB(), (int)(dataFlow2.getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(dataFlow2.getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
		g.setStroke(new BasicStroke(4));
		DataFrame dataFrame=new DataFrame();
		dataFrame.setVisible(true);
		dataFrame.btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String name=dataFrame.txtName.getText();
				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
				else{
					dataFlow2.setData(new DataType(dataFrame.txtName.getText(),dataFrame.txtDescription.getText(),"DFA"));
					dfd.getDFA2List().add(dataFlow2);
					dfd.getDataDictionary().addDD(dataFlow2.getData());
					dataFrame.setVisible(false);
					g.setColor(Color.BLACK);
					g.drawString(dataFlow2.getData().getName(), (dataFlow2.getStartA()+dataFlow2.getEndA())/2,(dataFlow2.getStartB()+dataFlow2.getEndB())/2);
					g.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public Point2D.Double iEllipse(Ellipse2D.Double ellipse,int x1,int y1,int x2,int y2){
		if(x1==x2)x1++;
		if(y1==y2)y1++;
		double a,b,c,d,e,f,g,h,i,j,k,l,m,n;
		f=(double)(y2-y1)/(double)(x2-x1);
		e=(double)y1-((double)x1*f);
		a=ellipse.getCenterX();
		b=ellipse.getCenterY();
		c=ellipse.getWidth()/2.0;
		d=ellipse.getHeight()/2.0;
		g=c*c*f*f+d*d;
		h=f*c*c*(e-b)-a*d*d;
		i=c*c*(e-b)*(e-b)+a*a*d*d-c*c*d*d;
		j=Math.sqrt(h*h-g*i);
		k=(j-h)/g;
		m=f*k+e;
		l=-(j+h)/g;
		n=f*l+e;
		Point2D.Double pt1=new Point2D.Double(k, m);
		Point2D.Double pt2=new Point2D.Double(l, n);
		if(x1<x2){
			if(k>=x1&&k<=x2)return pt1;
			return pt2;
		}
		if(x1>x2&&k>=x2&&k<=x1)return pt1;
		return pt2;
	}
	
	public double[] jEllipse(Ellipse2D.Double ellipse,int x1,int y1,int x2,int y2){
		if(x1==x2)x1++;
		if(y1==y2)y1++;
		double a,b,c,d,e,f,g,h,i,j,k,l,m,n;
		f=(double)(y2-y1)/(double)(x2-x1);
		e=(double)y1-((double)x1*f);
		a=ellipse.getCenterX();
		b=ellipse.getCenterY();
		c=ellipse.getWidth()/2.0;
		d=ellipse.getHeight()/2.0;
		g=c*c*f*f+d*d;
		h=f*c*c*(e-b)-a*d*d;
		i=c*c*(e-b)*(e-b)+a*a*d*d-c*c*d*d;
		j=Math.sqrt(h*h-g*i);
		k=(j-h)/g;
		m=f*k+e;
		l=-(j+h)/g;
		n=f*l+e;
		double [] array=new double[4];
		array[0]=k;
		array[1]=m;
		array[2]=l;
		array[3]=n;
		return array;
	}
	
	public Point2D.Double iRectangle(Rectangle2D.Double rect,int x1,int y1,int x2,int y2){
		if((x1<=rect.getX()&&x2>=rect.getX())||(x2<=rect.getX()&&x1>=rect.getX())){
			if((y1<=rect.getY() && y2>=rect.getY())||(y2<=rect.getY() && y1>=rect.getY()))return(new Point2D.Double(rect.getX(),rect.getY()));
			return(new Point2D.Double(rect.getX(), rect.getY()+rect.getHeight()));
		}
		if(y1<=rect.getY() && y2>=rect.getY()||(y2<=rect.getY() && y1>=rect.getY()))return(new Point2D.Double(rect.getX()+rect.getWidth(),rect.getY()));
		return(new Point2D.Double(rect.getX()+rect.getWidth(), rect.getY()+rect.getHeight()));
	}

	public void drawSystemAnalysis2(DataFlowDiagram DFD,int index,Graphics2D g){
		if(index==1){
			g.setColor(Color.WHITE);
			g.fillRect( 0, 0, canvas.getWidth()/3, canvas.getHeight()/3);
		}
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLUE);
		for(int i=0;i<DFD.getBubbleList().size();i++){
			g.drawOval((int)DFD.getBubbleList().get(i).getOval().getX()/3,(int)DFD.getBubbleList().get(i).getOval().getY()/3,(int)DFD.getBubbleList().get(i).getOval().getWidth()/3,(int)DFD.getBubbleList().get(i).getOval().getHeight()/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getBubbleList().get(i).getName(), (int)(DFD.getBubbleList().get(i).getOval().getCenterX()/3), (int)(DFD.getBubbleList().get(i).getOval().getCenterY()/3));
			g.setColor(Color.BLUE);
		}
		
		for(int i=0;i<DFD.getEntityList().size();i++){
			Rectangle2D.Double rect=DFD.getEntityList().get(i).getRect();
			g.drawRect((int)rect.getX()/3,(int)rect.getY()/3,(int)rect.getWidth()/3,(int)rect.getHeight()/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getEntityList().get(i).getName(), (int)(DFD.getEntityList().get(i).getRect().getCenterX()/3), (int)(DFD.getEntityList().get(i).getRect().getCenterY()/3));
			g.setColor(Color.BLUE);
		}
		
		for(int i=0;i<DFD.getDataStoreList().size();i++){			
			g.drawLine(DFD.getDataStoreList().get(i).getStartA()/3, DFD.getDataStoreList().get(i).getStartB()/3, DFD.getDataStoreList().get(i).getEndA()/3, DFD.getDataStoreList().get(i).getStartB()/3);
			g.drawLine(DFD.getDataStoreList().get(i).getStartA()/3, DFD.getDataStoreList().get(i).getStartB()/3, DFD.getDataStoreList().get(i).getStartA()/3, DFD.getDataStoreList().get(i).getEndB()/3);
			g.drawLine(DFD.getDataStoreList().get(i).getStartA()/3, DFD.getDataStoreList().get(i).getEndB()/3, DFD.getDataStoreList().get(i).getEndA()/3, DFD.getDataStoreList().get(i).getEndB()/3);
			g.drawLine((3*DFD.getDataStoreList().get(i).getStartA()+DFD.getDataStoreList().get(i).getEndA())/12, DFD.getDataStoreList().get(i).getStartB()/3,(3*DFD.getDataStoreList().get(i).getStartA()+DFD.getDataStoreList().get(i).getEndA())/12,DFD.getDataStoreList().get(i).getEndB()/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getDataStoreList().get(i).getName(), (int)(DFD.getDataStoreList().get(i).getRect().getCenterX()/3), (int)(DFD.getDataStoreList().get(i).getRect().getCenterY()/3));
			g.setColor(Color.BLUE);
		}
		
		g.setStroke(new BasicStroke(1));
		for(int i=0;i<DFD.getDFAList().size();i++){
			g.drawLine(DFD.getDFAList().get(i).getStartA()/3, DFD.getDFAList().get(i).getStartB()/3, DFD.getDFAList().get(i).getEndA()/3, DFD.getDFAList().get(i).getEndB()/3);
			double slope=(double)(DFD.getDFAList().get(i).getEndB()-DFD.getDFAList().get(i).getStartB())/(double)(DFD.getDFAList().get(i).getEndA()-DFD.getDFAList().get(i).getStartA());
			double thita=Math.atan(slope);
			if(DFD.getDFAList().get(i).getEndA()<DFD.getDFAList().get(i).getStartA())thita+=Math.PI;
			g.drawLine(DFD.getDFAList().get(i).getEndA()/3,DFD.getDFAList().get(i).getEndB()/3, (int)(DFD.getDFAList().get(i).getEndA()+15*Math.cos(thita-Math.PI*3/4))/3,(int)(DFD.getDFAList().get(i).getEndB()+15*Math.sin(thita-Math.PI*3/4))/3);
			g.drawLine(DFD.getDFAList().get(i).getEndA()/3,DFD.getDFAList().get(i).getEndB()/3, (int)(DFD.getDFAList().get(i).getEndA()+15*Math.cos(thita+Math.PI*3/4))/3,(int)(DFD.getDFAList().get(i).getEndB()+15*Math.sin(thita+Math.PI*3/4))/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getDFAList().get(i).getData().getName(), (DFD.getDFAList().get(i).getStartA()+DFD.getDFAList().get(i).getEndA())/6, (DFD.getDFAList().get(i).getStartB()+DFD.getDFAList().get(i).getEndB())/6);
			g.setColor(Color.BLUE);
		}
		g.setStroke(new BasicStroke(4));

	}
	
	public void drawSystemDesign2(DataFlowDiagram DFD,int index,Graphics2D g){
		if(index==1){
			g.setColor(Color.WHITE);
			g.fillRect( 0, 0, canvas2.getWidth()/3, canvas2.getHeight()/3);
		}
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLUE);
		for(int i=0;i<DFD.getModuleList().size();i++){
			Rectangle2D.Double rect=DFD.getModuleList().get(i).getRect();
			g.drawRect((int)rect.getX()/3,(int)rect.getY()/3,(int)rect.getWidth()/3,(int)rect.getHeight()/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getModuleList().get(i).getName(), (int)(DFD.getModuleList().get(i).getRect().getCenterX()/3), (int)(DFD.getModuleList().get(i).getRect().getCenterY()/3));
			g.setColor(Color.BLUE);
		}
		
		g.setStroke(new BasicStroke(1));
		for(int i=0;i<DFD.getLibraryModuleList().size();i++){
			Rectangle2D.Double rect=DFD.getLibraryModuleList().get(i).getRect();
			g.drawRect((int)(rect.getX())/3, (int)(rect.getY())/3, (int)(rect.getWidth())/3, (int)(rect.getHeight())/3);
			g.drawRect((int)(rect.getX()+2)/3, (int)(rect.getY()+2)/3, (int)(rect.getWidth()-4)/3, (int)(rect.getHeight()-4)/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getLibraryModuleList().get(i).getName(), (int)(DFD.getLibraryModuleList().get(i).getRect().getCenterX())/3, (int)(DFD.getLibraryModuleList().get(i).getRect().getCenterY())/3);
			g.setColor(Color.BLUE);
		}

		g.setStroke(new BasicStroke(2));
		for(int i=0;i<DFD.getCFAList().size();i++){
			g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));		
			g.drawLine(DFD.getCFAList().get(i).getStartA()/3, DFD.getCFAList().get(i).getStartB()/3, DFD.getCFAList().get(i).getEndA()/3, DFD.getCFAList().get(i).getEndB()/3);
			double slope=(double)(DFD.getCFAList().get(i).getEndB()-DFD.getCFAList().get(i).getStartB())/(double)(DFD.getCFAList().get(i).getEndA()-DFD.getCFAList().get(i).getStartA());
			double thita=Math.atan(slope);
			if(DFD.getCFAList().get(i).getEndA()<DFD.getCFAList().get(i).getStartA())thita+=Math.PI;
			g.setStroke(new BasicStroke(2));
			g.drawLine(DFD.getCFAList().get(i).getEndA()/3,DFD.getCFAList().get(i).getEndB()/3, (int)(DFD.getCFAList().get(i).getEndA()+15*Math.cos(thita-Math.PI*3/4))/3,(int)(DFD.getCFAList().get(i).getEndB()+15*Math.sin(thita-Math.PI*3/4))/3);
			g.drawLine(DFD.getCFAList().get(i).getEndA()/3,DFD.getCFAList().get(i).getEndB()/3, (int)(DFD.getCFAList().get(i).getEndA()+15*Math.cos(thita+Math.PI*3/4))/3,(int)(DFD.getCFAList().get(i).getEndB()+15*Math.sin(thita+Math.PI*3/4))/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getCFAList().get(i).getData().getName(), (DFD.getCFAList().get(i).getStartA()+DFD.getCFAList().get(i).getEndA())/6, (DFD.getCFAList().get(i).getStartB()+DFD.getCFAList().get(i).getEndB())/6);
			g.setColor(Color.BLUE);
		}

		g.setStroke(new BasicStroke(2));
		for(int i=0;i<DFD.getDFA2List().size();i++){
			g.setStroke(new BasicStroke(1));
			g.drawLine(DFD.getDFA2List().get(i).getStartA()/3, DFD.getDFA2List().get(i).getStartB()/3, DFD.getDFA2List().get(i).getEndA()/3, DFD.getDFA2List().get(i).getEndB()/3);
			double slope=(double)(DFD.getDFA2List().get(i).getEndB()-DFD.getDFA2List().get(i).getStartB())/(double)(DFD.getDFA2List().get(i).getEndA()-DFD.getDFA2List().get(i).getStartA());
			double thita=Math.atan(slope);
			if(DFD.getDFA2List().get(i).getEndA()<DFD.getDFA2List().get(i).getStartA())thita+=Math.PI;
			g.drawLine(DFD.getDFA2List().get(i).getEndA()/3,DFD.getDFA2List().get(i).getEndB()/3, (int)(DFD.getDFA2List().get(i).getEndA()+15*Math.cos(thita-Math.PI*3/4))/3,(int)(DFD.getDFA2List().get(i).getEndB()+15*Math.sin(thita-Math.PI*3/4))/3);
			g.drawLine(DFD.getDFA2List().get(i).getEndA()/3,DFD.getDFA2List().get(i).getEndB()/3, (int)(DFD.getDFA2List().get(i).getEndA()+15*Math.cos(thita+Math.PI*3/4))/3,(int)(DFD.getDFA2List().get(i).getEndB()+15*Math.sin(thita+Math.PI*3/4))/3);
			g.setColor(Color.BLACK);
			g.drawString(DFD.getDFA2List().get(i).getData().getName(), (DFD.getDFA2List().get(i).getStartA()+DFD.getDFA2List().get(i).getEndA())/6, (DFD.getDFA2List().get(i).getStartB()+DFD.getDFA2List().get(i).getEndB())/6);
			g.setColor(Color.BLUE);
		}
		g.setStroke(new BasicStroke(2));
	}

	public void drawSystemAnalysis(DataFlowDiagram DFD,int index,Graphics2D g){
		if(index==1){
			g.setColor(Color.WHITE);
			g.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight());
		}
		g.setStroke(new BasicStroke(4));
		g.setColor(Color.BLUE);
		for(int i=0;i<DFD.getBubbleList().size();i++){
			g.draw(DFD.getBubbleList().get(i).getOval());
			g.setColor(Color.BLACK);
			g.drawString(DFD.getBubbleList().get(i).getName(), (int)(DFD.getBubbleList().get(i).getOval().getCenterX()), (int)(DFD.getBubbleList().get(i).getOval().getCenterY()));
			g.setColor(Color.BLUE);
		}
		
		for(int i=0;i<DFD.getEntityList().size();i++){
			g.draw(DFD.getEntityList().get(i).getRect());
			g.setColor(Color.BLACK);
			g.drawString(DFD.getEntityList().get(i).getName(), (int)(DFD.getEntityList().get(i).getRect().getCenterX()), (int)(DFD.getEntityList().get(i).getRect().getCenterY()));
			g.setColor(Color.BLUE);
		}
		
		for(int i=0;i<DFD.getDataStoreList().size();i++){			
			g.drawLine(DFD.getDataStoreList().get(i).getStartA(), DFD.getDataStoreList().get(i).getStartB(), DFD.getDataStoreList().get(i).getEndA(), DFD.getDataStoreList().get(i).getStartB());
			g.drawLine(DFD.getDataStoreList().get(i).getStartA(), DFD.getDataStoreList().get(i).getStartB(), DFD.getDataStoreList().get(i).getStartA(), DFD.getDataStoreList().get(i).getEndB());
			g.drawLine(DFD.getDataStoreList().get(i).getStartA(), DFD.getDataStoreList().get(i).getEndB(), DFD.getDataStoreList().get(i).getEndA(), DFD.getDataStoreList().get(i).getEndB());
			g.drawLine((3*DFD.getDataStoreList().get(i).getStartA()+DFD.getDataStoreList().get(i).getEndA())/4, DFD.getDataStoreList().get(i).getStartB(),(3*DFD.getDataStoreList().get(i).getStartA()+DFD.getDataStoreList().get(i).getEndA())/4,DFD.getDataStoreList().get(i).getEndB());
			g.setColor(Color.BLACK);
			g.drawString(DFD.getDataStoreList().get(i).getName(), (int)(DFD.getDataStoreList().get(i).getRect().getCenterX()), (int)(DFD.getDataStoreList().get(i).getRect().getCenterY()));
			g.setColor(Color.BLUE);
		}
		
		g.setStroke(new BasicStroke(2));
		for(int i=0;i<DFD.getDFAList().size();i++){
			g.drawLine(DFD.getDFAList().get(i).getStartA(), DFD.getDFAList().get(i).getStartB(), DFD.getDFAList().get(i).getEndA(), DFD.getDFAList().get(i).getEndB());
			double slope=(double)(DFD.getDFAList().get(i).getEndB()-DFD.getDFAList().get(i).getStartB())/(double)(DFD.getDFAList().get(i).getEndA()-DFD.getDFAList().get(i).getStartA());
			double thita=Math.atan(slope);
			if(DFD.getDFAList().get(i).getEndA()<DFD.getDFAList().get(i).getStartA())thita+=Math.PI;
			g.drawLine(DFD.getDFAList().get(i).getEndA(),DFD.getDFAList().get(i).getEndB(), (int)(DFD.getDFAList().get(i).getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(DFD.getDFAList().get(i).getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
			g.drawLine(DFD.getDFAList().get(i).getEndA(),DFD.getDFAList().get(i).getEndB(), (int)(DFD.getDFAList().get(i).getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(DFD.getDFAList().get(i).getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
			g.setColor(Color.BLACK);
			g.drawString(DFD.getDFAList().get(i).getData().getName(), (DFD.getDFAList().get(i).getStartA()+DFD.getDFAList().get(i).getEndA())/2, (DFD.getDFAList().get(i).getStartB()+DFD.getDFAList().get(i).getEndB())/2);
			g.setColor(Color.BLUE);
		}
		g.setStroke(new BasicStroke(4));

	}
	
	public void drawSystemDesign(DataFlowDiagram DFD,int index,Graphics2D g){
		if(index==1){
			g.setColor(Color.WHITE);
			g.fillRect( 0, 0, canvas2.getWidth(), canvas2.getHeight());
		}
		g.setStroke(new BasicStroke(4));
		g.setColor(Color.BLUE);
		for(int i=0;i<DFD.getModuleList().size();i++){
			g.draw(DFD.getModuleList().get(i).getRect());
			g.setColor(Color.BLACK);
			g.drawString(DFD.getModuleList().get(i).getName(), (int)(DFD.getModuleList().get(i).getRect().getCenterX()), (int)(DFD.getModuleList().get(i).getRect().getCenterY()));
			g.setColor(Color.BLUE);
		}
		
		g.setStroke(new BasicStroke(1));
		for(int i=0;i<DFD.getLibraryModuleList().size();i++){
			Rectangle2D.Double rect=DFD.getLibraryModuleList().get(i).getRect();
			g.draw(rect);
			g.drawRect((int)(rect.getX()+2), (int)(rect.getY()+2), (int)(rect.getWidth()-4), (int)(rect.getHeight()-4));
			g.setColor(Color.BLACK);
			g.drawString(DFD.getLibraryModuleList().get(i).getName(), (int)(DFD.getLibraryModuleList().get(i).getRect().getCenterX()), (int)(DFD.getLibraryModuleList().get(i).getRect().getCenterY()));
			g.setColor(Color.BLUE);
		}

		g.setStroke(new BasicStroke(4));
		for(int i=0;i<DFD.getCFAList().size();i++){
			g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));		
			g.drawLine(DFD.getCFAList().get(i).getStartA(), DFD.getCFAList().get(i).getStartB(), DFD.getCFAList().get(i).getEndA(), DFD.getCFAList().get(i).getEndB());
			double slope=(double)(DFD.getCFAList().get(i).getEndB()-DFD.getCFAList().get(i).getStartB())/(double)(DFD.getCFAList().get(i).getEndA()-DFD.getCFAList().get(i).getStartA());
			double thita=Math.atan(slope);
			if(DFD.getCFAList().get(i).getEndA()<DFD.getCFAList().get(i).getStartA())thita+=Math.PI;
			g.setStroke(new BasicStroke(2));
			g.drawLine(DFD.getCFAList().get(i).getEndA(),DFD.getCFAList().get(i).getEndB(), (int)(DFD.getCFAList().get(i).getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(DFD.getCFAList().get(i).getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
			g.drawLine(DFD.getCFAList().get(i).getEndA(),DFD.getCFAList().get(i).getEndB(), (int)(DFD.getCFAList().get(i).getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(DFD.getCFAList().get(i).getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
			g.setColor(Color.BLACK);
			g.drawString(DFD.getCFAList().get(i).getData().getName(), (DFD.getCFAList().get(i).getStartA()+DFD.getCFAList().get(i).getEndA())/2, (DFD.getCFAList().get(i).getStartB()+DFD.getCFAList().get(i).getEndB())/2);
			g.setColor(Color.BLUE);
		}

		g.setStroke(new BasicStroke(4));
		for(int i=0;i<DFD.getDFA2List().size();i++){
			g.setStroke(new BasicStroke(2));
			g.drawLine(DFD.getDFA2List().get(i).getStartA(), DFD.getDFA2List().get(i).getStartB(), DFD.getDFA2List().get(i).getEndA(), DFD.getDFA2List().get(i).getEndB());
			double slope=(double)(DFD.getDFA2List().get(i).getEndB()-DFD.getDFA2List().get(i).getStartB())/(double)(DFD.getDFA2List().get(i).getEndA()-DFD.getDFA2List().get(i).getStartA());
			double thita=Math.atan(slope);
			if(DFD.getDFA2List().get(i).getEndA()<DFD.getDFA2List().get(i).getStartA())thita+=Math.PI;
			g.drawLine(DFD.getDFA2List().get(i).getEndA(),DFD.getDFA2List().get(i).getEndB(), (int)(DFD.getDFA2List().get(i).getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(DFD.getDFA2List().get(i).getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
			g.drawLine(DFD.getDFA2List().get(i).getEndA(),DFD.getDFA2List().get(i).getEndB(), (int)(DFD.getDFA2List().get(i).getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(DFD.getDFA2List().get(i).getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
			g.setColor(Color.BLACK);
			g.drawString(DFD.getDFA2List().get(i).getData().getName(), (DFD.getDFA2List().get(i).getStartA()+DFD.getDFA2List().get(i).getEndA())/2, (DFD.getDFA2List().get(i).getStartB()+DFD.getDFA2List().get(i).getEndB())/2);
			g.setColor(Color.BLUE);
		}
		g.setStroke(new BasicStroke(4));
	}

	public void rightMouseClicked(MouseEvent ev,Graphics2D g2){
		endi=-1;
		endtype=0;
		for(int i=0;i<dfd.getBubbleList().size();i++){
			if(dfd.getBubbleList().get(i).getOval().contains(endA, endB)){
				endi=i;
				endtype=1;
				break;
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getEntityList().size();i++){
				if(dfd.getEntityList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=2;
					break;
				}
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getDataStoreList().size();i++){
				if(dfd.getDataStoreList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=3;
					break;
				}
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getDFAList().size();i++){
				int x1=dfd.getDFAList().get(i).getStartA();
				int x2=dfd.getDFAList().get(i).getEndA();
				int x3=endA;
				int y1=dfd.getDFAList().get(i).getStartB();
				int y2=dfd.getDFAList().get(i).getEndB();
				int y3=endB;
				if(new Line2D.Double(x1, y1, x2, y2).ptSegDist(x3, y3)<11){
					endi=i;
					endtype=4;
					break;
				}
			}
		}		
		if(endtype==0)return;
		if(endtype==1){
    		g2.setColor(Color.BLACK);
    		g2.setStroke(new BasicStroke(4));
    		g2.draw(dfd.getBubbleList().get(endi).getOval());
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit bubble");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getBubbleList().get(endi).getName());
		    		frame.txtDescription.setText(dfd.getBubbleList().get(endi).getDescription());
		    		dfd.getDataDictionary().getData().remove(dfd.getBubbleList().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getBubbleList().get(endi).setName(name);
		    					dfd.getBubbleList().get(endi).setDescription(description);
			    				dfd.getBubbleList().get(endi).setType("Bubble");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent e){
	    					dfd.getDataDictionary().addDD(dfd.getBubbleList().get(endi).getData());
		    				drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete bubble");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		dfd.getDataDictionary().getData().remove(dfd.getBubbleList().get(endi).getData());
					try{
						if(dfd.getBubbleList().get(endi).getHasModule()){
							dfd.getModuleList().get(dfd.getModuleList().indexOf(dfd.getBubbleList().get(endi).getDesignModule())).getBubbles().remove(dfd.getBubbleList().get(endi));
						}
					}
					catch(Exception e){
						
					}
	    			for(int i=0;i<dfd.getBubbleList().get(endi).getArrowinList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getBubbleList().get(endi).getArrowinList().get(i).getData());
	    				try{
	    					dfd.getDFAList().remove(dfd.getBubbleList().get(endi).getArrowinList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getBubbleList().size();j++){
    						try{
    							dfd.getBubbleList().get(j).getArrowoutList().remove(dfd.getBubbleList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getEntityList().size();j++){
    						try{
    							dfd.getEntityList().get(j).getArrowoutList().remove(dfd.getBubbleList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getDataStoreList().size();j++){
    						try{
    							dfd.getDataStoreList().get(j).getArrowoutList().remove(dfd.getBubbleList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
	    			for(int i=0;i<dfd.getBubbleList().get(endi).getArrowoutList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getBubbleList().get(endi).getArrowoutList().get(i).getData());
	    				try{
	    					dfd.getDFAList().remove(dfd.getBubbleList().get(endi).getArrowoutList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getBubbleList().size();j++){
    						
    						try{
    							dfd.getBubbleList().get(j).getArrowinList().remove(dfd.getBubbleList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getEntityList().size();j++){
    						try{
    							dfd.getEntityList().get(j).getArrowinList().remove(dfd.getBubbleList().get(endi).getArrowoutList().get(i));
    						}
     						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getDataStoreList().size();j++){
    						try{
    							dfd.getDataStoreList().get(j).getArrowinList().remove(dfd.getBubbleList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
		    		dfd.getBubbleList().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    JMenuItem menuItem3=new JMenuItem("Assign a module");
		    menuItem3.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		String [] names=new String[dfd.getModuleList().size()];
		    		for(int i=0;i<dfd.getModuleList().size();i++){
		    			names[i]=dfd.getModuleList().get(i).getName();
		    		}
		    		AssignList assignModule = new AssignList(names);
		    		assignModule.setVisible(true);
		    		assignModule.setTitle("Assign a Module");
		    		assignModule.btnSelect.addActionListener(new ActionListener(){
		    			public void actionPerformed(ActionEvent evt){
		    				int index=assignModule.comboBox.getSelectedIndex();
	    					assignModule.setVisible(false);
		    				if(index<0||index>=dfd.getModuleList().size()){
		    					JOptionPane.showMessageDialog(null, "Invalid Selection");
		    				}
		    				else{
		    					if(dfd.getBubbleList().get(endi).getHasModule()){
		    						dfd.getModuleList().get(dfd.getModuleList().indexOf(dfd.getBubbleList().get(endi).getDesignModule())).getBubbles().remove(dfd.getBubbleList().get(endi));
		    						dfd.getBubbleList().get(endi).setDesignModule(dfd.getModuleList().get(index));
		    						dfd.getModuleList().get(index).getBubbles().add(dfd.getBubbleList().get(endi));
		    					}
		    					else{
		    						dfd.getBubbleList().get(endi).setDesignModule(dfd.getModuleList().get(index));
		    						dfd.getModuleList().get(index).getBubbles().add(dfd.getBubbleList().get(endi));
		    						dfd.getBubbleList().get(endi).setHasModule(true);
		    					}
		    				}
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem3);
		    JMenuItem menuItem4=new JMenuItem("Create Hierarchy");
		    menuItem4.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		createHierarchy(dfd.getBubbleList().get(endi));
		    		type=-1;
		    	}
		    });
		    popup.add(menuItem4);
		    JMenuItem menuItem5=new JMenuItem("View Hierarchy");
		    menuItem5.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		viewHierarchy(dfd.getBubbleList().get(endi));
		    		type=-1;
		    	}
		    });
		    popup.add(menuItem5);
		    JMenuItem menuItem6=new JMenuItem("Delete Hierarchy");
		    menuItem6.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		deleteHierarchy(dfd.getBubbleList().get(endi));
		    		type=-1;
		    	}
		    });
		    popup.add(menuItem6);
		    popup.show(ev.getComponent(), endA, endB);
		}
		if(endtype==2){
    		g2.setColor(Color.BLACK);
    		g2.setStroke(new BasicStroke(4));
    		g2.draw(dfd.getEntityList().get(endi).getRect());
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit entity");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getEntityList().get(endi).getName());
		    		frame.txtDescription.setText(dfd.getEntityList().get(endi).getDescription());
		    		dfd.getDataDictionary().getData().remove(dfd.getEntityList().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getEntityList().get(endi).setName(name);
		    					dfd.getEntityList().get(endi).setDescription(description);
			    				dfd.getEntityList().get(endi).setType("Entity");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent e){
	    					dfd.getDataDictionary().addDD(dfd.getEntityList().get(endi).getData());
		    				drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete entity");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		//panel.add(canvas);
		    		dfd.getDataDictionary().getData().remove(dfd.getEntityList().get(endi).getData());
	    			for(int i=0;i<dfd.getEntityList().get(endi).getArrowinList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getEntityList().get(endi).getArrowinList().get(i).getData());
	    				try{
	    					dfd.getDFAList().remove(dfd.getEntityList().get(endi).getArrowinList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getBubbleList().size();j++){
    						try{
    							dfd.getBubbleList().get(j).getArrowoutList().remove(dfd.getEntityList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getEntityList().size();j++){
    						
    						try{
    							dfd.getEntityList().get(j).getArrowoutList().remove(dfd.getEntityList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getDataStoreList().size();j++){
    						try{
    							dfd.getDataStoreList().get(j).getArrowoutList().remove(dfd.getEntityList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
	    			for(int i=0;i<dfd.getEntityList().get(endi).getArrowoutList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getEntityList().get(endi).getArrowoutList().get(i).getData());
	    				try{
	    					dfd.getDFAList().remove(dfd.getEntityList().get(endi).getArrowoutList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getBubbleList().size();j++){
    						try{
    							dfd.getBubbleList().get(j).getArrowinList().remove(dfd.getEntityList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getEntityList().size();j++){
    						
    						try{
    							dfd.getEntityList().get(j).getArrowinList().remove(dfd.getEntityList().get(endi).getArrowoutList().get(i));
    						}
     						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getDataStoreList().size();j++){
    						try{
    							dfd.getDataStoreList().get(j).getArrowinList().remove(dfd.getEntityList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
		    		dfd.getEntityList().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    popup.show(ev.getComponent(), endA, endB);
		}
		if(endtype==3){
    		g2.setColor(Color.BLACK);
    		g2.setStroke(new BasicStroke(4));
    		g2.draw(dfd.getDataStoreList().get(endi).getRect());
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit data store");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getDataStoreList().get(endi).getName());
		    		frame.txtDescription.setText(dfd.getDataStoreList().get(endi).getDescription());
		    		dfd.getDataDictionary().getData().remove(dfd.getDataStoreList().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getDataStoreList().get(endi).setName(name);
		    					dfd.getDataStoreList().get(endi).setDescription(description);
			    				dfd.getDataStoreList().get(endi).setType("DataStore");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent e){
	    					dfd.getDataDictionary().addDD(dfd.getDataStoreList().get(endi).getData());
		    				drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete data store");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		//panel.add(canvas);
		    		dfd.getDataDictionary().getData().remove(dfd.getDataStoreList().get(endi).getData());
	    			for(int i=0;i<dfd.getDataStoreList().get(endi).getArrowinList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getDataStoreList().get(endi).getArrowinList().get(i).getData());
	    				try{
	    					dfd.getDFAList().remove(dfd.getDataStoreList().get(endi).getArrowinList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getBubbleList().size();j++){
    						try{
    							dfd.getBubbleList().get(j).getArrowoutList().remove(dfd.getDataStoreList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getEntityList().size();j++){
    						try{
    							dfd.getEntityList().get(j).getArrowoutList().remove(dfd.getDataStoreList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getDataStoreList().size();j++){
    						
    						try{
    							dfd.getDataStoreList().get(j).getArrowoutList().remove(dfd.getDataStoreList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
	    			for(int i=0;i<dfd.getDataStoreList().get(endi).getArrowoutList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getDataStoreList().get(endi).getArrowoutList().get(i).getData());
	    				try{
	    					dfd.getDFAList().remove(dfd.getDataStoreList().get(endi).getArrowoutList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getBubbleList().size();j++){
    						try{
    							dfd.getBubbleList().get(j).getArrowinList().remove(dfd.getDataStoreList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getEntityList().size();j++){
    						try{
    							dfd.getEntityList().get(j).getArrowinList().remove(dfd.getDataStoreList().get(endi).getArrowoutList().get(i));
    						}
     						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getDataStoreList().size();j++){
    						
    						try{
    							dfd.getDataStoreList().get(j).getArrowinList().remove(dfd.getDataStoreList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
		    		dfd.getDataStoreList().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    popup.show(ev.getComponent(), endA, endB);
		}
		if(endtype==4){
    		g2.setColor(Color.BLACK);
    		g2.setStroke(new BasicStroke(2));
    		g2.drawLine(dfd.getDFAList().get(endi).getStartA(), dfd.getDFAList().get(endi).getStartB(), dfd.getDFAList().get(endi).getEndA(), dfd.getDFAList().get(endi).getEndB());
    		double slope=(double)(dfd.getDFAList().get(endi).getEndB()-dfd.getDFAList().get(endi).getStartB())/(double)(dfd.getDFAList().get(endi).getEndA()-dfd.getDFAList().get(endi).getStartA());
    		double thita=Math.atan(slope);
    		if(dfd.getDFAList().get(endi).getEndA()<dfd.getDFAList().get(endi).getStartA())thita+=Math.PI;
    		g2.drawLine(dfd.getDFAList().get(endi).getEndA(),dfd.getDFAList().get(endi).getEndB(), (int)(dfd.getDFAList().get(endi).getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(dfd.getDFAList().get(endi).getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
    		g2.drawLine(dfd.getDFAList().get(endi).getEndA(),dfd.getDFAList().get(endi).getEndB(), (int)(dfd.getDFAList().get(endi).getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(dfd.getDFAList().get(endi).getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
    		g2.setStroke(new BasicStroke(4));
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit data flow arrow");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getDFAList().get(endi).getData().getName());
		    		frame.txtDescription.setText(dfd.getDFAList().get(endi).getData().getDefinition());
		    		dfd.getDataDictionary().getData().remove(dfd.getDFAList().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getDFAList().get(endi).setName(name);
		    					dfd.getDFAList().get(endi).setDescription(description);
			    				dfd.getDFAList().get(endi).setType("DFA");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent e){
	    					dfd.getDataDictionary().addDD(dfd.getDFAList().get(endi).getData());
		    				drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete data flow arrow");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		//panel.add(canvas);
		    		dfd.getDataDictionary().getData().remove(dfd.getDFAList().get(endi).getData());
					for(int j=0;j<dfd.getBubbleList().size();j++){
						try{
							dfd.getBubbleList().get(j).getArrowoutList().remove(dfd.getDFAList().get(endi));
						}
						catch(Exception e){
						}
						try{
							dfd.getBubbleList().get(j).getArrowinList().remove(dfd.getDFAList().get(endi));
						}
						catch(Exception e){
						}
					}
					for(int j=0;j<dfd.getEntityList().size();j++){
						try{
							dfd.getEntityList().get(j).getArrowoutList().remove(dfd.getDFAList().get(endi));
						}
						catch(Exception e){
						}
						try{
							dfd.getEntityList().get(j).getArrowinList().remove(dfd.getDFAList().get(endi));
						}
						catch(Exception e){
						}
					}
					for(int j=0;j<dfd.getDataStoreList().size();j++){
						try{
							dfd.getDataStoreList().get(j).getArrowoutList().remove(dfd.getDFAList().get(endi));
						}
						catch(Exception e){
						}
						try{
							dfd.getDataStoreList().get(j).getArrowinList().remove(dfd.getDFAList().get(endi));
						}
						catch(Exception e){
						}
					}
		    		dfd.getDFAList().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemAnalysis(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    popup.show(ev.getComponent(), endA, endB);
		}
	}

	public void rightMouseClicked2(MouseEvent ev,Graphics2D g2){
		endi=-1;
		endtype=0;
		for(int i=0;i<dfd.getModuleList().size();i++){
			if(dfd.getModuleList().get(i).getRect().contains(ev.getX(), ev.getY())){
				endi=i;
				endtype=1;
				break;
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getLibraryModuleList().size();i++){
				if(dfd.getLibraryModuleList().get(i).getRect().contains(endA, endB)){
					endi=i;
					endtype=2;
					break;
				}
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getCFAList().size();i++){
				int x1=dfd.getCFAList().get(i).getStartA();
				int x2=dfd.getCFAList().get(i).getEndA();
				int x3=endA;
				int y1=dfd.getCFAList().get(i).getStartB();
				int y2=dfd.getCFAList().get(i).getEndB();
				int y3=endB;
				if(new Line2D.Double(x1, y1, x2, y2).ptSegDist(x3, y3)<11){
					endi=i;
					endtype=3;
					break;
				}
			}
		}
		if(endtype==0){
			for(int i=0;i<dfd.getDFA2List().size();i++){
				int x1=dfd.getDFA2List().get(i).getStartA();
				int x2=dfd.getDFA2List().get(i).getEndA();
				int x3=endA;
				int y1=dfd.getDFA2List().get(i).getStartB();
				int y2=dfd.getDFA2List().get(i).getEndB();
				int y3=endB;
				if(new Line2D.Double(x1, y1, x2, y2).ptSegDist(x3, y3)<11){
					endi=i;
					endtype=4;
					break;
				}
			}
		}		
		if(endtype==0)return;
		if(endtype==1){
    		g2.setColor(Color.BLACK);
    		g2.setStroke(new BasicStroke(4));
    		g2.draw(dfd.getModuleList().get(endi).getRect());
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit module");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getModuleList().get(endi).getName());
		    		frame.txtDescription.setText(dfd.getModuleList().get(endi).getDescription());
		    		dfd.getDataDictionary().getData().remove(dfd.getModuleList().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getModuleList().get(endi).setName(name);
		    					dfd.getModuleList().get(endi).setDescription(description);
			    				dfd.getModuleList().get(endi).setType("Module");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent evt2){
	    					dfd.getDataDictionary().addDD(dfd.getModuleList().get(endi).getData());
		    				drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete module");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		dfd.getDataDictionary().getData().remove(dfd.getModuleList().get(endi).getData());
		    		for(int i=0;i<dfd.getModuleList().get(endi).getBubbles().size();i++){
		    			dfd.getModuleList().get(endi).getBubbles().get(i).setHasModule(false);
		    			dfd.getModuleList().get(endi).getBubbles().get(i).setDesignModule(null);
		    		}
	    			for(int i=0;i<dfd.getModuleList().get(endi).getArrowinList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getModuleList().get(endi).getArrowinList().get(i).getData());
	    				try{
	    					dfd.getCFAList().remove(dfd.getModuleList().get(endi).getArrowinList().get(i));
	    				}
	    				catch(Exception e){
	    				}
	    				try{
	    					dfd.getDFA2List().remove(dfd.getModuleList().get(endi).getArrowinList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getModuleList().size();j++){
    						try{
    							dfd.getModuleList().get(j).getArrowoutList().remove(dfd.getModuleList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getLibraryModuleList().size();j++){
    						try{
    							dfd.getLibraryModuleList().get(j).getArrowoutList().remove(dfd.getModuleList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
	    			for(int i=0;i<dfd.getModuleList().get(endi).getArrowoutList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getModuleList().get(endi).getArrowoutList().get(i).getData());
	    				try{
	    					dfd.getCFAList().remove(dfd.getModuleList().get(endi).getArrowoutList().get(i));
	    				}
	    				catch(Exception e){
	    				}
	    				try{
	    					dfd.getDFA2List().remove(dfd.getModuleList().get(endi).getArrowoutList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getModuleList().size();j++){
    						try{
    							dfd.getModuleList().get(j).getArrowinList().remove(dfd.getModuleList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getLibraryModuleList().size();j++){
    						try{
    							dfd.getLibraryModuleList().get(j).getArrowinList().remove(dfd.getModuleList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
		    		dfd.getModuleList().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    JMenuItem menuItem3=new JMenuItem("Assign bubbles");
		    menuItem3.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		String [] names=new String[dfd.getBubbleList().size()];
		    		for(int i=0;i<dfd.getBubbleList().size();i++){
		    			names[i]=dfd.getBubbleList().get(i).getName();
		    		}
		    		AssignList assignBubble = new AssignList(names);
		    		assignBubble.setVisible(true);
		    		assignBubble.setTitle("Assign bubbles");
		    		assignBubble.btnSelect.addActionListener(new ActionListener(){
		    			public void actionPerformed(ActionEvent evt){
		    				int index=assignBubble.comboBox.getSelectedIndex();
		    				if(index<0||index>=dfd.getBubbleList().size()){
		    					JOptionPane.showMessageDialog(null, "Invalid Selection");
		    				}
		    				else{
		    					assignBubble.setVisible(false);
		    					if(dfd.getBubbleList().get(index).getHasModule()){
		    						dfd.getModuleList().get(dfd.getModuleList().indexOf(dfd.getBubbleList().get(index).getDesignModule())).getBubbles().remove(dfd.getBubbleList().get(index));
		    						dfd.getBubbleList().get(index).setDesignModule(dfd.getModuleList().get(endi));
		    						dfd.getModuleList().get(endi).getBubbles().add(dfd.getBubbleList().get(index));
		    					}
		    					else{
		    						dfd.getBubbleList().get(index).setDesignModule(dfd.getModuleList().get(endi));
		    						dfd.getModuleList().get(endi).getBubbles().add(dfd.getBubbleList().get(index));
		    						dfd.getBubbleList().get(index).setHasModule(true);
		    					}
		    				}
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem3);
		    popup.show(ev.getComponent(), endA, endB);
		}
		if(endtype==2){
    		g2.setColor(Color.BLACK);
    		g2.setStroke(new BasicStroke(1));
			Rectangle2D.Double rect=dfd.getLibraryModuleList().get(endi).getRect();
			g2.draw(rect);
			g2.drawRect((int)(rect.getX()+2), (int)(rect.getY()+2), (int)(rect.getWidth()-4), (int)(rect.getHeight()-4));
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit library module");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getLibraryModuleList().get(endi).getName());
		    		frame.txtDescription.setText(dfd.getLibraryModuleList().get(endi).getDescription());
		    		dfd.getDataDictionary().getData().remove(dfd.getLibraryModuleList().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getLibraryModuleList().get(endi).setName(name);
		    					dfd.getLibraryModuleList().get(endi).setDescription(description);
			    				dfd.getLibraryModuleList().get(endi).setType("LibraryModule");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent e){
	    					dfd.getDataDictionary().addDD(dfd.getLibraryModuleList().get(endi).getData());
		    				drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete library module");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		dfd.getDataDictionary().getData().remove(dfd.getLibraryModuleList().get(endi).getData());
	    			for(int i=0;i<dfd.getLibraryModuleList().get(endi).getArrowinList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getLibraryModuleList().get(endi).getArrowinList().get(i).getData());
	    				try{
	    					dfd.getCFAList().remove(dfd.getLibraryModuleList().get(endi).getArrowinList().get(i));
	    				}
	    				catch(Exception e){
	    				}
	    				try{
	    					dfd.getDFA2List().remove(dfd.getLibraryModuleList().get(endi).getArrowinList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getModuleList().size();j++){
    						try{
    							dfd.getModuleList().get(j).getArrowoutList().remove(dfd.getLibraryModuleList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getLibraryModuleList().size();j++){
    						
    						try{
    							dfd.getLibraryModuleList().get(j).getArrowoutList().remove(dfd.getLibraryModuleList().get(endi).getArrowinList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
	    			for(int i=0;i<dfd.getLibraryModuleList().get(endi).getArrowoutList().size();i++){
	    				dfd.getDataDictionary().getData().remove(dfd.getLibraryModuleList().get(endi).getArrowoutList().get(i).getData());
	    				try{
	    					dfd.getCFAList().remove(dfd.getLibraryModuleList().get(endi).getArrowoutList().get(i));
	    				}
	    				catch(Exception e){
	    				}
	    				try{
	    					dfd.getDFA2List().remove(dfd.getLibraryModuleList().get(endi).getArrowoutList().get(i));
	    				}
	    				catch(Exception e){
	    				}
    					for(int j=0;j<dfd.getModuleList().size();j++){
    						try{
    							dfd.getModuleList().get(j).getArrowinList().remove(dfd.getLibraryModuleList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
    					for(int j=0;j<dfd.getLibraryModuleList().size();j++){
    						
    						try{
    							dfd.getLibraryModuleList().get(j).getArrowinList().remove(dfd.getLibraryModuleList().get(endi).getArrowoutList().get(i));
    						}
    						catch(Exception e){
    						}
    					}
	    			}
		    		dfd.getLibraryModuleList().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    popup.show(ev.getComponent(), endA, endB);
		}
		if(endtype==3){
    		g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));		
    		g2.drawLine(dfd.getCFAList().get(endi).getStartA(), dfd.getCFAList().get(endi).getStartB(), dfd.getCFAList().get(endi).getEndA(), dfd.getCFAList().get(endi).getEndB());
    		double slope=(double)(dfd.getCFAList().get(endi).getEndB()-dfd.getCFAList().get(endi).getStartB())/(double)(dfd.getCFAList().get(endi).getEndA()-dfd.getCFAList().get(endi).getStartA());
    		double thita=Math.atan(slope);
    		if(dfd.getCFAList().get(endi).getEndA()<dfd.getCFAList().get(endi).getStartA())thita+=Math.PI;
    		g2.drawLine(dfd.getCFAList().get(endi).getEndA(),dfd.getCFAList().get(endi).getEndB(), (int)(dfd.getCFAList().get(endi).getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(dfd.getCFAList().get(endi).getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
    		g2.drawLine(dfd.getCFAList().get(endi).getEndA(),dfd.getCFAList().get(endi).getEndB(), (int)(dfd.getCFAList().get(endi).getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(dfd.getCFAList().get(endi).getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
    		g2.setStroke(new BasicStroke(4));
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit control flow arrow");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getCFAList().get(endi).getData().getName());
		    		frame.txtDescription.setText(dfd.getCFAList().get(endi).getData().getDefinition());
		    		dfd.getDataDictionary().getData().remove(dfd.getCFAList().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getCFAList().get(endi).setName(name);
		    					dfd.getCFAList().get(endi).setDescription(description);
			    				dfd.getCFAList().get(endi).setType("CFA");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent e){
	    					dfd.getDataDictionary().addDD(dfd.getCFAList().get(endi).getData());
		    				drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete data flow arrow");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		//panel.add(canvas);
		    		dfd.getDataDictionary().getData().remove(dfd.getCFAList().get(endi).getData());
					for(int j=0;j<dfd.getModuleList().size();j++){
						try{
							dfd.getModuleList().get(j).getArrowoutList().remove(dfd.getCFAList().get(endi));
						}
						catch(Exception e){
						}
						try{
							dfd.getModuleList().get(j).getArrowinList().remove(dfd.getCFAList().get(endi));
						}
						catch(Exception e){
						}
					}
					for(int j=0;j<dfd.getLibraryModuleList().size();j++){
						try{
							dfd.getLibraryModuleList().get(j).getArrowoutList().remove(dfd.getCFAList().get(endi));
						}
						catch(Exception e){
						}
						try{
							dfd.getLibraryModuleList().get(j).getArrowinList().remove(dfd.getCFAList().get(endi));
						}
						catch(Exception e){
						}
					}
		    		dfd.getCFAList().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    popup.show(ev.getComponent(), endA, endB);
		}
		if(endtype==4){
    		g2.setColor(Color.BLACK);
    		g2.setStroke(new BasicStroke(2));
    		g2.drawLine(dfd.getDFA2List().get(endi).getStartA(), dfd.getDFA2List().get(endi).getStartB(), dfd.getDFA2List().get(endi).getEndA(), dfd.getDFA2List().get(endi).getEndB());
    		double slope=(double)(dfd.getDFA2List().get(endi).getEndB()-dfd.getDFA2List().get(endi).getStartB())/(double)(dfd.getDFA2List().get(endi).getEndA()-dfd.getDFA2List().get(endi).getStartA());
    		double thita=Math.atan(slope);
    		if(dfd.getDFA2List().get(endi).getEndA()<dfd.getDFA2List().get(endi).getStartA())thita+=Math.PI;
    		g2.drawLine(dfd.getDFA2List().get(endi).getEndA(),dfd.getDFA2List().get(endi).getEndB(), (int)(dfd.getDFA2List().get(endi).getEndA()+15*Math.cos(thita-Math.PI*3/4)),(int)(dfd.getDFA2List().get(endi).getEndB()+15*Math.sin(thita-Math.PI*3/4)) );
    		g2.drawLine(dfd.getDFA2List().get(endi).getEndA(),dfd.getDFA2List().get(endi).getEndB(), (int)(dfd.getDFA2List().get(endi).getEndA()+15*Math.cos(thita+Math.PI*3/4)),(int)(dfd.getDFA2List().get(endi).getEndB()+15*Math.sin(thita+Math.PI*3/4)) );
    		g2.setStroke(new BasicStroke(4));
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem menuItem = new JMenuItem("Edit data flow arrow");
		    menuItem.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		DataFrame frame=new DataFrame();
		    		frame.setVisible(true);
		    		frame.txtName.setText(dfd.getDFA2List().get(endi).getData().getName());
		    		frame.txtDescription.setText(dfd.getDFA2List().get(endi).getData().getDefinition());
		    		dfd.getDataDictionary().getData().remove(dfd.getDFA2List().get(endi).getData());
		    		frame.btnSave.addMouseListener(new MouseAdapter(){
		    			public void mousePressed(MouseEvent evt){
		    				String name=frame.txtName.getText();
		    				String description=frame.txtDescription.getText();
		    				if(name.isEmpty())JOptionPane.showMessageDialog(null, "The name is empty");
		    				else{
		    					frame.setVisible(false);
		    					dfd.getDFA2List().get(endi).setName(name);
		    					dfd.getDFA2List().get(endi).setDescription(description);
			    				dfd.getDFA2List().get(endi).setType("DFA");
		    				}
		    			}
		    			public void mouseReleased(MouseEvent e){
	    					dfd.getDataDictionary().addDD(dfd.getDFA2List().get(endi).getData());
		    				drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    			}
		    		});
		    	}
		    });
		    popup.add(menuItem);
		    JMenuItem menuItem2 = new JMenuItem("Delete data flow arrow");
		    menuItem2.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent evt){
		    		//panel.add(canvas);
		    		dfd.getDataDictionary().getData().remove(dfd.getDFA2List().get(endi).getData());
					for(int j=0;j<dfd.getModuleList().size();j++){
						try{
							dfd.getModuleList().get(j).getArrowoutList().remove(dfd.getDFA2List().get(endi));
						}
						catch(Exception e){
						}
						try{
							dfd.getModuleList().get(j).getArrowinList().remove(dfd.getDFA2List().get(endi));
						}
						catch(Exception e){
						}
					}
					for(int j=0;j<dfd.getLibraryModuleList().size();j++){
						try{
							dfd.getLibraryModuleList().get(j).getArrowoutList().remove(dfd.getDFA2List().get(endi));
						}
						catch(Exception e){
						}
						try{
							dfd.getLibraryModuleList().get(j).getArrowinList().remove(dfd.getDFA2List().get(endi));
						}
						catch(Exception e){
						}
					}
		    		dfd.getDFA2List().remove(endi);
		    	}
		    	public void mouseReleased(MouseEvent evt2){
				    drawSystemDesign(dfd,1,(Graphics2D)canvas.getGraphics());
		    	}
		    });
		    popup.add(menuItem2);
		    popup.show(ev.getComponent(), endA, endB);
		}
	}
	
	public void createHierarchy(Bubble bubble){
		MainWindow f=new MainWindow();
		f.previous=this;
		f.setVisible(true);
		bubble.next_level=f;
		bubble.setHierarchy(true);
		this.setVisible(false);
	}
	
	public void viewHierarchy(Bubble bubble){
		if(bubble.next_level==null)JOptionPane.showMessageDialog(null,"No hierarchy exist");
		else{
			MainWindow f=new MainWindow();
			f.previous=this;
			f.setVisible(true);
			f.dfd=bubble.next_level.dfd;
			f.drawSystemAnalysis(f.dfd, 1,(Graphics2D)(f.canvas.getGraphics()));
			this.setVisible(false);
		}
	}
	
	public void deleteHierarchy(Bubble bubble){
		bubble.next_level=null;
		bubble.setHierarchy(false);
	}

	public void goBack(){
		if(previous!=null){
			this.previous.setVisible(true);
			this.setVisible(false);
			this.previous.drawSystemAnalysis(this.previous.dfd, 1,(Graphics2D)(this.previous.canvas.getGraphics()));
		}
		else JOptionPane.showMessageDialog(null,"This is level 0");
	}
	
	public void checkBE(){
		if(checkError(this))JOptionPane.showMessageDialog(null,"No balancing error found");
		else JOptionPane.showMessageDialog(null,"There are balancing error in the data flow diagram");
	}
	
	public boolean checkError(MainWindow f){
		if(f.previous==null){
			return checkError2(f);
		}
		else{
			return(checkError(f.previous));
		}
	}
	
	public boolean checkError2(MainWindow f){
		for(int i=0;i<f.dfd.getBubbleList().size();i++){
			if(f.dfd.getBubbleList().get(i).getHasModule()==false){
				return false;
			}
		}
		for(int i=0;i<f.dfd.getModuleList().size();i++){
			if(f.dfd.getModuleList().get(i).getBubbles().size()<1){
				return false;
			}
		}
		for(int i=0;i<f.dfd.getBubbleList().size();i++){
			if(f.dfd.getBubbleList().get(i).hasHierarchy()){
				if(Balance(f.dfd.getBubbleList().get(i).next_level,f.dfd.getBubbleList().get(i))==false){
					return false;
				}
			}
		}
		for(int i=0;i<f.dfd.getBubbleList().size();i++){
			if(f.dfd.getBubbleList().get(i).hasHierarchy()){
				if(checkError2(f.dfd.getBubbleList().get(i).next_level)==false){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean Balance(MainWindow f,Bubble b){
		int ain=b.getArrowinList().size();
		int aout=b.getArrowoutList().size();
		for(int i=0;i<f.dfd.getDFAList().size();i++){
			if(dfd.getDFAList().get(i).hasTail==false)ain--;
			if(dfd.getDFAList().get(i).hasHead==false)aout--;
		}
		if(ain==0&&aout==0)return true;
		else return false;
	}
	
}
