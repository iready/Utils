package luoweifu.image.identification;

import luoweifu.image.ImageDigital;

import java.awt.image.BufferedImage;


public abstract class FingerIdentification implements Identification {
	/**
	 * ͼƬ��С��Ŀ�
	 */
	public static final int FWIDTH = 8;
	/**
	 *  ͼƬ��С��ĸ�
	 */
	public static final int FHEIGHT = 8;
	/**
	 * ������ɢ���ұ任��ͼ��Ŀ�	
	 */
	public static final int DCTW = 40;
	/**
	 * ������ɢ���ұ任��ͼ��ĸ�
	 */
	public static final int DCTH = 40;
	
	public static final int N = 16;
	
	public FingerIdentification() {
		
	}

	public String getCharacteristic(String srcPath) {
		BufferedImage img = ImageDigital.readImg(srcPath);
		int w = img.getWidth();
		int h = img.getHeight();
		int pix[] = new int[w * h];
		pix = img.getRGB(0, 0, w, h, pix, 0, w);
		String s = getCharacteristicValue2(pix, w, h);
		return s;
	}
	/**
	 * ���(Ҫʶ��)ͼ�������ֵ��ͼƬ��ָ����
	 * @param pix ͼ������ؾ���
	 * @param w ͼ��Ŀ�
	 * @param h ͼ��ĸ�
	 * @return String���͵�����ֵ
	 */
	public String getCharacteristicValue2(int[] pix, int w, int h) {
		long l = getCharacteristicValue(pix, w, h);		
		StringBuilder sb = new StringBuilder(Long.toHexString(l));
		if(sb.length() < N) {
			int n = N-sb.length();
			for(int i=0; i<n; i++) {
				sb.insert(0, "0");
			}
		}
		return sb.toString();
	}
	/**
	 * ���ͼ�������ֵ��������ʵ��
	 * @param pix ͼ������ؾ���
	 * @param w ͼ��Ŀ�
	 * @param h ͼ��ĸ�
	 * @return long���͵�����ֵ
	 */
	public long getCharacteristicValue(int[] pix, int w, int h) { 
		return 0;
	}
/*
	@Override
	public String getCharacteristic2(Object target) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public float identification(String charac1, String charc2) {
		int h = hammingDistance(charac1, charc2);
		float f = (float)Math.round(DECIMAL_PALACE*(N-h)/N )/DECIMAL_PALACE;
		return f;
	}
	/**
	 * ���ͼ�������ֵ,�����ݿⱣ��ͼ������ֵʱ����
	 * @param srcPath ͼ������ؾ���
	 * @return long���͵�����ֵ
	 */
	public long getCharacteristicValue4(String srcPath) {
		BufferedImage img = ImageDigital.readImg(srcPath);
		int w = img.getWidth();
		int h = img.getHeight();
		int pix[] = new int[w * h];
		pix = img.getRGB(0, 0, w, h, pix, 0, w);
		
		long l = getCharacteristicValue(pix, w, h);
		return l;
	}
	private int hammingDistance(String s1, String s2) {
		int count = 0;
		for(int i=0; i<s1.length(); i++) {
			if(s1.charAt(i) != s2.charAt(i)) {
				count ++;
			}
		}
		return count;
	}
	/*
	public static void main(String[] args) {
		String str1 = "F:\\image processing\\����ͼƬ�ز�\\����ͼƬ7\\Timages.jpg";
		String str2 = "F:\\image processing\\����ͼƬ�ز�\\����ͼƬ7\\images";
		int n = 46;
		FingerIdentification ident = new PHash();
		//searchImage(n, str1, str2, ident);
		
		BufferedImage  img = ImageDigital.readImg("F:\\image processing\\������׳ʿ������ɽ����.png");
		ImageDigital.writeImg(img, "gif", "F:\\image processing\\bmptest.gif");
		
	}*/
}
