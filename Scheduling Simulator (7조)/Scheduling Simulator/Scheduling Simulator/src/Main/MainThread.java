package Main;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Algorithm.FCFS;
import Algorithm.RR;
import Algorithm.RS;
import Algorithm.SRTN;

public class MainThread extends Thread { // 그래프를 그리는 Thread를 설정한 클래스
	static public int count = 1; 
	int sum;
	int[] endtime = new int[6];
	ArrayList<Integer> list = new ArrayList<Integer>();

	/*아이콘*/
	ImageIcon p1in = new ImageIcon(Main.class.getResource("/Image/p1_in.png"));
	ImageIcon p2in = new ImageIcon(Main.class.getResource("/Image/p2_in.png"));
	ImageIcon p3in = new ImageIcon(Main.class.getResource("/Image/p3_in.png"));
	ImageIcon p4in = new ImageIcon(Main.class.getResource("/Image/p4_in.png"));
	ImageIcon p5in = new ImageIcon(Main.class.getResource("/Image/p5_in.png"));
	ImageIcon p1out = new ImageIcon(Main.class.getResource("/Image/p1_out.png"));
	ImageIcon p2out = new ImageIcon(Main.class.getResource("/Image/p2_out.png"));
	ImageIcon p3out = new ImageIcon(Main.class.getResource("/Image/p3_out.png"));
	ImageIcon p4out = new ImageIcon(Main.class.getResource("/Image/p4_out.png"));
	ImageIcon p5out = new ImageIcon(Main.class.getResource("/Image/p5_out.png"));
	ImageIcon ATicon = new ImageIcon(Main.class.getResource("/Word/Arrival Time.png"));
	ImageIcon BTicon = new ImageIcon(Main.class.getResource("/Word/Burst Time.png"));
	ImageIcon IDicon = new ImageIcon(Main.class.getResource("/Word/Process ID.png"));
	ImageIcon WTicon = new ImageIcon(Main.class.getResource("/Word/Waiting Time.png"));
	ImageIcon TTicon = new ImageIcon(Main.class.getResource("/Word/Turnaround Time.png"));
	ImageIcon NTTicon = new ImageIcon(Main.class.getResource("/Word/Normalized TT.png"));

	Resizeicon icon = new Resizeicon();

	public MainThread(int count) {
		MainThread.count = count;
	}

