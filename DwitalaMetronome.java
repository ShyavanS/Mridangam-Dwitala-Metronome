import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.util.logging.*;
import javax.sound.sampled.*;
import java.io.*;

public class DwitalaMetronome {
    // Initialize beats
    public static int sankeernaBeat = 1;
    public static int khandaBeat = 1;
    public static void main(String[] args) {
        final Timer metronome = new Timer();
        // Sankeerna Chapu
        metronome.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Only play beat for beats 1, 3, 5, 7, 8
                if ((sankeernaBeat % 2 == 1 && sankeernaBeat != 9) || sankeernaBeat == 8) {
                    playWav("metronome.wav");
                    System.out.println(String.format("Sankeerna Chapu beat #%d", sankeernaBeat));
                } else if (sankeernaBeat == 9) {
                    System.out.println("Sankeerna finished");
                    // Reset beats after 1 cycle
                    sankeernaBeat = 0;
                }
                sankeernaBeat++;
            }
        }, 2000, 60000 / 120); // ms/b = (60000 ms / bpm) is half of khanda so 2 sankeerna == 1 khanda

        // Khanda Triputa
        metronome.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Play beat
                playWav("metronome.wav");
                System.out.println(String.format("Khanda Triputa beat #%d", khandaBeat));
                if (khandaBeat == 9) {
                    System.out.println("Khanda finished");
                    // Reset beats after 1 cycle
                    khandaBeat = 0;
                }
                khandaBeat++;
            }
        }, 2000, 60000 / 60); // ms/b = (60000 ms / bpm) is twice of sankeerna so 1 khanda == 2 sankeerna
    }

    // Play metronome tick
    private static void playWav(String soundFilePath) {
        File sFile = new File(soundFilePath);
        if (!sFile.exists()) {
            String ls = System.lineSeparator();
            System.err.println("Can not locate the supplied sound file!" + 
                    ls + "(" + soundFilePath + ")" + ls);
            return;
        }
        try {
            Clip clip;
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sFile.getAbsoluteFile())) {
                clip = AudioSystem.getClip();
                clip.setFramePosition(0); 
                clip.open(audioInputStream);
            }
            clip.start();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger("playWav()").log(Level.SEVERE, null, ex);
        }
    }
}