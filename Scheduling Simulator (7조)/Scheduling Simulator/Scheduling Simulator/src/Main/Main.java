package Main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import Algorithm.FCFS;
import Algorithm.HRRN;
import Algorithm.RR;
import Algorithm.RS;
import Algorithm.SPN;
import Algorithm.SRTN;
import javax.swing.JSeparator;

public class Main extends JFrame { // ���� GUI Ŭ����
	JPanel mainpanel = new JPanel(); //�����г�
	
	/*��� �󺧵��� ����*/
	static JLabel mainlabel = new JLabel(); 
	JLabel title = new JLabel("");
	JLabel lblProcessName = new JLabel(""); 
	JLabel lblArrivalTime = new JLabel("");
	JLabel lblBurstTime = new JLabel("");
	JLabel lblTimeQuantum = new JLabel("");
	
	JLabel[] printlabel1 = new JLabel[6];
	JLabel[] printlabel2 = new JLabel[6];
	JLabel[] printlabel3 = new JLabel[6];
	static JLabel[] lblgraph;
	static JLabel[] lblcount;
	static JLabel[] lblcolor;
	static JLabel[] lblcolorname;
	static JLabel[] lbltableheader;
	static JLabel[] lblinput;
	static JLabel[] lbloutput;
	static JTable table; 

	/*�Է��� �޴� TextField�� ����*/
	static JTextField nametext = new JTextField("");
	static JTextField arrivaltext = new JTextField("");
	static JTextField bursttext = new JTextField("");
	static JTextField timeQTtext = new JTextField("");

	JSeparator[] separator = new JSeparator[4];
	String[] processname = { "FCFS", "RR", "SPN", "SRTN", "HRRN", "RS" };
	JComboBox comboBox = new JComboBox(processname); //ComboBox ����

	JButton btnenter = new JButton("");
	JButton btnstart = new JButton("");
	JButton brnreset = new JButton("");

	public static Process[] process = new Process[10]; //���μ��� ��ü�� ����

	static public int selectpro = 3; // 0: FCFS, 1: RR, 2: SPN, 3: SRTN, 4: HRRN, 5: RS(�츮���� �˰���)
	int loc = 1; //���μ����� �ε���
	public static int num = 0; // ���μ����� ���� (Max: 5)
	public static int tq = 0; // Time Quantum

	static ArrayList<Integer> grapharr; // �迭�� ����� ���� ArrayList
	static int[] newgrapharr; //�׷����� ����� �迭

	public static FCFS fcfs; //FCFS
	public static RR rr; //RR
	public static SPN spn; //SPN
	public static SRTN srtn; //SRTN
	public static HRRN hrrn; //HRRN
	public static RS rs; //RS

	/*�̹���*/
	Resizeicon icon = new Resizeicon();
	ImageIcon background = new ImageIcon(Main.class.getResource("/Image/star.png"));
	ImageIcon titleAT = new ImageIcon(Main.class.getResource("/Word/Arrival Time_title.png"));
	ImageIcon titleBT = new ImageIcon(Main.class.getResource("/Word/Burst Time_title.png"));
	ImageIcon titlePN = new ImageIcon(Main.class.getResource("/Word/Process Name_title.png"));
	ImageIcon titleTQ = new ImageIcon(Main.class.getResource("/Word/Time Quantum.png"));
	ImageIcon entericon = new ImageIcon(Main.class.getResource("/Word/Enter.png"));
	ImageIcon reseticon = new ImageIcon(Main.class.getResource("/Word/Reset.png"));
	ImageIcon starticon = new ImageIcon(Main.class.getResource("/Word/Start.png"));
	ImageIcon titleicon = new ImageIcon(Main.class.getResource("/Image/title1-5.png"));

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {

		// ��ü �������� ����, ��ġ �� ũ��, ���� ����, visible, �������� �� ����
		setTitle("Scheduling Simulator");
		setBounds(0, 0, 1200, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		// ���� �г��� ��ġ �� ũ��, visible ����
		mainpanel.setBounds(0, 0, 1182, 950);
		mainpanel.setVisible(true);
		getContentPane().add(mainpanel);
		mainpanel.setLayout(null);

		// ComboBox�� ��ġ �� ũ��, ����, ���, ��Ŀ���� �׵θ� ����
		comboBox.setBounds(270, 92, 71, 24);
		comboBox.setOpaque(false);
		comboBox.setBackground(Color.white);
		comboBox.setFocusable(false);
		comboBox.addItemListener(new ItemListener() { // RR�� ���� �Ǿ��� �� �󺧰� TextField�� ���̰� ��.
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int index = comboBox.getSelectedIndex();
					if (index == 1) {
						lblTimeQuantum.setVisible(true);
						timeQTtext.setVisible(true);
						separator[3].setVisible(true);
					} else {
						lblTimeQuantum.setVisible(false);
						timeQTtext.setVisible(false);
						separator[3].setVisible(false);
					}
				}

			}
		});
		mainpanel.add(comboBox);

