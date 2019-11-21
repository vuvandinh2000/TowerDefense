package game;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

public class ImageLoader 
{
	private Map<String, Image> imageBank;
	private static ImageLoader instance;

	private ImageLoader()
	{
		imageBank = new TreeMap<String, Image>();
	}

	public static ImageLoader getLoader()
	{	
		if(instance == null)
			instance = new ImageLoader();
		return instance;
	}

	public Image getImage(String pic)
	{	
		Image loaded = null;
		
		// Tránh 1 bức bị load 2 lần
		if(imageBank.containsKey(pic))
			return imageBank.get(pic);
		else
		{
			try
			{
	    		// Tạo ra 1 loader cho file truy cập được lưu trữ ở package resource
				ClassLoader myLoader = this.getClass().getClassLoader();
		        
	    		// Dùng loader để đọc ảnh background.
				InputStream imageStream = myLoader.getResourceAsStream(pic);

				// lưu trữ ảnh trên var 'loaded'
				loaded = ImageIO.read(imageStream);
					
			}
			catch (IOException e) {
				System.out.println ("Không thể load: " + e);
			}
			
			imageBank.put(pic, loaded);	// Adds key và bức ảnh vào map
			
			return loaded;
		}
	}
}
