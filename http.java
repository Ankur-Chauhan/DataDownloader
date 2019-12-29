package designPatterns.creational.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class http extends Protocols{
	
	public http() {

	}

	@Override
	protected boolean dataDownload(String zFileURL, String zOutFileDirectory) throws IOException, MalformedURLException
	{
		URL stURL						= new URL(zFileURL);
		HttpURLConnection stConnection	= (HttpURLConnection) stURL.openConnection();	
		int nResponseCode				= stConnection.getResponseCode();
		if(nResponseCode != HttpURLConnection.HTTP_OK)
		{
			System.out.println("No file to download. Server replied HTTP code: " + nResponseCode);
			return false;
		}
		
		String lzFileName 				= "";
        String lzDisposition 			= stConnection.getHeaderField("Content-Disposition");
        String lzContentType 			= stConnection.getContentType();
        int lnContentLength				= stConnection.getContentLength();
        if (lzDisposition != null) 
        {	//URL is an indirect link
	        int index = lzDisposition.indexOf("filename=");
	        if (index > 0)
	        	lzFileName = lzDisposition.substring(index + 10,lzDisposition.length() - 1);
        }
        else 
        {   //URL is an direct link
            lzFileName = zFileURL.substring(zFileURL.lastIndexOf("/") + 1, zFileURL.length());
        }
        
        System.out.println("Content-Type = " + lzContentType);
        System.out.println("Content-Disposition = " + lzDisposition);
        System.out.println("Content-Length = " + lnContentLength);
        System.out.println("fileName = " + lzFileName);
        
        InputStream stInputStream 		= stConnection.getInputStream();
        String lzOutFilePath 			= zOutFileDirectory + File.separator + lzFileName;
        FileOutputStream stOutputStream = new FileOutputStream(lzOutFilePath);
        byte[] lcBuffer 				= new byte[BUFFER_LENGTH];
        int lnReadBuffer				= -1;
        while((lnReadBuffer = stInputStream.read(lcBuffer)) != -1)
        {
        	stOutputStream.write(lcBuffer, 0, lnReadBuffer);
        }
        stInputStream.close();
        stOutputStream.close();
        stConnection.disconnect();
		if(lnReadBuffer != -1)
		{
			cleanup(lzOutFilePath);
			return false;
		}
        return true;
	}
}
