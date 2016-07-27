package luoweifu.image.identification;
/**
 * ͼ��ʶ��  �Ƚ�����ͼƬ�����ƶ�
 * @author Administrator
 *
 */
public interface Identification {
	/**
	 * ���ص�С��ƥ��ֵ��С�����λ��
	 */
	public static final int DECIMAL_PALACE = 1000;
	/**
	 * �ӱ���ͼƬ�л��ͼ�������ֵ
	 * @param srcPath
	 * @return
	 */
	public String getCharacteristic(String srcPath);
	/**
	 * �����ݿ��л��ͼ�������ֵ 
	 * @param target
	 * @returnhlhl
	 */
	//public String getCharacteristic2(String target);
	
	/**
	 * ͨ����������ֵ�Ƚ�����ͼ������ƶ�
	 * @param charac1
	 * @param charac2
	 * @return һ��0-1��floatֵ��ֵԽ�����ʾԽ����
	 */
	public float identification(String charac1, String charac2);
}
