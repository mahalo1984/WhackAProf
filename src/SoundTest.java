import java.io.*;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;
import java.net.URL;

public class SoundTest implements Runnable
{
    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
	private String soundURL;
	private boolean isLooping;
	private URL url;
	
	public SoundTest(String path, boolean loopFlag)
	{
		
		isLooping = loopFlag;
		soundURL = path;
		url = SoundTest.class.getResource(soundURL);
		
		/*
        try 
		{
            soundFile = new File(SoundTest.class.getResource(soundURL).toURI());
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
            System.exit(1);
        }
		*/
        try 
		{
            audioStream = AudioSystem.getAudioInputStream(url);
        } 
		catch (Exception e)
		{
            e.printStackTrace();
            System.exit(1);
        }
		
        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try 
		{
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } 
		catch (LineUnavailableException e) 
		{
            e.printStackTrace();
            System.exit(1);
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
            System.exit(1);
        }
		
	}
	
	public SoundTest(String path)
	{
		this(path, false);
	}
	
    /**
     * @param filename the name of the file that is going to be played
     */
	public void playSound(){
        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
		
		//for sounds that don't repeat
		if(isLooping == false)
		{
			//this segment of code was taken from stackoverflow
			while (nBytesRead != -1) 
			{
				try 
				{
					nBytesRead = audioStream.read(abData, 0, abData.length);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
				if (nBytesRead >= 0) 
				{
					@SuppressWarnings("unused")
					int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
				}
			}
        }
		//for sounds that do repeat
		else if(isLooping == true)
		{
			while(isLooping == true)
			{
				try 
				{
					nBytesRead = audioStream.read(abData, 0, abData.length);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
				if (nBytesRead >= 0) 
				{
					@SuppressWarnings("unused")
					int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
				}	
				
				if(nBytesRead == -1) 
				{
					try 
					{
						audioStream = AudioSystem.getAudioInputStream(url);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
			
		}

        sourceLine.drain();
        sourceLine.close();
    }

	public void stopLooping()
	{
		isLooping = false;
	}
	
    public void run() {
        this.playSound();
    }	
	
	
}
