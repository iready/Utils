package luoweifu.image.transformation;

/**
 * ����Ҷ�任
 * @author Administrator
 *
 */
public class FFT extends Transformation {
	
	@Override
	public int[] processing(int[] pix, int w, int h) {
		Complex matrix[][] = translateToComplex(pix, w, h);
		Complex[] fftPix = new Complex[w*h];
		matrix = fft(matrix, w, h);
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				fftPix[i*w + j] = matrix[i][j];
			}
			
		}	
		int newpix[] = new int[w * h];
		double a = 0;
		double b = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < h; j++) {
				a = matrix[i][j].getA();
				b = matrix[i][j].getB();
				 newpix[i*w + j] = (int) (Math.sqrt(a*a +
				 b*b)/100);
				//System.out.print(a + "\t");
			}
		}
		return super.processing(newpix, w, h);
	}

	/**
	 * һά���ٸ���Ҷ�任
	 * 
	 * @param matrix
	 *            ��ά����������
	 * @param w
	 *            ͼ��Ŀ�
	 * @param h
	 *            ͼ��ĸ�
	 * @return ����Ҷ�任������鼯
	 */
	public Complex[][] fft(Complex matrix[][], int w, int h) {
		double r1 = Math.log10(w) / Math.log10(2.0)
				- (int) (Math.log10(w) / Math.log10(2.0));
		double r2 = Math.log10(h) / Math.log10(2.0)
				- (int) (Math.log10(h) / Math.log10(2.0));
		if (r1 != 0.0 || r2 != 0.0) {
			System.err.println("����Ĳ���w��h����2��n���ݣ�");
			return null;
		}
		int r = 0;
		r = (int) (Math.log10(w) / Math.log10(2));
		// �����и���Ҷ�任
		for (int i = 0; i < h; i++) {
			matrix[i] = fft(matrix[i]);
		}
		// �����и���Ҷ�任
		r = (int) (Math.log10(h) / Math.log10(2)); // ���������r
		Complex tempCom[] = new Complex[h];
		for (int j = 0; j < w; j++) {
			for (int i = 0; i < h; i++) {
				tempCom[i] = matrix[i][j];
			}
			tempCom = fft(tempCom);
			for (int i = 0; i < h; i++) {
				matrix[i][j] = tempCom[i];
			}
		}
		return matrix;
	}

	/**
	 * һά���ٸ���Ҷ�任
	 * 
	 * @param values
	 *            һά����������
	 * @return ����Ҷ�任������鼯
	 */
	public Complex[] fft(Complex[] values) {
		int n = values.length;
		int r = (int) (Math.log10(n) / Math.log10(2)); // ���������r
		Complex[][] temp = new Complex[r + 1][n]; // ������̵���ʱ����
		Complex w = new Complex(); // Ȩϵ��
		temp[0] = values;
		int x1, x2; // һ�Զ�ż�����±�ֵ
		int d, p; // p��ʾ��Ȩϵ��Wpn��pֵ, t������������Ӧ������ֵ
		for (int l = 1; l <= r; l++) {
			if (l != r) {
				d = (int) (n / Math.pow(2, l));
				for(int i=0; i<Math.pow(2, l-1); i++) {
					for (int k = 0; k < d; k++) {
						x1 = (int) (i*Math.pow(2, r-(l-1)) + k);
						x2 = x1 + d;
						p = getWeight(k, l, r);
						w.setA(Math.cos(-2 * Math.PI * p / n));
						w.setB(Math.sin(-2 * Math.PI * p / n));
						temp[l][x1] = Complex.add(temp[l - 1][x1], Complex.multiply(
								w, temp[l - 1][x2]));
		
						w.setA(-Math.cos(-2 * Math.PI * p / n));
						w.setB(-Math.sin(-2 * Math.PI * p / n));
						temp[l][x2] = Complex.add(temp[l - 1][x1], Complex
								.multiply(w, temp[l - 1][x2]));
					}
				}
			} else {
				for (int k = 0; k < n / 2; k++) {
					x1 = 2 * k;
					x2 = 2 * k +1;
					// System.out.println("x1:" + x1 + "  x2:" + x2);
					p = reverseRatio(2 * k, r);
					w.setA(Math.cos(-2 * Math.PI * p / n));
					w.setB(Math.sin(-2 * Math.PI * p / n));
					temp[l][p] = Complex.add(temp[l - 1][x1], Complex.multiply(
							w, temp[l - 1][x2]));
					p = reverseRatio(2 * k + 1, r);
					w.setA(Math.cos(-2 * Math.PI * p / n));
					w.setB(Math.sin(-2 * Math.PI * p / n));
					temp[l][p] = Complex.add(temp[l - 1][x1], Complex.multiply(
							w, temp[l - 1][x2]));
				}
			}
		}
		return temp[r];
	}
	/*
	public int[] toPix(Complex[] fftData, int iw, int ih) {
		int[] pix = new int[iw * ih];

		int u, v, r;
		for (int j = 0; j < ih; j++) {
			for (int i = 0; i < iw; i++) {
				double tem = fftData[i + j * iw].getA() * fftData[i + j * iw].getA()
						+ fftData[i + j * iw].getB() * fftData[i + j * iw].getB();
				r = (int) (Math.sqrt(tem) / 100);
				if (r > 255)
					r = 255;

				if (i < iw / 2)
					u = i + iw / 2;
				else
					u = i - iw / 2;
				if (j < ih / 2)
					v = j + ih / 2;
				else
					v = j - ih / 2;

				pix[u + v * iw] = r;//255 << 24 | r << 16 | r << 8 | r;
			}
		}
		return pix;
	}*/
	/**
	 * ���Ȩϵ�� 1.����kд��rλ�Ķ�������;2.���ö�������������r-lλ;3.��rλ�Ķ����������ص�ת;4.������ú�Ķ������������ʮ������;
	 * 
	 * @param k
	 *            Ҫ��ת��ʮ������
	 * @param l
	 *            �±�ֵ
	 * @param r
	 *            �����Ƶ�λ��
	 * @return ��Ȩϵ��
	 */
	private int getWeight(int k, int l, int r) {
		int d = r - l; // λ����
		k = k >> d;
		return reverseRatio(k, r);
	}

	/**
	 * �������ж����Ƶ�ת�� ��0101��ת��1010
	 * 1.����kд��rλ�Ķ�������;2.��rλ�Ķ����������ص�ת;3.������ú�Ķ������������ʮ������;
	 * 
	 * @param k
	 *            Ҫ��ת��ʮ������
	 * @param r
	 *            �����Ƶ�λ��
	 * @return ��ת���ʮ������
	 */
	private int reverseRatio(int k, int r) {
		int n = 0;
		StringBuilder sb = new StringBuilder(Integer.toBinaryString(k));
		StringBuilder sb2 = new StringBuilder("");
		if (sb.length() < r) {
			n = r - sb.length();
			for (int i = 0; i < n; i++) {
				sb.insert(0, "0");
			}
		}
	
		for (int i = 0; i < sb.length(); i++) {
			sb2.append(sb.charAt(sb.length() - i - 1));
		}
		return Integer.parseInt(sb2.toString(), 2);
	}

	private Complex[][] translateToComplex(int[] pix, int w, int h) {
		Complex[][] complexs = new Complex[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				complexs[i][j] = new Complex(pix[i * w + j], 0.0);
			}
		}
		return complexs;
	}
	
}
