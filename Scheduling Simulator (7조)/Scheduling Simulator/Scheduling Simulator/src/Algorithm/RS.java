package Algorithm;

import java.util.*;
import Main.Main;

/* ���� �ð� �������� ������ �ִ� ���μ������� ������� �켱������ �������� �־� ó���ϴ� �����ٸ� ���
 * (�츮���� �˰���)
 */
public class RS {
   ArrayList<Integer> sequence = new ArrayList<>();
   static public double[][] arr = new double[5][7];

   int graph[];
   
   public ArrayList<Integer> func() {// �Ű������� ���μ��� �Է¹޴°ɷ� �߰��ϸ� ��
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
         if (index != -1) {// ������ �۵��ϴ� ���μ����� ����ð��� ���� ��� if�� ���� �� �Ʒ��κ� �������
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

         for (j = 0; j < process_num; j++) {// �۵��� ���μ����� �ֳ� �˻��ϴ� �ݺ���
            if (arr_at[j] <= check_t)
               arr_at[j] = Integer.MAX_VALUE;
         }
         
         for (j = 0; j < process_num; j++) {// �۵��� ���μ����� �ֳ� �˻��ϴ� �ݺ���
            if ((arr_at[j] == Integer.MAX_VALUE) && (arr_bt[j] > 0))
               que.add(j);
         }
         
         val = que.size();
         
         if(val == 0){//���డ���� ���μ����� ���� ���
            sequence.add(-1);
            totalbt++;
            check_t++;
            continue;
         }
         
         if(val==1) {//���డ���� ���μ��� 1���� ���
            j = que.poll();
         }
         
         if(val > 1) {//���డ���� ���μ��� 1�� �̻��� ���
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