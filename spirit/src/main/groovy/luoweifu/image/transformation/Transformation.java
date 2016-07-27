package luoweifu.image.transformation;

import luoweifu.image.ImageDecorator;
import luoweifu.image.ImageDigital;

import java.awt.image.BufferedImage;

/**
 * ͼ��任
 * @author Administrator
 *
 */
public abstract class Transformation extends ImageDecorator {
	/**
	 * ���任
	 */
	public static final int POSITIVE = 1;
	/**
	 * ��任
	 */
	public static final int INVERSE = 2;

	@Override
	public int[] processing(int[] pix, int w, int h) {
		return super.processing(pix, w, h);
	}

	/**
	 * ͼ�����
	 * 
	 * @param srcPath
	 *            ͼƬ�Ĵ洢λ��
	 * @param destPath
	 *            ͼ��Ҫ����Ĵ洢λ��
	 * @param formatName
	 *            ͼ��Ҫ����Ĵ洢λ��
	 */
	public void processing(String srcPath, String destPath,
			String formatName) {
		BufferedImage img = ImageDigital.readImg(srcPath);
		int w = img.getWidth();
		int h = img.getHeight();
		int pix[] = new int[w * h];
		pix = img.getRGB(0, 0, w, h, pix, 0, w);
		for(int i=0; i<w*h; i++) {
			pix[i] = pix[i] & 0xff;
		}
		pix = processing(pix, w, h);
		int d = findMaxInt(pix)-findMinInt(pix);
		//System.out.println("min:" + findMinInt(pix) + "  max:" + findMaxInt(pix));
		//System.out.println("d:" + d);
		int p = 0;
		for(int i=0; i<w*h; i++) {
			p = pix[i];
			if(p < 0) {
				p = 0;
			}
			if(p > 255) {
				p = 255;
			}
			
			//p = (int)(255.0*pix[i]/d);
			pix[i] = 255<<24 | p<<16 | p<<8 | p;
		}
		img.setRGB(0, 0, w, h, pix, 0, w);
		ImageDigital.writeImg(img, formatName, destPath);
	}

	protected int findMaxInt(int[] a) {
		int max = a[0];
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) {
				max = a[i];
			}
		}
		return Math.abs(max);
	}

	/**
	 * �ҵ�ͼ�����С�Ҷ�ֵ
	 * @param a
	 * @return ��С��һ������
	 */
	protected int findMinInt(int[] a) {
		int min = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min && a[i] < 0) {
				min = a[i];
			}
		}
		return min;
	}
}
