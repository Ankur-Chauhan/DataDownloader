package designPatterns.creational.factory;

public class ProtocolStarter {
	
	public Protocols getProtocol(String zProtocolName)
	{
		switch(zProtocolName)
		{
			case "http":
			{
				return new http();
			}
			case "ftp":
			{
				return new ftp();
			}
			case "sftp":
			{
				return new sftp();
			}
		}
		return null;
	}
}
