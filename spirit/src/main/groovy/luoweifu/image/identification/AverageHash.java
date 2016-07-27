package luoweifu.image.identification;

import luoweifu.image.AmplificatingShrinking;
import luoweifu.image.ImageDigital;

/**
 * ��ֵhash�㷨
 * @author ��ΰ��
 *
 */
public class AverageHash extends FingerIdentification{

	@Override
	public long getCharacteristicValue(int[] pix, int w, int h) {
		pix = AmplificatingShrinking.shrink(pix, w, h, FWIDTH, FHEIGHT);
		int[] newpix = ImageDigital.grayImage(pix, FWIDTH, FHEIGHT);
		int avrPix = averageGray(newpix, FWIDTH, FHEIGHT);
		//int hist[] = new int[FWIDTH*FHEIGHT];
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<FWIDTH*FHEIGHT; i++) {
			if(newpix[i] >= avrPix) {
				sb.append("1");	
			} else {
				sb.append("0");	
			}			
		}
		//StringBuilder sb = new StringBuilder(getCharacteristicValue(pix, w, h));
		long result = 0;
		if(sb.charAt(0) == '0') {
			result = Long.parseLong(sb.toString(), 2);
		} else {
			//�����һ���ַ���1�����ʾ����������ֱ��ת����long��
			result = 0x8000000000000000l ^ Long.parseLong(sb.substring(1), 2);
		}
		return result;
	}
	
	/**
	 * ��Ҷ�ͼ��ľ�ֵ
	 * @param pix ͼ������ؾ���
	 * @param w ͼ��Ŀ�
	 * @param h ͼ��ĸ�
	 * @return �ҶȾ�ֵ
	 */
	protected int averageGray(int[] pix, int w, int h) {
		long sum = 0;
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				sum = sum+pix[i*w + j];
			}	
		}
		return (int)(sum/(w*h));
	}
}