		// TextField���� ��ġ �� ũ��, ����, �׵θ�, �۾� ����, ��Ʈ ����
		nametext.setBounds(87, 186, 116, 24);
		nametext.setOpaque(false);
		nametext.setColumns(10);
		nametext.setBorder(BorderFactory.createEmptyBorder());
		nametext.setForeground(Color.white);
		nametext.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		mainpanel.add(nametext);
		
		arrivaltext.setColumns(10);
		arrivaltext.setBounds(287, 186, 116, 24);
		arrivaltext.setOpaque(false);
		arrivaltext.setBorder(BorderFactory.createEmptyBorder());
		arrivaltext.setForeground(Color.white);
		arrivaltext.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		mainpanel.add(arrivaltext);

		bursttext.setColumns(10);
		bursttext.setBounds(487, 186, 116, 24);
		bursttext.setOpaque(false);
		bursttext.setBorder(BorderFactory.createEmptyBorder());
		bursttext.setForeground(Color.white);
		bursttext.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		mainpanel.add(bursttext);

		timeQTtext.setColumns(10);
		timeQTtext.setBounds(687, 186, 116, 24);
		timeQTtext.setOpaque(false);
		timeQTtext.setBorder(BorderFactory.createEmptyBorder());
		timeQTtext.setForeground(Color.white);
		timeQTtext.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		timeQTtext.setVisible(false);
		mainpanel.add(timeQTtext);
		
		// Separator ����
		for (int i = 0; i < 4; i++) {
			separator[i] = new JSeparator();
			separator[i].setBounds(87+i*200, 210, 116, 2);
			mainpanel.add(separator[i]);
		}
		separator[3].setVisible(false);
		
		// TextField ���� �ִ� �󺧵��� ��ġ �� ũ��, ��Ʈ, ������ ����
		lblProcessName.setBounds(88, 156, 130, 18);
		lblProcessName.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		lblProcessName.setIcon(icon.resizeicon(titlePN, 130, 18));
		mainpanel.add(lblProcessName);

		lblArrivalTime.setBounds(288, 156, 125, 18);
		lblArrivalTime.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		lblArrivalTime.setIcon(icon.resizeicon(titleAT, 125, 18));
		mainpanel.add(lblArrivalTime);

		lblBurstTime.setBounds(488, 156, 115, 18);
		lblBurstTime.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		lblBurstTime.setIcon(icon.resizeicon(titleBT, 115, 18));
		mainpanel.add(lblBurstTime);

		lblTimeQuantum.setBounds(688, 156, 164, 18);
		lblTimeQuantum.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		lblTimeQuantum.setIcon(icon.resizeicon(titleTQ, 164, 18));
		lblTimeQuantum.setVisible(false);
		mainpanel.add(lblTimeQuantum);

