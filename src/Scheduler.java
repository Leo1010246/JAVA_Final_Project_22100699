import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

    public static int time;
    public static int difficulty;
    public static boolean sw; // 쓰레드 제어

    public static int x1;
    public static int x2;
    public static int x3;
    public static int x4;

    public Scheduler(int note_num, File file, int difficulty) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

        String music_name = file.getName();
        music_name = music_name.substring(0, music_name.length() - 4); // 확장자 자르기

        Queue<Integer> key1 = new LinkedList<>();
        Queue<Integer> key2 = new LinkedList<>();
        Queue<Integer> key3 = new LinkedList<>();
        Queue<Integer> key4 = new LinkedList<>();

        int key_note_count = setKey(key1, key2, key3, key4, file); // 각 노트드롭에 키 배열 세팅

        final int width = 100;
        final int height = 30;

        Scheduler.time = 0; // 0.1초 단위
        Scheduler.difficulty = difficulty;
        Scheduler.sw = true;

        Display display = new Display(note_num, music_name, key_note_count, width, height); // Display 선언, note_num = 4

        String real_music_name = music_name.substring(0, music_name.length() - 2);
        new KeyCheck_And_MusicPlay(real_music_name);

        Scheduler.x1 = (Display.screen_width / 5) - 1;
        Scheduler.x2 = (Display.screen_width * 2 / 5) - 2;
        Scheduler.x3 = (Display.screen_width * 3 / 5) - 2;
        Scheduler.x4 = (Display.screen_width * 4 / 5) - 2;
        int len = 4;

        display.clearScreen();
        while(Scheduler.sw) {
            display.doGravity();

            if(!key1.isEmpty() && key1.peek() == Scheduler.time) {
                key1.remove();
                display.makeNoteInPlace(x1, len, key1.poll());
            }
            if(!key2.isEmpty() && key2.peek() == Scheduler.time) {
                key2.remove();
                display.makeNoteInPlace(x2, len, key2.poll());
            }
            if(!key3.isEmpty() && key3.peek() == Scheduler.time) {
                key3.remove();
                display.makeNoteInPlace(x3, len, key3.poll());
            }
            if(!key4.isEmpty() && key4.peek() == Scheduler.time) {
                key4.remove();
                display.makeNoteInPlace(x4, len, key4.poll());
            }

            display.setLine();
            //display.clearScreenJ();
            display.printScreen();
            Thread.sleep(100); // 0.1 sec delay
            Scheduler.time++;
        }
        //display.printNullBox();
        display.printInfo();

    }

    public int setKey(Queue<Integer> key1, Queue<Integer> key2, Queue<Integer> key3, Queue<Integer> key4, File file) {

        int key_note_count = 0;

        String file_path = file.getPath();
        file_path = file_path.substring(1);

        String str = "";

        try {
            FileInputStream fileInputStream = new FileInputStream(file_path);

            int i = 0;
            while((i = fileInputStream.read()) != -1) {
                str += (char)i;
            }

            fileInputStream.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        String[] split_str = str.split(" ");

        int note_type;
        int note_key;
        int delay_num;
        for(int i = 0 ; i < split_str.length ; i += 3) {
            key_note_count++;
            note_type = Integer.parseInt(split_str[i]);
            note_key = Integer.parseInt(split_str[i + 1]);
            delay_num = Integer.parseInt(split_str[i + 2]);
            try {
                switch(note_key) {
                    case 1:
                        key1.add(delay_num);
                        key1.add(note_type);
                        break;
                    case 2:
                        key2.add(delay_num);
                        key2.add(note_type);
                        break;
                    case 3:
                        key3.add(delay_num);
                        key3.add(note_type);
                        break;
                    case 4:
                        key4.add(delay_num);
                        key4.add(note_type);
                        break;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Over Array Error");
            }
        }

        return key_note_count;
    }

}
