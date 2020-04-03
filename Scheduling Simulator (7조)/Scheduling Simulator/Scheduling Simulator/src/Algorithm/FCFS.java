package Algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import Main.Process;

public class FCFS extends Scheduler { // FCFS
	
	Queue<Process> wait = new LinkedList<Process>();
	
	public FCFS(int n) {
		super(n);
	}
	
	public void insert_queue(int time) {			// time �ð��� �� ���μ����� ť�� ����
		for(int i = 0; i < num_of_p; i++) {
			if(p[i].get_at() == time)
				wait.offer(p[i]);
		}
	}
	
	public void service() {
		graph.add(-1);
		Process p = new Process();
		
		for(int i = 0; isEnd() == false ; i++) {	// ��� ���μ����� ó���� ���� ������
			insert_queue(i);						// i�ð��� ������ ���μ��� ť�� ����
			if(wait.isEmpty()) {					// ť�� ��������� -1 �ְ� �����ð�
				graph.add(0);
				continue;
			}
			
			p = wait.peek();						// ť���� ������ ���μ��� ����
			p.set_rt(p.get_rt() - 1);				// ���μ��� 1��ŭ ����
			graph.add(p.ID);						// ������ ���μ��� �׸���
			
			DecimalFormat df = new DecimalFormat("#.#"); // �Ҽ���°¥������ ǥ��
			df.setRoundingMode(RoundingMode.CEILING); // �ݿø����
			
			if(p.get_rt() == 0) {					// ���μ����� ������ ��ġ�� ť���� ����
				p.set_tt(i+1 - p.get_at());			// turnaround time
				p.set_endtime(i+1);                 // endtime
				p.set_wt(p.get_tt() - p.get_bt());	// waiting time
				double n =(double)p.get_tt()/(double)p.get_bt(); // normalized TT
				p.set_ntt(Double.parseDouble(df.format(n))); 
				wait.poll();
			}
		}
	}
}