		// Enter ��ư ����
		btnenter.setBounds(933, 185, 90, 23);
		btnenter.setFocusPainted(false);
		btnenter.setContentAreaFilled(false);
		btnenter.setBorderPainted(false);
		btnenter.setIcon(icon.resizeicon(entericon, 90, 23));
		btnenter.setFont(new Font("������� ExtraBold", Font.BOLD, 15));
		btnenter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { //���콺�� ������ ��
				btnenter.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Ŀ�� �ٲ�
			}
		});
		// Enter��ư ������ ������ ���� ��(���μ��� ��� ��)���� ���̰� ��.
		btnenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectpro();

				if (loc <= 5) {
					process[loc] = new Process();
					process[loc].saveprocess();
					printlabel1[0].setText("Process Name");;
					printlabel2[0].setText("Arrival Time");
					printlabel3[0].setText("Burst Time");
					printlabel1[0].setVisible(true);
					printlabel2[0].setVisible(true);
					printlabel3[0].setVisible(true);
					printlabel1[loc].setText(process[loc].processname);
					printlabel1[loc].setVisible(true);
					printlabel2[loc].setText(Integer.toString(process[loc].arrival_time));
					printlabel2[loc].setVisible(true);
					printlabel3[loc].setText(Integer.toString(process[loc].burst_time));
					printlabel3[loc].setVisible(true);
					process[loc].processnum = loc;
					++loc;
					if (num < 5)
						++num;
				}

				nametext.setText("");
				arrivaltext.setText("");
				bursttext.setText("");

			}
		});
		mainpanel.add(btnenter);

		// Start ��ư ����
		btnstart.setBounds(698, 89, 90, 23);
		btnstart.setFocusPainted(false);
		btnstart.setContentAreaFilled(false);
		btnstart.setBorderPainted(false);
		btnstart.setIcon(icon.resizeicon(starticon, 90, 23));
		btnstart.setFont(new Font("������� ExtraBold", Font.BOLD, 20));
		btnstart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnstart.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		// Start ��ư ������ �� �˰����� ������ �ϰ� Thread ����
		btnstart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				fcfs = new FCFS(loc - 1);
				rr = new RR(loc - 1);
				spn = new SPN(loc - 1);
				srtn = new SRTN(loc - 1);
				hrrn = new HRRN(loc - 1);

				for (int i = 0; i < loc - 1; i++) {
					fcfs.p[i].set_at(process[i + 1].arrival_time);
					rr.p[i].set_at(process[i + 1].arrival_time);
					spn.p[i].set_at(process[i + 1].arrival_time);
					srtn.p[i].set_at(process[i + 1].arrival_time);
					hrrn.p[i].set_at(process[i + 1].arrival_time);
				}
				for (int i = 0; i < loc - 1; i++) {
					fcfs.p[i].set_bt(process[i + 1].burst_time);
					rr.p[i].set_bt(process[i + 1].burst_time);
					spn.p[i].set_bt(process[i + 1].burst_time);
					srtn.p[i].set_bt(process[i + 1].burst_time);
					hrrn.p[i].set_bt(process[i + 1].burst_time);
				}
				if (selectpro == 1)
					tq = Integer.parseInt(timeQTtext.getText());
				rr.setTQ(tq);
				
				selectpro();
				grapharr = new ArrayList<Integer>();
				lblcolor = new JLabel[Main.num+1];
				lblcolorname = new JLabel[Main.num+1];
				lblinput = new JLabel[5];
				lbloutput = new JLabel[5];
				lbltableheader = new JLabel[6];
				table = new JTable();

				switch (selectpro) {
				case 0:
					fcfs.service();
					grapharr = fcfs.getgraph();
					break;
				case 1:
					rr.service();
					grapharr = rr.getgraph();
					break;
				case 2:
					spn.service();
					grapharr = spn.getgraph();
					break;
				case 3:
					srtn.service();
					grapharr = srtn.getgraph();
					break;
				case 4:
					hrrn.service();
					grapharr = hrrn.getgraph();
					break;
				case 5:
					grapharr = rs.func();
					break;
				}
				lblgraph = new JLabel[grapharr.size()];
				lblcount = new JLabel[grapharr.size() + 1];
				newgrapharr = new int[grapharr.size()];
						
				for (int i = 1; i < grapharr.size(); i++) {
					newgrapharr[i] = grapharr.get(i);
				}
				
				MainThread thread = new MainThread(MainThread.count);
				thread.settable(process);
				thread.start();
			}
		});
		mainpanel.add(btnstart);

		// Reset ��ư ����
		brnreset.setFont(new Font("������� ExtraBold", Font.BOLD, 20));
		brnreset.setFocusPainted(false);
		brnreset.setContentAreaFilled(false);
		brnreset.setBorderPainted(false);
		brnreset.setIcon(icon.resizeicon(reseticon, 90, 23));
		brnreset.setBounds(498, 91, 90, 23);
		brnreset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				brnreset.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		// Reset ��ư ������ ���� ��Ŵ
		brnreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		mainpanel.add(brnreset);

		// ���� �� ����
		mainlabel.setBounds(0, 0, 1194, 765);
		mainlabel.setIcon(icon.resizeicon(background, 1182, 950));
		mainlabel.setOpaque(true);
		mainpanel.add(mainlabel);

		// ���� �� ����
		title.setFont(new Font("������� ExtraBold", Font.BOLD, 30));
		title.setForeground(Color.white);
		title.setIcon(icon.resizeicon(titleicon, 330, 35));
		title.setBounds(14, 22, 330, 35);
		mainlabel.add(title);

		setlabel(); // ���� �����ϴ� �Լ� ȣ��
	}

	void setlabel() { //������ ���� ��(���μ��� ��� ��)���� �����ϴ� �Լ�
		printlabel1[0] = new JLabel();
		printlabel1[0].setBounds(850, -80, 100, 200);
		printlabel1[0].setText("Process Name");
		printlabel1[0].setVisible(false);
		printlabel1[0].setForeground(Color.white);
		printlabel1[0].setHorizontalAlignment(JLabel.CENTER);
		mainlabel.add(printlabel1[0]);

		printlabel2[0] = new JLabel();
		printlabel2[0].setBounds(970, -80, 100, 200);
		printlabel2[0].setText("Arrival Time");
		printlabel2[0].setVisible(false);
		printlabel2[0].setForeground(Color.white);
		printlabel2[0].setHorizontalAlignment(JLabel.CENTER);
		mainlabel.add(printlabel2[0]);

		printlabel3[0] = new JLabel();
		printlabel3[0].setBounds(1090, -80, 100, 200);
		printlabel3[0].setText("Burst Time");
		printlabel3[0].setVisible(false);
		printlabel3[0].setForeground(Color.white);
		printlabel3[0].setHorizontalAlignment(JLabel.CENTER);
		mainlabel.add(printlabel3[0]);

		for (int i = 1; i <= 5; i++) {
			printlabel1[i] = new JLabel();
			printlabel1[i].setBounds(850, 10 + 20 * i, 100, 20);
			printlabel1[i].setVisible(false);
			printlabel1[i].setForeground(Color.white);
			printlabel1[i].setHorizontalAlignment(JLabel.CENTER);
			mainlabel.add(printlabel1[i]);

			printlabel2[i] = new JLabel();
			printlabel2[i].setBounds(970, 10 + 20 * i, 100, 20);
			printlabel2[i].setVisible(false);
			printlabel2[i].setForeground(Color.white);
			printlabel2[i].setHorizontalAlignment(JLabel.CENTER);
			mainlabel.add(printlabel2[i]);

			printlabel3[i] = new JLabel();
			printlabel3[i].setBounds(1090, 10 + 20 * i, 100, 20);
			printlabel3[i].setVisible(false);
			printlabel3[i].setForeground(Color.white);
			printlabel3[i].setHorizontalAlignment(JLabel.CENTER);
			mainlabel.add(printlabel3[i]);
		}
	}

	void selectpro() { // ComboBox�� ���� ���� � �˰����� ���õǾ��� ���� �����ϴ� �Լ�
		if (comboBox.getSelectedItem().equals("FCFS"))
			selectpro = 0;
		else if (comboBox.getSelectedItem().equals("RR"))
			selectpro = 1;
		else if (comboBox.getSelectedItem().equals("SPN"))
			selectpro = 2;
		else if (comboBox.getSelectedItem().equals("SRTN"))
			selectpro = 3;
		else if (comboBox.getSelectedItem().equals("HRRN"))
			selectpro = 4;
		else if (comboBox.getSelectedItem().equals("?"))
			selectpro = 5;
	}

	void reset() { // ���� ��Ű�� �Լ�

		for (int i = 0; i < Main.num + 1; i++) {
			printlabel1[i].setText("");
			printlabel2[i].setText("");
			printlabel3[i].setText("");
			lblcolor[i].setVisible(false);
			lblcolorname[i].setVisible(false);
		}

		for (int i = 0; i < 6; i++) 
			lbltableheader[i].setVisible(false);

		for (int i = 0; i < 5; i++) {
			lblinput[i].setVisible(false);
			lbloutput[i].setVisible(false);
		}
		for (int i = 1; i < lblgraph.length; i++) {
			lblgraph[i].setVisible(false);
			lblcount[i].setVisible(false);
		}
		mainlabel.setVisible(false);
		mainlabel.setVisible(true);
		lblcount[lblgraph.length].setVisible(false);	
		table.setVisible(false);
		tq = 0;
		num = 0;
		loc = 1;
		MainThread.count = 1;
	}
}
