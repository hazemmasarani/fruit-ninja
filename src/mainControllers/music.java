package mainControllers;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class music {
	
	 void playMusic(String musicName){
	        try{
	            File musicPath=new File(musicName+".wav");
	            if(musicPath.exists()){
	                AudioInputStream audioInput=AudioSystem.getAudioInputStream(musicPath);
	                Clip clip=AudioSystem.getClip();
	                clip.open(audioInput);
	               clip.start();
	            }else
	                System.out.println("javaapplication9.MusicStaff.playMusic()");
	        } catch(Exception ex) { ex.printStackTrace(); }
	    }
}
