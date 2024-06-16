import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class Main {

    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

        final int NOTE_NUM = 4;

        Scanner scanner = new Scanner(System.in);

        String path = System.getProperty("user.dir");

        String folderPath = path + "/src/music_ori";

        System.out.println(folderPath);

        label:
        while(true) {

            System.out.println("[ Select Mode ]"); // 모드 선택
            System.out.println("[1] Play Mode");
            System.out.println("[2] Make Mode");
            System.out.println("[0] Exit");
            System.out.print(">> "); // 모드 번호 입력 받음
            String in = scanner.next();
            System.out.print("\n"); // 한칸 띄우기

            switch (in) {
                case "1": // 게임 플레이 모드
                    File folder = new File(folderPath);
                    File[] listOfFiles = folder.listFiles();

                    int count = 0; // 선택할 수 있는 음악 노트 목록 출력
                    if(listOfFiles != null) {
                        System.out.println("[ Select Music ]");
                        for (File file : listOfFiles) {
                            if (file.isFile()) {
                                count++;
                                System.out.println("[" + count + "] " + file.getName());
                            }
                        }
                    }
                    else { // 폴더 못 찾으면 오류 출력
                        System.out.println("Cannot Find Folder \"" + folderPath + "\"\n");
                        continue;
                    }

                    System.out.print(">> "); // 음악 노트 번호 입력 받음
                    String music_num_str = scanner.next();

                    int music_num = 0; // 에러 처리, 입력값 숫자 변환
                    try{
                        music_num = Integer.parseInt(music_num_str);
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Error Input\n");
                        continue;
                    }
                    if(music_num < 1 || count < music_num) {
                        System.out.println("Error Input\n");
                        continue;
                    }

                    System.out.print("\n"); // 한칸 띄우기

                    System.out.println("[ Select Difficulty ]"); // 난이도 선택
                    System.out.println("[1] Very Easy");
                    System.out.println("[2] Easy");
                    System.out.println("[3] Normal");
                    System.out.println("[4] Hard");
                    System.out.println("[5] Very Hard");
                    System.out.print(">> "); // 난이도 번호 입력 받음
                    String difficulty_num_str = scanner.next();

                    int difficulty_num = 0; // 에러 처리, 입력값 숫자 변환
                    try{
                        difficulty_num = Integer.parseInt(difficulty_num_str);
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Error Input Change\n");
                        continue;
                    }
                    if(difficulty_num < 1 || 5 < difficulty_num) {
                        System.out.println("Error Input Range\n");
                        continue;
                    }

                    String music_name = listOfFiles[music_num - 1].getName();
                    music_name = music_name.substring(0, music_name.length() - 4);
                    File music_note_d_file = new File("/src/music_note/" + music_name + "_" + difficulty_num + ".txt");

                    Scheduler s = new Scheduler(NOTE_NUM, music_note_d_file, difficulty_num); // 게임 스케쥴러 실행 , NOTE_NUM = 4
                    break;

                case "2": // 뮤직 노트 만들기 모드
                    File folder_m = new File(folderPath);
                    File[] listOfFiles_m = folder_m.listFiles();

                    int count_m = 0; // 선택할 수 있는 음악 노트 목록 출력
                    if(listOfFiles_m != null) {
                        System.out.println("[ Select Music ]");
                        for (File file : listOfFiles_m) {
                            if (file.isFile()) {
                                count_m++;
                                System.out.println("[" + count_m + "] " + file.getName());
                            }
                        }
                    }
                    else { // 폴더 못 찾으면 오류 출력
                        System.out.println("Cannot Find Folder \"" + folderPath + "\"\n");
                        continue;
                    }

                    System.out.print(">> "); // 음악 노트 번호 입력 받음
                    String music_num_str_m = scanner.next();

                    int music_num_m = 0; // 에러 처리, 입력값 숫자 변환
                    try{
                        music_num_m = Integer.parseInt(music_num_str_m);
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Error Input\n");
                        continue;
                    }
                    if(music_num_m < 1 || count_m < music_num_m) {
                        System.out.println("Error Input\n");
                        continue;
                    }

                    System.out.print("\n"); // 한칸 띄우기

                    System.out.println("[ Select Difficulty ]"); // 난이도 선택
                    System.out.println("[1] Very Easy");
                    System.out.println("[2] Easy");
                    System.out.println("[3] Normal");
                    System.out.println("[4] Hard");
                    System.out.println("[5] Very Hard");
                    System.out.print(">> "); // 난이도 번호 입력 받음
                    String difficulty_num_str_m = scanner.next();

                    int difficulty_num_m = 0; // 에러 처리, 입력값 숫자 변환
                    try{
                        difficulty_num_m = Integer.parseInt(difficulty_num_str_m);
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Error Input Change\n");
                        continue;
                    }
                    if(difficulty_num_m < 1 || 5 < difficulty_num_m) {
                        System.out.println("Error Input Range\n");
                        continue;
                    }

                    String music_name_m = listOfFiles_m[music_num_m - 1].getName();
                    music_name = music_name_m.substring(0, music_name_m.length() - 4);
                    File music_note_d_file_m = new File("/src/music_note/" + music_name + "_" + difficulty_num_m + ".txt");
                    MakeMusicNote makeMusic = new MakeMusicNote(NOTE_NUM, music_note_d_file_m, difficulty_num_m);
                    break;

                case "0": // 종료
                    System.out.println("[ Game Exit ]");
                    break label;
            }

        }

        scanner.close();

    }

}
