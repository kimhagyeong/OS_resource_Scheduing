package Main;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Resizeicon { //아이콘을 크기에 맞게 재설정하는 클래스
	ImageIcon resizeicon(ImageIcon Icon, int x, int y) { //아이콘을 크기에 맞게 재설정하는 함수
		ImageIcon Icon1 = Icon; //기본 아이콘을 받아 저장한다.
		Image originImg = Icon1.getImage();  //기본 아이콘의 이미지를 저장한다.
		Image changedImg= originImg.getScaledInstance(x, y, Image.SCALE_SMOOTH ); //이미지의 크기를 재설정해 다시 저장한다.
		ImageIcon changedIcon = new ImageIcon(changedImg); //재설정한 이미지를 아이콘으로 바꾼다.
		
		return changedIcon; //변경된 아이콘을 리턴한다.
	}
}
