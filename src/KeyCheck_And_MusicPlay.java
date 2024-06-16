import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class KeyCheck_And_MusicPlay extends JFrame {

    private JLabel keyMessage;
    private Clip clip; // Clip 객체를 클래스 변수로 선언해 음악 재생을 관리

    public static int[] key_array;

    public KeyCheck_And_MusicPlay(String music_name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        int note_number = 4;
        key_array = new int[note_number];
        for(int i = 0 ; i < note_number ; i++) KeyCheck_And_MusicPlay.key_array[i] = 0;

        setTitle("키 입력, 음악 재생용 창");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.addKeyListener(new MyKeyListener());

        keyMessage = new JLabel();
        c.add(keyMessage);

        setSize( 100,100);
        setVisible(true);

        c.setFocusable(true);
        c.requestFocus();

        String path = System.getProperty("user.dir");
        String musicFilePath = path + "/src/music_ori/" + music_name + ".wav"; // 음악 파일 경로

        File musicFile = new File(musicFilePath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                close();
            }
        });

        float volume = -12.0f;

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume); // 볼륨을 조절합니다. 값은 데시벨(dB)입니다. 예: -10.0f
        }

        clip.start(); // 음악 재생 시작
    }

    public class MyKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            //int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();

            keyMessage.setText(Integer.toString(keyChar));

            //System.out.println("key pressed " + keyChar);
            if(keyChar == 'a' || keyChar == 'A') {
                KeyCheck_And_MusicPlay.key_array[0] = 1;
            }
            else if(keyChar == 's' || keyChar == 'S') {
                KeyCheck_And_MusicPlay.key_array[1] = 1;
            }
            else if(keyChar == 'k' || keyChar == 'K') {
                KeyCheck_And_MusicPlay.key_array[2] = 1;
            }
            else if(keyChar == 'l' || keyChar == 'L') {
                KeyCheck_And_MusicPlay.key_array[3] = 1;
            }
            else if (keyChar == 'q' || keyChar == 'Q') {
                close();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("key released");
        }

        @Override
        public void keyTyped(KeyEvent e) {
            //System.out.println("key typed");
        }

    }

    public void close() {
        KeyCheck_And_MusicPlay.key_array[0] = 0;
        KeyCheck_And_MusicPlay.key_array[1] = 0;
        KeyCheck_And_MusicPlay.key_array[2] = 0;
        KeyCheck_And_MusicPlay.key_array[3] = 0;
        Scheduler.sw = false; // 음악이 끝났음 => 쓰레드 종료
        MakeMusicNote.sw_m = false; // 음악이 끝났음 => 쓰레드 종료
        clip.close();
        dispose();
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new KeyCheck_And_MusicPlay("쿨(COOL)-애상"); // KeyCheck_And_MusicPlay 클래스의 인스턴스를 생성하여 프로그램 시작
    }

}