package designPatterns.creational.factory; 
import java.nio.file.*; 
import java.io.IOException;
import java.net.MalformedURLException;

public abstract class Protocols{
	
	public static int BUFFER_LENGTH = 4096;
	
	protected abstract boolean dataDownload(String zFileURL, String zOutFileDirectory)throws IOException, MalformedURLException;
	
	protected String getOutFileName(String zFileURL)
	{
		return getProtocolName(zFileURL) + "_" + getInputFileName(zFileURL);
	}
	
	protected String getInputFileName(String zFileURL)
	{
		String[] lzTemp = zFileURL.split("/");
		return lzTemp[lzTemp.length - 1];
	}
	
	protected String getProtocolName(String zFileURL)
	{
		String[] lzTemp = zFileURL.split("/");
		return lzTemp[0];
	}
	
	protected void cleanup(String zFileName) throws IOException
	{
		try
        { 
            Files.deleteIfExists(Paths.get(zFileName)); 
        }
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists");
            return;
        }
		catch(IOException e) 
        { 
            System.out.println("Invalid permissions");
            return;
        }
		System.out.println("Deletion successful for " + zFileName); 
	}
}
