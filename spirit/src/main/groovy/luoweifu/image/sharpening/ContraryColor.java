package luoweifu.image.sharpening;
/**
 * ��ɫ����������Ǻڰ�ͼƬ���򽫺ڱ�ɰף��ױ�ɺ�
 * @author Administrator
 *
 */
public class ContraryColor extends Sharpening {

	@Override
	public int[] processing(int[] pix, int w, int h) {
		for(int i=0; i<w*h; i++) {
			pix[i] = 255-pix[i];
		}
		return super.processing(pix, w, h);
	}

}
