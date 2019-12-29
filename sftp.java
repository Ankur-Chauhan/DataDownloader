package designPatterns.creational.factory;
import com.jcraft.jsch.*;
import java.io.IOException;
import java.net.MalformedURLException;

public class sftp extends Protocols{

	public sftp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean dataDownload(String zFileURL, String zOutFileDirectory)
			throws IOException, MalformedURLException {
		
		return true;
	}
}
