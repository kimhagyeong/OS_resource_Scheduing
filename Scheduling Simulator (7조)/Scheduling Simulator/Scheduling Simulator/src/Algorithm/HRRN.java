package Algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import Main.Process;

public class HRRN extends Scheduler { // HRRN
	
	ArrayList<Process> wait = new ArrayList<>();
	
	public HRRN(int n) {
		super(n);
	}
	
	public void insert_list(int time) {			// time �ð��� �� ���μ����� ����Ʈ�� ����
		for(int i = 0; i < num_of_p; i++) {
			if(p[i].get_at() == time)
				wait.add(p[i]);
		}
	}
	
	public int find_next(int time) {			// time �ð��� Respond ratio�� ���� ���� ���μ����� �ε���
		double max = 0;
		int index = 0;
		for(int i = 0; i < wait.size(); i++) {
			if(wait.get(i).get_R_ratio(time) > max) {
				max = wait.get(i).get_R_ratio(time);
				index = i;
			}
		}
		return index;
	}
	
	public void service() {							// HRRN ����
		graph.add(-1);
		Process p = new Process();
		boolean isEnd = true;						// ���μ��� ���� �߰��� CPU ������ �ʱ� ���� ����
		int index = -1;
		
		for(int i = 0; isEnd() == false ; i++) {	// ��� ���μ����� ó���� ���� ������
			insert_list(i);							// i�ð��� ������ ���μ��� ť�� ����
			if(wait.isEmpty()) {					// ����Ʈ�� ��������� -1 �ְ� �����ð�
				graph.add(-1);
				continue;
			}
			if(isEnd) {
				p = wait.get(find_next(i));				// ���� ������ ���μ���
				isEnd = false;
				index = find_next(i);
			}
			
			p.set_rt(p.get_rt() - 1);				// ���μ��� 1��ŭ ����
			graph.add(p.ID);						// ������ ���μ��� �׸���
			
			DecimalFormat df = new DecimalFormat("#.#"); // �Ҽ���°¥������ ǥ��
			df.setRoundingMode(RoundingMode.CEILING); // �ݿø����
			
			if(p.get_rt() == 0) {					// ���μ����� ������ ��ġ�� ť���� ����
				p.set_tt(i+1 - p.get_at());
				p.set_endtime(i+1);                 // endtime
				p.set_wt(p.get_tt() - p.get_bt());
				Double n =(double)p.get_tt()/(double)p.get_bt(); // normalized TT
				p.set_ntt(Double.parseDouble(df.format(n)));
				wait.remove(index);
				isEnd = true;
			}
			
		}
	}
}
