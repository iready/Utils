package luoweifu.image.identification;

import java.awt.image.BufferedImage;

import luoweifu.image.ImageDigital;
import luoweifu.image.ImageProcessing;
/**
 * 
 * @author Administrator
 *
 */
public class HistogramIdentification implements Identification{
	/**
	 * ��ʾR��G��B��λ��
	 */
	public static final int GRAYBIT = 4;
	
	//private BufferedImage img = null;
	public HistogramIdentification() {
		
	}
	/**
	 * ��һά�ĻҶ�ֱ��ͼ
	 * @param img
	 * @return
	 */
	public String getHistogram(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		int series = (int) Math.pow(2, GRAYBIT);	//GRAYBIT=4;��12λ��int��ʾ�Ҷ�ֵ��ǰ4λ��ʾred,�м�4�Ǳ�ʾgreen,����4λ��ʾblue
		int greyScope = 256/series;
		float[] hist = new float[series*series*series]; 
		int r, g, b, index;
		int pix[] = new int[w*h]; 
		pix = img.getRGB(0, 0, w, h, pix, 0, w);
		for(int i=0; i<w*h; i++) {
			r = pix[i]>>16 & 0xff;
			r = r/greyScope;
			g = pix[i]>>8 & 0xff;
			g = g/greyScope;
			b = pix[i] & 0xff;
			b = b/greyScope;
			index = r<<(2*GRAYBIT) | g<<GRAYBIT | b; 
			hist[index] ++;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<hist.length; i++) {
			hist[i] = hist[i]/(w*h);
			hist[i] = (float)Math.round(hist[i]*DECIMAL_PALACE)/DECIMAL_PALACE;
			sb.append(hist[i]);
			if(i != hist.length-1) {
				sb.append("_");
			}			
		}
		return sb.toString();
	}
	/**
	 * ����һά�Ҷ�ֱ��ͼ������ͼ��ƥ��
	 * @param histR
	 * @param histD
	 * @return
	 */
	public static float indentification(float[] histR, float[] histD) {
		float p = (float) 0.0;
		for(int i=0; i<histR.length; i++) {
			p += Math.sqrt(histR[i]*histD[i]);
		}
		p = (float)Math.round(p*DECIMAL_PALACE)/DECIMAL_PALACE;
		return p;
	}
	/*
	public static void main(String args[]) {
		String src = "F:\\image processing\\����ͼƬ�ز�\\����ͼƬ3\\Timages.jpg";
		String dest = "F:\\image processing\\����ͼƬ�ز�\\����ͼƬ3\\images";
		int n = 14;
		float p = 0;
		float[][] histR = getHistgram(src); 
		float[][] histD = null;
		for(int i=0; i<n; i++) {
			histD = getHistgram(dest + (i+1) + ".jpg");
			p = indentification(histR, histD);
			System.out.print((i+1) + "--" + p + "    ");
		}
		
		String src = "F:\\image processing\\����ͼƬ�ز�\\����ͼƬ3\\Timages.jpg";
		String dest = "F:\\image processing\\����ͼƬ�ز�\\����ͼƬ3\\images";
		int n = 14;                          
		float p = 0;
		float[] histR = getHistgram2(src); 
		float[] histD = null;
		for(int i=0; i<n; i++) {
			histD = getHistgram2(dest + (i+1) + ".jpg");
			p = indentification2(histR, histD);
			System.out.print((i+1) + "--" + p + "    ");
		}
	}
	*/
	private float[] convertFloat(String charact ) {
		String strs[] = charact.split("_");
		float histo[] = new float[strs.length]; 
		for(int i=0; i<strs.length; i++) {
			histo[i] = Float.parseFloat(strs[i]);
		}
		return histo;
	}

	public float identification(String charac1, String charc2) {
		float[] histR = convertFloat(charac1);
		float[] histD = convertFloat(charc2);
		return indentification(histR, histD);
	}

	public String getCharacteristic(String srcPath) {
		BufferedImage img = ImageDigital.readImg(srcPath);
		return getHistogram(img);
	}
	/*@Override
	public String getCharacteristic2(Object target) {
		return (String)target;
	}*/
}
