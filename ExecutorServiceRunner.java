package designPatterns.creational.factory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceRunner {

	public ExecutorServiceRunner() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean StartExecutorService(String[] zServerNames, int nNumberOfURL, String zOutFileDirectory, String zDelimiter) throws MalformedURLException, IOException, InterruptedException
	{
		String [] Protocols 					= new String[nNumberOfURL];
		String [] lzTemp						= null;
		ExecutorService executorService 		= Executors.newFixedThreadPool(nNumberOfURL);
		ProtocolStarter protocolStarter 		= new ProtocolStarter();
		List<Callable<Boolean>> callableTasks	= new ArrayList<>(); 
		for(int i = 0; i < nNumberOfURL; i++)
		{
			lzTemp = zServerNames[i].split(zDelimiter);
			Protocols[i] = lzTemp[0];
			Protocols _Protocols				= protocolStarter.getProtocol(Protocols[i]);
			String lzServerName					= zServerNames[i];
			System.out.println(Protocols[i] + ":" + lzServerName);
			Callable<Boolean> callableTask 		= () -> {
				return _Protocols.dataDownload(lzServerName, zOutFileDirectory);
			};
			callableTasks.add(callableTask);
		}
		List<Future<Boolean>> futures = executorService.invokeAll(callableTasks);
		return true;
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		String lzURLArray							= args[0];
		String lzDelimiter							= ",";
		String[] lzURLList							= lzURLArray.split(lzDelimiter);
		String lzOutFileDirectory					= ""; //To make it configurable
		System.out.println(lzURLList.length);
		
		lzDelimiter									= ":";
		ExecutorServiceRunner executorServiceRunner = new ExecutorServiceRunner();
		executorServiceRunner.StartExecutorService(lzURLList, lzURLList.length, lzOutFileDirectory, lzDelimiter);
	}

}
