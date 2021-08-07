package mainControllers;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class music {
	 void playMusic(String musicLocation){
	        try{
	            File musicPath=new File(musicLocation);
	            if(musicPath.exists()){
	                AudioInputStream audioInput=AudioSystem.getAudioInputStream(musicPath);
	                Clip clip=AudioSystem.getClip();
	                clip.open(audioInput);
	               clip.start();	            	              	                 
	                //JOptionPane.showMessageDialog(null, "press ok");
	            }else
	                System.out.println("javaapplication9.MusicStaff.playMusic()");
	        }
	        catch(Exception ex)
	        {
	            ex.printStackTrace();
	        }
	    }
}
