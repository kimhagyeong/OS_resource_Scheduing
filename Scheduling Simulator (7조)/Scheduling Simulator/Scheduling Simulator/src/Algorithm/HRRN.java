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
	
	public void insert_list(int time) {			// time 시간에 온 프로세스를 리스트에 삽입
		for(int i = 0; i < num_of_p; i++) {
			if(p[i].get_at() == time)
				wait.add(p[i]);
		}
	}
	
	public int find_next(int time) {			// time 시간에 Respond ratio가 가장 높은 프로세스의 인덱스
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
	
	public void service() {							// HRRN 실행
		graph.add(-1);
		Process p = new Process();
		boolean isEnd = true;						// 프로세스 실행 중간에 CPU 뺏기지 않기 위한 변수
		int index = -1;
		
		for(int i = 0; isEnd() == false ; i++) {	// 모든 프로세스의 처리가 끝날 때까지
			insert_list(i);							// i시간에 도착한 프로세스 큐에 삽입
			if(wait.isEmpty()) {					// 리스트가 비어있으면 -1 넣고 다음시간
				graph.add(-1);
				continue;
			}
			if(isEnd) {
				p = wait.get(find_next(i));				// 다음 실행할 프로세스
				isEnd = false;
				index = find_next(i);
			}
			
			p.set_rt(p.get_rt() - 1);				// 프로세스 1만큼 실행
			graph.add(p.ID);						// 실행한 프로세스 그리기
			
			DecimalFormat df = new DecimalFormat("#.#"); // 소수둘째짜리까지 표시
			df.setRoundingMode(RoundingMode.CEILING); // 반올림모드
			
			if(p.get_rt() == 0) {					// 프로세스가 실행을 마치면 큐에서 삭제
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
