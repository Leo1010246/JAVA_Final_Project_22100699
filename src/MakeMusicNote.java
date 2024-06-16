import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Queue;

public class MakeMusicNote {

    public static int time_m;
    public static int difficulty_m;
    public static boolean sw_m; // 쓰레드 제어

    public static int x1_m;
    public static int x2_m;
    public static int x3_m;
    public static int x4_m;

    public MakeMusicNote(int note_num, File file, int difficulty) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

        String music_name = file.getName();
        music_name = music_name.substring(0, music_name.length() - 4); // 확장자 자르기

        FileOutputStream fos = new FileOutputStream(file.getName(), false);

        final int width = 100;
        final int height = 30;

        MakeMusicNote.time_m = 0; // 0.1초 단위
        MakeMusicNote.difficulty_m = difficulty;
        MakeMusicNote.sw_m = true;

        Display display = new Display(note_num, music_name, 0, width, height); // Display 선언, note_num = 4

        String real_music_name = music_name.substring(0, music_name.length() - 2);
        new KeyCheck_And_MusicPlay(real_music_name);

        MakeMusicNote.x1_m = (Display.screen_width / 5) - 1;
        MakeMusicNote.x2_m = (Display.screen_width * 2 / 5) - 2;
        MakeMusicNote.x3_m = (Display.screen_width * 3 / 5) - 2;
        MakeMusicNote.x4_m = (Display.screen_width * 4 / 5) - 2;
        int len = 4;

        display.clearScreen();
        while(MakeMusicNote.sw_m) {
            display.doGravity();

            if(KeyCheck_And_MusicPlay.key_array[0] == 1) {
                fos.write("1 ".getBytes());
                fos.write("1 ".getBytes());
                String t = MakeMusicNote.time_m + " ";
                fos.write(t.getBytes());
                display.makeNoteInPlace(x1_m, len, 1);
            }
            if(KeyCheck_And_MusicPlay.key_array[1] == 1) {
                fos.write("1 ".getBytes());
                fos.write("2 ".getBytes());
                String t = MakeMusicNote.time_m + " ";
                fos.write(t.getBytes());
                display.makeNoteInPlace(x2_m, len, 1);
            }
            if(KeyCheck_And_MusicPlay.key_array[2] == 1) {
                fos.write("1 ".getBytes());
                fos.write("3 ".getBytes());
                String t = MakeMusicNote.time_m + " ";
                fos.write(t.getBytes());
                display.makeNoteInPlace(x3_m, len, 1);
            }
            if(KeyCheck_And_MusicPlay.key_array[3] == 1) {
                fos.write("1 ".getBytes());
                fos.write("4 ".getBytes());
                String t = MakeMusicNote.time_m + " ";
                fos.write(t.getBytes());
                display.makeNoteInPlace(x4_m, len, 1);
            }

            display.setLine();
            //display.clearScreenJ();
            display.printScreen();
            Thread.sleep(100); // 0.1 sec delay
            MakeMusicNote.time_m++;
        }
        System.out.println("===================");
        System.out.println("Finish make notes");
        System.out.println("===================");
        System.out.flush();

//        String sourceFilePath = file.getName();
//        String destinationFilePath = "/src/music_note/" + file.getName();
//
//        try {
//            // 파일 복사
//            Files.copy(Paths.get(sourceFilePath), Paths.get(destinationFilePath), StandardCopyOption.REPLACE_EXISTING);
//
//            // 원본 파일 삭제 (옵션)
//            Files.delete(Paths.get(sourceFilePath));
//
//            //System.out.println("File moved successfully.");
//        } catch (IOException e) {
//            System.out.println("An error occurred while moving the file.");
//            e.printStackTrace();
//        }

        fos.close();

    }

}
