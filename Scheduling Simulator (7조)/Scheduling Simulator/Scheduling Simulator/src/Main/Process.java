package Main;

public class Process { // 프로세스의 정보를 담고 있는 클래스
	public String processname;
	public int processnum;
	public int endtime;
	public int ID;
	public int burst_time;
	public int arrival_time;
	public int waiting_time;
	public int turnaround_time;
	public double ntt =0;
	
	int remaining_time;
	
	public Process() {
		processname ="";
		burst_time = 0;
		arrival_time = 0;
		waiting_time = 0;
		turnaround_time = 0;
		processnum=0;
		endtime=0;
	}
	public Process(int id) {
		ID = id;
	}
	
	public void set_name(String name) { processname = name; }
	public void set_bt(int num) { burst_time = num; remaining_time = num; }
	public void set_at(int num) { arrival_time = num;}
	public void set_wt(int num) { waiting_time = num; }
	public void set_tt(int num) { turnaround_time = num; }
	public void set_rt(int num) { remaining_time = num; }
	public void set_endtime(int num) { endtime = num; }
	public void set_ntt(double d) { ntt = d; } 
	
	public String get_name() { return processname; }
	public int get_bt() { return burst_time; }
	public int get_at() { return arrival_time; }
	public int get_wt() { return waiting_time; }
	public int get_tt() { return turnaround_time; }
	public int get_rt() { return remaining_time; }
	public int get_endtime() { return endtime; }
	public double get_ntt() { return ntt; }
	public double get_R_ratio(int time) { return ((double)(time - get_at()) + get_bt()) / (double)get_bt(); }
	
	void saveprocess() {
		processname = Main.nametext.getText();
		arrival_time = Integer.parseInt(Main.arrivaltext.getText());
		burst_time = Integer.parseInt(Main.bursttext.getText());
		if(!Main.timeQTtext.getText().equals(""))
		Main.tq = Integer.parseInt(Main.timeQTtext.getText());
	}
}
