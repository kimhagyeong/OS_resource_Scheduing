package Algorithm;

import java.util.*;
import Main.Main;

/* 현재 시간 기준으로 도착해 있는 프로세스만을 대상으로 우선순위를 랜덤으로 주어 처리하는 스케줄링 기법
 * (우리만의 알고리즘)
 */
public class RS {
   ArrayList<Integer> sequence = new ArrayList<>();
   static public double[][] arr = new double[5][7];

   int graph[];
   
   public ArrayList<Integer> func() {// 매개변수에 프로세서 입력받는걸로 추가하면 됨
      int process_num, totalbt = 0;
      Queue<Integer> que = new LinkedList<Integer>(); 

      process_num = Main.num;
      
      double arr_at[] = new double[process_num];
      double arr_bt[] = new double[process_num];
      int wait_check[] = new int[process_num];

       for (int i = 0; i < process_num; i++) {
       arr[i][1] = Main.process[i + 1].arrival_time;
       arr[i][2] = Main.process[i + 1].burst_time;
       arr_bt[i] = arr[i][2];
       }

      for (int i = 0; i < process_num; i++) {
         totalbt = (int) (totalbt + arr[i][2]);
         wait_check[i] = 0;
         arr_at[i] = arr[i][1];
      }

      int check_t = 0, val = 0, index = -1;
      
      for (int i = 0, j = 0, k = 0; i < totalbt; i++) {
         if (index != -1) {// 이전에 작동하던 프로세서의 실행시간이 남은 경우 if문 실행 후 아래부분 실행안함
            if ((arr_bt[index] > 0)) {
               sequence.add(index + 1);
               check_t++;
               arr_bt[index]--;
               if (arr_bt[index] == 0) {
                  arr[index][6] = i + 1;
                  arr[index][4] = i - arr[index][1] + 1;
                  index = -1;
               }
               continue;
            }
         }

         for (j = 0; j < process_num; j++) {// 작동할 프로세서가 있나 검사하는 반복문
            if (arr_at[j] <= check_t)
               arr_at[j] = Integer.MAX_VALUE;
         }
         
         for (j = 0; j < process_num; j++) {// 작동할 프로세서가 있나 검사하는 반복문
            if ((arr_at[j] == Integer.MAX_VALUE) && (arr_bt[j] > 0))
               que.add(j);
         }
         
         val = que.size();
         
         if(val == 0){//실행가능한 프로세서가 없는 경우
            sequence.add(-1);
            totalbt++;
            check_t++;
            continue;
         }
         
         if(val==1) {//실행가능한 프로세서 1개인 경우
            j = que.poll();
         }
         
         if(val > 1) {//실행가능한 프로세서 1개 이상인 경우
            Random r = new Random();
            int x = r.nextInt(que.size());
            k = 0;
            while(!que.isEmpty()) {
               if(k == x) {
                  j = que.poll();
               }
               que.poll();
               k++;
            }
         }
         
         sequence.add(j + 1);
         check_t++;
         arr_bt[j]--;
         if(arr_bt[j] == 0) {
            arr[j][6] = i +1;
            arr[j][4] = i - arr[j][1] + 1;
            index = -1;
         } else index = j;
      }
      
      for (int i = 0; i < process_num; i++) {
         arr[i][3] = arr[i][4] - arr[i][2];
         arr[i][5] = (arr[i][4]) / arr[i][2];
         arr[i][5] = Double.parseDouble(String.format("%.2f",arr[i][5]));
      }
      
      return sequence;
   }
}