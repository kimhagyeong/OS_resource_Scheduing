package Main;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Resizeicon { //�������� ũ�⿡ �°� �缳���ϴ� Ŭ����
	ImageIcon resizeicon(ImageIcon Icon, int x, int y) { //�������� ũ�⿡ �°� �缳���ϴ� �Լ�
		ImageIcon Icon1 = Icon; //�⺻ �������� �޾� �����Ѵ�.
		Image originImg = Icon1.getImage();  //�⺻ �������� �̹����� �����Ѵ�.
		Image changedImg= originImg.getScaledInstance(x, y, Image.SCALE_SMOOTH ); //�̹����� ũ�⸦ �缳���� �ٽ� �����Ѵ�.
		ImageIcon changedIcon = new ImageIcon(changedImg); //�缳���� �̹����� ���������� �ٲ۴�.
		
		return changedIcon; //����� �������� �����Ѵ�.
	}
}