	public void run() { // Thread를 실행하는 함수
		initgraph(); // 라벨들을 초기화하고 설정하는 함수
		setcolorlabel();
		setinputoutput();

		while (count < Main.newgrapharr.length) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					setgraph();
					setlabel(count);
					++count;

				}
			});
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	void initgraph() { // 라벨들을 초기화하고 설정하는 함수

		for (int i = 0; i < Main.num + 1; i++) {
			Main.lblcolor[i] = new JLabel();
			Main.lblcolor[i].setBounds(950, 300 + 40 * i, 120, 20);
			Main.lblcolor[i].setOpaque(true);
			Main.lblcolor[i].setVisible(true);
			Main.mainlabel.add(Main.lblcolor[i]);

			Main.lblcolorname[i] = new JLabel();
			Main.lblcolorname[i].setBounds(1100, 300 + 40 * i, 60, 30);
			Main.lblcolorname[i].setForeground(Color.white);
			Main.lblcolorname[i].setOpaque(false);
			Main.lblcolorname[i].setVisible(true);
			Main.mainlabel.add(Main.lblcolorname[i]);
		}

		for (int i = 0; i < 6; i++) {
			Main.lbltableheader[i] = new JLabel();
			Main.lbltableheader[i].setBounds(105 + 125 * i, 540, 90, 40);
			Main.lbltableheader[i].setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 15));
			Main.lbltableheader[i].setForeground(Color.white);
			Main.lbltableheader[i].setHorizontalAlignment(JLabel.CENTER);
			Main.lbltableheader[i].setOpaque(false);
			Main.lbltableheader[i].setVisible(true);
			Main.mainlabel.add(Main.lbltableheader[i]);
		}
		Main.lbltableheader[0].setIcon(icon.resizeicon(IDicon, 90, 40));
		Main.lbltableheader[1].setIcon(icon.resizeicon(ATicon, 90, 40));
		Main.lbltableheader[2].setIcon(icon.resizeicon(BTicon, 70, 40));
		Main.lbltableheader[3].setIcon(icon.resizeicon(WTicon, 90, 40));
		Main.lbltableheader[4].setIcon(icon.resizeicon(TTicon, 90, 40));
		Main.lbltableheader[5].setIcon(icon.resizeicon(NTTicon, 90, 40));

		for (int i = 0; i < 5; i++) {
			Main.lblinput[i] = new JLabel();
			Main.lblinput[i].setOpaque(false);
			Main.lblinput[i].setVisible(true);
			Main.mainlabel.add(Main.lblinput[i]);

			Main.lbloutput[i] = new JLabel();
			Main.lbloutput[i].setOpaque(false);
			Main.lbloutput[i].setVisible(true);
			Main.mainlabel.add(Main.lbloutput[i]);
		}

		for (int i = 1; i < Main.lblgraph.length; i++) {
			Main.lblgraph[i] = new JLabel();
			Main.lblgraph[i].setBounds(60 + 30 * i, 350, 30, 50);
			Main.lblgraph[i].setBorder(new TitledBorder(new LineBorder(Color.black, 1)));
			Main.lblgraph[i].setOpaque(true);
			Main.lblgraph[i].setVisible(false);
			Main.mainlabel.add(Main.lblgraph[i]);

			Main.lblcount[i] = new JLabel();
			Main.lblcount[i].setBounds(60 + 30 * i, 400, 30, 50);
			Main.lblcount[i].setForeground(Color.white);
			Main.lblcount[i].setOpaque(false);
			Main.lblcount[i].setVisible(true);
			Main.mainlabel.add(Main.lblcount[i]);
		}
		Main.lblcount[Main.lblcount.length - 1] = new JLabel();
		Main.lblcount[Main.lblcount.length - 1].setBounds(60 + 30 * (Main.lblcount.length - 1), 400, 50, 50);
		Main.lblcount[Main.lblcount.length - 1].setOpaque(false);
		Main.lblcount[Main.lblcount.length - 1].setVisible(true);
		Main.mainlabel.add(Main.lblcount[Main.lblcount.length - 1]);

	}

	void setgraph() { // 그래프와 카운트 라벨을 설정하는 함수
		if (count == Main.newgrapharr.length - 1) {
			Main.lblcount[count + 1].setText(Integer.toString(count));
			Main.lblcount[count + 1].setForeground(Color.white);
		}
		Main.lblgraph[count].setVisible(true);

		/*newgrapharr의 원소에 따라 색깔을 결정하고, 카운트라벨 설정*/
		switch (Main.newgrapharr[count]) {
		case 0:
			Main.lblgraph[count].setBackground(Color.white);
			Main.lblcount[count].setText(Integer.toString(count - 1));
			break;
		case 1:
			Main.lblgraph[count].setBackground(new Color(251, 194, 044));
			Main.lblcount[count].setText(Integer.toString(count - 1));
			break;
		case 2:
			Main.lblgraph[count].setBackground(new Color(240, 020, 134));
			Main.lblcount[count].setText(Integer.toString(count - 1));
			break;
		case 3:
			Main.lblgraph[count].setBackground(new Color(160, 103, 173));
			Main.lblcount[count].setText(Integer.toString(count - 1));
			break;
		case 4:
			Main.lblgraph[count].setBackground(new Color(070, 163, 210));
			Main.lblcount[count].setText(Integer.toString(count - 1));
			break;
		case 5:
			Main.lblgraph[count].setBackground(new Color(140, 227, 061));
			Main.lblcount[count].setText(Integer.toString(count - 1));
			break;
		}
	}

	void setcolorlabel() { // 오른쪽 라벨(색깔을 표시해주는 라벨)을 설정하는 함수

		Main.lblcolor[0].setBackground(Color.white);
		Main.lblcolorname[0].setText("Empty");
		for (int i = 1; i <= Main.num; i++) {
			Main.lblcolorname[i].setText(Main.process[i].processname);
			switch (Main.process[i].processnum) {
			case 1:
				Main.lblcolor[i].setBackground(new Color(251, 194, 044));
				break;
			case 2:
				Main.lblcolor[i].setBackground(new Color(240, 020, 134));
				break;
			case 3:
				Main.lblcolor[i].setBackground(new Color(160, 103, 173));
				break;
			case 4:
				Main.lblcolor[i].setBackground(new Color(070, 163, 210));
				break;
			case 5:
				Main.lblcolor[i].setBackground(new Color(140, 227, 061));
				break;
			default:
				break;
			}
		}
	}

	void setinputoutput() { // 들어가고 나오는 아이콘의 위치를 설정하는 함수
		for (int i = 0; i < Main.num; i++) {
			Main.lblinput[i].setBounds(75 + 30 * Main.process[i + 1].arrival_time, 250, 30, 70);
			Main.lblinput[i].setVisible(false);
			Main.lbloutput[i].setVisible(false);
			switch (Main.selectpro) {
			case 0:
				Main.lbloutput[i].setBounds(75 + 30 * Main.fcfs.p[i].endtime, 450, 30, 70);
				break;
			case 1:
				Main.lbloutput[i].setBounds(75 + 30 * Main.rr.p[i].endtime, 450, 30, 70);
				break;
			case 2:
				Main.lbloutput[i].setBounds(75 + 30 * Main.spn.p[i].endtime, 450, 30, 70);
				break;
			case 3:
				Main.lbloutput[i].setBounds(75 + 30 * Main.srtn.p[i].endtime, 450, 30, 70);
				break;
			case 4:
				Main.lbloutput[i].setBounds(75 + 30 * Main.hrrn.p[i].endtime, 450, 30, 70);
				break;
			case 5:
				break;
			}
		}
		Main.lblinput[0].setIcon(icon.resizeicon(p1in, 30, 60));
		Main.lblinput[1].setIcon(icon.resizeicon(p2in, 30, 60));
		Main.lblinput[2].setIcon(icon.resizeicon(p3in, 30, 60));
		Main.lblinput[3].setIcon(icon.resizeicon(p4in, 30, 60));
		Main.lblinput[4].setIcon(icon.resizeicon(p5in, 30, 60));
		Main.lbloutput[0].setIcon(icon.resizeicon(p1out, 30, 60));
		Main.lbloutput[1].setIcon(icon.resizeicon(p2out, 30, 60));
		Main.lbloutput[2].setIcon(icon.resizeicon(p3out, 30, 60));
		Main.lbloutput[3].setIcon(icon.resizeicon(p4out, 30, 60));
		Main.lbloutput[4].setIcon(icon.resizeicon(p5out, 30, 60));

	}

	void settable(Process[] pro) { // 표의 내용을 설정하는 함수
		String header[] = { "Normalized TT", "ProcessID", "Arrival Time", "Burst Time", "WaitingTime",
				"Turnaround Time" };
		String contents[][] = new String[5][6];

		switch (Main.selectpro) {
		case 0:
			for (int i = 0; i < Main.num; i++) {
				contents[i][0] = pro[i + 1].processname;
				contents[i][1] = Integer.toString(Main.fcfs.p[i].arrival_time);
				contents[i][2] = Integer.toString(Main.fcfs.p[i].burst_time);
				contents[i][3] = Integer.toString(Main.fcfs.p[i].waiting_time);
				contents[i][4] = Integer.toString(Main.fcfs.p[i].turnaround_time);
				contents[i][5] = Double.toString(Main.fcfs.p[i].ntt);
			}
			break;
		case 1:
			for (int i = 0; i < Main.num; i++) {
				contents[i][0] = pro[i + 1].processname;
				contents[i][1] = Integer.toString(Main.rr.p[i].arrival_time);
				contents[i][2] = Integer.toString(Main.rr.p[i].burst_time);
				contents[i][3] = Integer.toString(Main.rr.p[i].waiting_time);
				contents[i][4] = Integer.toString(Main.rr.p[i].turnaround_time);
				contents[i][5] = Double.toString(Main.rr.p[i].ntt);
			}
			break;
		case 2:
			for (int i = 0; i < Main.num; i++) {
				contents[i][0] = pro[i + 1].processname;
				contents[i][1] = Integer.toString(Main.spn.p[i].arrival_time);
				contents[i][2] = Integer.toString(Main.spn.p[i].burst_time);
				contents[i][3] = Integer.toString(Main.spn.p[i].waiting_time);
				contents[i][4] = Integer.toString(Main.spn.p[i].turnaround_time);
				contents[i][5] = Double.toString(Main.spn.p[i].ntt);
			}
			break;
		case 3:
			for (int i = 0; i < Main.num; i++) {
				contents[i][0] = pro[i + 1].processname;
				contents[i][1] = Integer.toString(Main.srtn.p[i].arrival_time);
				contents[i][2] = Integer.toString(Main.srtn.p[i].burst_time);
				contents[i][3] = Integer.toString(Main.srtn.p[i].waiting_time);
				contents[i][4] = Integer.toString(Main.srtn.p[i].turnaround_time);
				contents[i][5] = Double.toString(Main.srtn.p[i].ntt);
			}
			break;
		case 4:
			for (int i = 0; i < Main.num; i++) {
				contents[i][0] = pro[i + 1].processname;
				contents[i][1] = Integer.toString(Main.hrrn.p[i].arrival_time);
				contents[i][2] = Integer.toString(Main.hrrn.p[i].burst_time);
				contents[i][3] = Integer.toString(Main.hrrn.p[i].waiting_time);
				contents[i][4] = Integer.toString(Main.hrrn.p[i].turnaround_time);
				contents[i][5] = Double.toString(Main.hrrn.p[i].ntt);
			}
			break;
		case 5:
			for (int i = 0; i < Main.num; i++) {
				for (int j = 0; j < 7; j++) {
					contents[i][j] = Double.toString(RS.arr[i][j]);
				}
			}
			break;
		}

		DefaultTableModel model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};

		/*표 설정*/
		Main.table = new JTable(model);
		Main.table.setBounds(90, 600, 750, 800);
		Main.table.setRowHeight(30);
		Main.table.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = Main.table.getColumnModel();

		Main.table.getTableHeader().setReorderingAllowed(false);
		Main.table.getTableHeader().setResizingAllowed(false);
		Main.table.setCellSelectionEnabled(false);
		Main.table.setFocusable(false);
		Main.table.setOpaque(false);
		Main.table.setForeground(Color.white);
		Main.table.setShowGrid(false);
		Main.table.setBackground(new Color(0, 0, 0, 0));

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		Main.table.setVisible(true);
		Main.mainlabel.add(Main.table);
	}

	void setlabel(int count) { //카운트에 맞게 input, output 라벨의 visible 상태를 변경하는 함수

		for (int i = 0; i < Main.num; i++) {
			switch (Main.selectpro) {
			case 0:
				if (count == Main.fcfs.p[i].arrival_time)
					Main.lblinput[i].setVisible(true);
				if (Main.fcfs.p[i].arrival_time == 0)
					Main.lblinput[0].setVisible(true);
				if (count == Main.fcfs.p[i].endtime)
					Main.lbloutput[i].setVisible(true);
				break;
			case 1:
				if (count == Main.rr.p[i].arrival_time)
					Main.lblinput[i].setVisible(true);
				if (Main.rr.p[i].arrival_time == 0)
					Main.lblinput[0].setVisible(true);
				if (count == Main.rr.p[i].endtime)
					Main.lbloutput[i].setVisible(true);
				break;
			case 2:
				if (count == Main.spn.p[i].arrival_time)
					Main.lblinput[i].setVisible(true);
				if (Main.spn.p[i].arrival_time == 0)
					Main.lblinput[0].setVisible(true);
				if (count == Main.spn.p[i].endtime)
					Main.lbloutput[i].setVisible(true);
				break;
			case 3:
				if (count == Main.srtn.p[i].arrival_time)
					Main.lblinput[i].setVisible(true);
				if (Main.srtn.p[i].arrival_time == 0)
					Main.lblinput[0].setVisible(true);
				if (count == Main.srtn.p[i].endtime)
					Main.lbloutput[i].setVisible(true);
				break;
			case 4:
				if (count == Main.hrrn.p[i].arrival_time)
					Main.lblinput[i].setVisible(true);
				if (Main.hrrn.p[i].arrival_time == 0)
					Main.lblinput[0].setVisible(true);
				if (count == Main.hrrn.p[i].endtime)
					Main.lbloutput[i].setVisible(true);
				break;
			case 5:
				break;
			}
		}
	}
}
