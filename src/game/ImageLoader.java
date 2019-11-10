package game;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

public class ImageLoader 
{
	private Map<String, Image> imageBank;	//declares a map to hold images
	private static ImageLoader instance;

	//Hàm constructor mặc định tạo ra một Map dạng TreeMap
	private ImageLoader()
	{
		imageBank = new TreeMap<String, Image>();
	}

	public static ImageLoader getLoader()
	{	
		if(instance == null)
			instance = new ImageLoader();
		return instance;	// gets the image loader object
	}

	public Image getImage(String pic)
	{	
		Image loaded = null;
		
		// Tránh 1 bức bị load 2 lần
		if(imageBank.containsKey(pic))	// nếu bức ảnh đã tồn tại trong map
			return imageBank.get(pic);	// return chính bức ảnh đó
		else
		{
			try
			{
	    		// Tạo ra 1 loader cho file truy cập được lưu trữ ở package resource
				ClassLoader myLoader = this.getClass().getClassLoader();
		        
	    		// Use the loader to read the background image.
				InputStream imageStream = myLoader.getResourceAsStream(pic);

				// stores image in 'loaded'
				loaded = ImageIO.read(imageStream);
					
			}
			catch (IOException e) {
				System.out.println ("Không thể load: " + e);
			}
			
			imageBank.put(pic, loaded);	// Adds key và bức ảnh vào map
			
			return loaded;	// returns the specified image 
		}
	}
}
