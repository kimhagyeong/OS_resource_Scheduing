package Algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;
import Main.Process;

public class RR extends Scheduler { // RR
	int time_quantum;
	Queue<Process> wait = new LinkedList<Process>();
	
	public RR(int n) {
		super(n);
	}
	
	public void setTQ(int t) { time_quantum = t; }
	public int getTQ() { return time_quantum; }
	
	public void insert_queue(int time) {			// time 시간에 온 프로세스를 큐에 삽입
		for(int i = 0; i < num_of_p; i++) {
			if(p[i].get_at() == time)
				wait.offer(p[i]);
		}
	}
	
	public void service() {							// Round Robin 실행
		graph.add(-1);
		Process p = new Process();
		int count = 0;								// 프로세스를 time quantum 만큼 실행하기 위한 변수
		
		for(int i = 0; isEnd() == false ; i++) {	// 모든 프로세스의 처리가 끝날 때까지
			insert_queue(i);						// i시간에 도착한 프로세스 큐에 삽입
			if(wait.isEmpty()) {					// 큐가 비어있으면 -1 넣고 다음시간
				graph.add(0);
				continue;
			}
			
			p = wait.peek();						// 다음 실행할 프로세스
			p.set_rt(p.get_rt() - 1);				// 프로세스 1만큼 실행
			graph.add(p.ID);						// 실행한 프로세스 그리기
			
			DecimalFormat df = new DecimalFormat("#.#"); // 소수둘째짜리까지 표시
			df.setRoundingMode(RoundingMode.CEILING); // 반올림모드
			
			if(p.get_rt() == 0) {					// 프로세스가 실행을 마치면 큐에서 삭제
				p.set_tt(i+1 - p.get_at());			// turnaround time
				p.set_endtime(i+1);                 // endtime
				p.set_wt(p.get_tt() - p.get_bt());	// waiting time
				double n =(double)p.get_tt()/(double)p.get_bt(); // normalized TT
				p.set_ntt(Double.parseDouble(df.format(n)));
				wait.poll();
				count = 0;
				continue;
			}
			if(count == (time_quantum-1)) {			// 프로세스가 time quantum만큼 실행하면 
				wait.offer(wait.poll());			// 큐의 맨 뒤로 넣음
				count = 0;
			}
			else
				count++;
		}
	}
}
