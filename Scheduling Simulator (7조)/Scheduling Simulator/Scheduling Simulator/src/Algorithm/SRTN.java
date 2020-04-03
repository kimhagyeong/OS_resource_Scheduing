package Algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import Main.Process;

public class SRTN extends Scheduler { // SRTN
	
	ArrayList<Process> wait = new ArrayList<>();
	
	public SRTN(int n) {
		super(n);
	}
	
	public void insert_list(int time) {			// time �ð��� �� ���μ����� ����Ʈ�� ����
		for(int i = 0; i < num_of_p; i++) {
			if(p[i].get_at() == time)
				wait.add(p[i]);
		}
	}
	
	public int find_next() {					// ��⿭�� �ִ� ���μ��� �� remaining time��
		int min = 2147483647;					// ���� ���� ���μ����� �ε��� ��ȯ
		int index = 0;
		for(int i = 0; i < wait.size(); i++) {
			if(wait.get(i).get_rt() < min) {
				min = wait.get(i).get_rt();
				index = i;
			}
		}
		return index;
	}
	
	public void service() {							// SRTN ����
		graph.add(-1);
		Process p = new Process();
		int index;
		
		for(int i = 0; isEnd() == false ; i++) {	// ��� ���μ����� ó���� ���� ������
			insert_list(i);							// i�ð��� ������ ���μ��� ����Ʈ�� ����
			if(wait.isEmpty()) {					// ����Ʈ�� ��������� -1 �ְ� �����ð�
				graph.add(0);
				continue;
			}
			
			p = wait.get(find_next());				// ���� ������ ���μ���
			
			p.set_rt(p.get_rt() - 1);				// ���μ��� 1��ŭ ����
			graph.add(p.ID);						// ������ ���μ��� �׸���
			
			DecimalFormat df = new DecimalFormat("#.#"); // �Ҽ���°¥������ ǥ��
			df.setRoundingMode(RoundingMode.CEILING); // �ݿø����
			
			if(p.get_rt() == 0) {					// ���μ����� ������ ��ġ�� ����Ʈ���� ����
				index = find_next();
				p.set_tt(i+1 - p.get_at());
				p.set_endtime(i+1);                 // endtime
				p.set_wt(p.get_tt() - p.get_bt());
				double n =(double)p.get_tt()/(double)p.get_bt(); // normalized TT
				p.set_ntt(Double.parseDouble(df.format(n)));
				wait.remove(index);
			}
			
		}
	}
}
