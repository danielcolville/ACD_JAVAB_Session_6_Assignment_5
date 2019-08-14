package session6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LogWriter {
	private static Date timeStamp;
	private static DateFormat df=new SimpleDateFormat("MMddyy hhmmss");
	private static File f=new File("logs//log.txt");
	public LogWriter() {
		timeStamp=new Date();
		
		if(f.exists()) {
			File fNew=new File("logs//log_"+df.format(timeStamp).toString()+".txt");
			f.renameTo(fNew);
		}
		
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	public static void writeToFile  (String textType,String text) throws WriteToFileException,IOException,LargeFileException {
		timeStamp=new Date();
		BufferedWriter br;
		double mbSize=f.length()/(1024*1024);
		
			if(!(textType.equals("Log")||textType.equals("Debug")||textType.equals("Error"))) {
				throw new WriteToFileException("Invalid log message type");
			}
			if(mbSize>=5.0) {
				throw new LargeFileException();
			}
			br = new BufferedWriter(new FileWriter(f,true));
			br.write(textType+":"+df.format(timeStamp).toString()+":"+text+"\n");
			br.flush();
		
			br.close();
		
	}
	public static void writeToFile (String text)throws WriteToFileException,IOException,LargeFileException {
		timeStamp=new Date();
		BufferedWriter br;
		double mbSize=f.length()/(1024*1024);
		
			if(mbSize>=5.0) {
				throw new LargeFileException();
			}
			br = new BufferedWriter(new FileWriter(f,true));
			br.write("N/A:"+df.format(timeStamp).toString()+":"+text+"\n");
			br.flush();
		br.close();
		
	}
	public static void main(String[] args) {
		LogWriter l=new LogWriter();
		try {
			writeToFile("Log","This is a log message");
			writeToFile("Debug","Line failed at is 85");
			writeToFile("Error","Encountered Stack Overflow error at line 55");
			writeToFile("Not really an error message");
			writeToFile("Bad Message","Message");//this line should have error writing
			Random rand=new Random();
			
			//this loop will lead to a file which is too large
			for(int i=0;i<200000;i++) {
				
				
				switch(rand.nextInt(3)+1) {
				case 1:
					writeToFile("Log","Log message "+rand.nextDouble());
					break;
				case 2:
					writeToFile("Debug","Debug message "+rand.nextDouble());
					break;
				case 3:
					writeToFile("Error","Error message "+rand.nextDouble());
					break;
				}
			}
		}
		catch(WriteToFileException e) {
			e.printStackTrace();
		}
		catch(LargeFileException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
