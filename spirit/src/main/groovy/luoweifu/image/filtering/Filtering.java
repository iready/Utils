package luoweifu.image.filtering;

import luoweifu.image.ImageDecorator;
import luoweifu.image.ImageDigital;

import java.awt.image.BufferedImage;

/**
 * ������
 * @author Administrator
 *
 */
public abstract class Filtering extends ImageDecorator {
	
	@Override
	public int[] processing(int[] pix, int w, int h) {
		return super.processing(pix, w, h);
	}
	/**
	 * �˲�
	 * 
	 * @param srcPath
	 *            ͼƬ�Ĵ洢λ��
	 * @param destPath
	 *            ͼ��Ҫ����Ĵ洢λ��
	 * @param format
	 *            ͼ��Ҫ����Ĵ洢λ��
	 */
	public void processing(String srcPath, String destPath,
			String format) {
		BufferedImage img = ImageDigital.readImg(srcPath);
		int w = img.getWidth();
		int h = img.getHeight();
		int[] pix = new int[w * h];
		img.getRGB(0, 0, w, h, pix, 0, w);
		for(int i=0; i<w*h; i++) {
			pix[i] = pix[i] & 0xff;
		}
		int newpix[] = processing(pix, w, h);
		for(int i=0; i<w*h; i++) {
			newpix[i] = 255<<24 | newpix[i]<<16 | newpix[i]<<8 | newpix[i];
		}
		img.setRGB(0, 0, w, h, newpix, 0, w);
		ImageDigital.writeImg(img, format, destPath);
	}

}
