package luoweifu.image;
/**
 * 图像装饰模式
 * @author luoweifu
 *
 */
public abstract class ImageDecorator extends ImageProcessing {
	private ImageProcessing imgPro;
	
	public void decorate(ImageProcessing process) {
		this.imgPro = process;
	}

	@Override
	public int[] processing(int[] pix, int w, int h) {
		if(imgPro != null) {
			return imgPro.processing(pix, w, h);
		} else {
			System.err.println("the ImageProcessing object is not esit!");
			return null;
		}
		
	}
}
