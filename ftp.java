package designPatterns.creational.factory;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.io.BufferedOutputStream;
import java.io.File ;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ftp extends Protocols{

	public ftp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean dataDownload(String zFileURL, String zOutFileDirectory)
			throws IOException, MalformedURLException 
	{
		String lzOutFilePath                = "";
		FTPClient ftpClient 				= new FTPClient();
        try 
        {
            ftpClient.connect(zFileURL);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            String lzInputFileName 			= getInputFileName(zFileURL);
            String lzOutFileName 			= getOutFileName(zFileURL);
            lzOutFilePath            		= zOutFileDirectory + File.separator + lzOutFileName;
            File downloadFile 				= new File(lzOutFilePath);
            OutputStream outputStream 		= new BufferedOutputStream(new FileOutputStream(downloadFile));
            InputStream inputStream 		= ftpClient.retrieveFileStream(lzInputFileName);
            byte[] bytesArray 				= new byte[BUFFER_LENGTH];
            int lnReadBuffer				= -1;

            while ((lnReadBuffer = inputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, lnReadBuffer);
            }
 
            boolean lnSuccess = ftpClient.completePendingCommand();
            outputStream.close();
            inputStream.close();
            if (!lnSuccess) {
            	cleanup(lzOutFilePath);
            	return false;
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            cleanup(lzOutFilePath);
            ex.printStackTrace();
        } 
        finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
		return true;
	}
}