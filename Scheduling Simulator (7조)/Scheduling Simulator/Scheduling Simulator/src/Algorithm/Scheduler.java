package Algorithm;

import java.util.ArrayList;

import Main.Process;

public class Scheduler { // �˰������ �������� �ʿ��� �Լ��� ������ �ִ� Ŭ����
	public Process[] p;
	int num_of_p;
	
	ArrayList<Integer> graph = new ArrayList<>();
	
	public Scheduler(int n) {
		num_of_p = n;
		p = new Process[n];
		for(int i = 0; i < n; i++)
			p[i] = new Process(i+1);
	}
	
	public int getNOP() { return num_of_p; }
	
	public boolean isEnd() {						// ��� ���μ����� ó���� ������
		for(int i = 0; i < num_of_p; i++) {
			if(p[i].get_rt() != 0)
				return false;
		}
		return true;
	}
	
	public ArrayList<Integer> getgraph(){
		return graph;
	}
	
}
