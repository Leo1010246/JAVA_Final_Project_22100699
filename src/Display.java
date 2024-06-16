import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Display {

    public static int screen_width;
    public static int screen_height;

    public static char[][] screen;

    public static int note_num; // NoteDrop 숫자

    public static String music_name;

    public static int max_score;
    public static int score; // (노트 하나당 1~5점)의 합

    public static int key_note_count;

    public Display(int note_num, String music_name, int key_note_count) {
        Display.screen_width = 20;
        Display.screen_height = 20;

        Display.screen = new char[Display.screen_height][Display.screen_width];

        Display.note_num = note_num;
        Display.music_name = music_name; // NoteDrop 숫자
        Display.key_note_count = key_note_count; // 총 노트 수
        Display.max_score = key_note_count * 5;
        Display.score = 0;
    }

    public Display(int note_num, String music_name, int key_note_count, int screen_width, int screen_height) {
        Display.screen_width = screen_width;
        Display.screen_height = screen_height;

        Display.screen = new char[Display.screen_height][Display.screen_width];

        Display.note_num = note_num;
        Display.music_name = music_name; // NoteDrop 숫자
        Display.key_note_count = key_note_count; // 총 노트 수
        Display.max_score = key_note_count * 5;
        Display.score = 0;
    }

    public int getScreen_width() {
        return screen_width;
    }

    public int getScreen_height() {
        return screen_height;
    }

    public void printScreen() throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        String s = "";
        s += printNullBox(1);
        s += printUI(1);
        for(int i = 0 ; i < screen_height ; i++) {
            for(int j = 0 ; j < screen_width ; j++) {
                //System.out.print(screen[i][j]);
                s += screen[i][j];
            }
            //System.out.print('\n');
            s += '\n';
        }
        //System.out.print(s);
        out.write(s);
        out.flush();
        //System.out.flush();
    }

//    public static void clearScreenJ() {
//        AnsiConsole.systemInstall();
//        AnsiConsole.out.print(Ansi.ansi().eraseScreen().cursor(1, 1));
//        AnsiConsole.systemUninstall();
//    }
//
//    public void printScreenJ() throws IOException {
//        AnsiConsole.systemInstall();
//
//        gotoxy(1, 1);
//        for(int i = 0 ; i < screen_height ; i++) {
//            String s = "";
//            for(int j = 0 ; j < screen_width ; j++) {
//                s += screen[i][j];
//            }
//            gotoxy(1, i + 1);
//            System.out.print(s);
//        }
//
//        AnsiConsole.systemUninstall();
//    }
//
//    protected static void gotoxy(int x, int y) {
//        AnsiConsole.out.print(Ansi.ansi().cursor(y, x));
//    }


    public void printInfo() {
        System.out.println("===================");
        System.out.println("Music Name : " + music_name);
        int min = (Scheduler.time / 10) / 60;
        int sec = (Scheduler.time / 10) % 60;
        System.out.println("Music Time : " + min + ":" + sec);
        System.out.println("Difficulty : " + diffInfo());
        //System.out.println("Max Combo : " + music_name);
        //System.out.println("Perfect Note : " + music_name);
        //System.out.println("Good Note : " + music_name);
        //System.out.println("Bad Note : " + music_name);
        System.out.println("Score Percentage : " + (int)(((double)score / (double)max_score) * 100.0) + "%");
        System.out.println("===================");
        System.out.flush();
    }

    private String diffInfo() {
        if(Scheduler.difficulty == 1) return "Very Easy";
        else if(Scheduler.difficulty == 2) return "Easy";
        else if(Scheduler.difficulty == 3) return "Normal";
        else if(Scheduler.difficulty == 4) return "Hard";
        else if(Scheduler.difficulty == 5) return "Very Hard";
        else return "";
    }

//    public void printNullBox() {
//        for(int i = 0 ; i < screen_height ; i++) {
//            System.out.print('\n');
//        }
//    }

    public String printNullBox(int t) {
        String s = "";
        for(int i = 0 ; i < screen_height ; i++) {
            //System.out.print('\n');
            s += '\n';
        }
        return s;
    }

//    public void printUI() {
//        System.out.print("@" + music_name);
//        for(int i = 0 ; i < (screen_width - (1 + music_name.length() + 7) ) ; i++) {
//            System.out.print(' ');
//        }
//        System.out.printf("%2d:%2d\n", ((Scheduler.time / 10) / 60), ((Scheduler.time / 10) % 60));
//    }

    public String printUI(int t) {
        String s = "";
        //System.out.print("@" + music_name);
        s += "@" + music_name;
        for(int i = 0 ; i < (screen_width - (1 + music_name.length() + 7) ) ; i++) {
            //System.out.print(' ');
            s += ' ';
        }
        //System.out.printf("%2d:%2d\n", (Scheduler.time / 60), (Scheduler.time % 60));
        int min = ((Scheduler.time / 10) / 60);
        int sec = ((Scheduler.time / 10) % 60);
        s += min + ":" + sec + "\n";
        return s;
    }

    public void clearScreen() {
        for(int i = 0 ; i < screen_height ; i++) {
            for(int j = 0 ; j < screen_width ; j++) {
               if(i == screen_height - 4) screen[i][j] = '-';
               else screen[i][j] = ' ';
            }
        }
    }

    public void setLine() {
        for(int i = 0 ; i < screen_width ; i++) {
            if(screen[screen_height - 4][i] != '=') screen[screen_height - 4][i] = '-';
        }

        char[] g = {'G','o','o','d'};
        if(KeyCheck_And_MusicPlay.key_array[0] == 1) {
            if(Display.screen[screen_height - 3][Scheduler.x1] == '=') {
                score++;
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 3][Scheduler.x1 + i] = ' ';
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 2][Scheduler.x1 + i] = g[i];
            }
            for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 4][Scheduler.x1 + i] = '#';
            KeyCheck_And_MusicPlay.key_array[0] = 0;
        }
        if(KeyCheck_And_MusicPlay.key_array[1] == 1) {
            if(Display.screen[screen_height - 3][Scheduler.x2] == '=') {
                score++;
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 3][Scheduler.x2 + i] = ' ';
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 2][Scheduler.x2 + i] = g[i];
            }
            for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 4][Scheduler.x2 + i] = '#';
            KeyCheck_And_MusicPlay.key_array[1] = 0;
        }
        if(KeyCheck_And_MusicPlay.key_array[2] == 1) {
            if(Display.screen[screen_height - 3][Scheduler.x3] == '=') {
                score++;
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 3][Scheduler.x3 + i] = ' ';
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 2][Scheduler.x3 + i] = g[i];
            }
            for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 4][Scheduler.x3 + i] = '#';
            KeyCheck_And_MusicPlay.key_array[2] = 0;
        }
        if(KeyCheck_And_MusicPlay.key_array[3] == 1) {
            if(Display.screen[screen_height - 3][Scheduler.x4] == '=') {
                score++;
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 3][Scheduler.x4 + i] = ' ';
                for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 2][Scheduler.x4 + i] = g[i];
            }
            for(int i = 0 ; i < 4 ; i++) Display.screen[screen_height - 4][Scheduler.x4 + i] = '#';
            KeyCheck_And_MusicPlay.key_array[3] = 0;
        }
    }

    public void makeNoteInPlace(int x, int len, int type) {
        char c = '=';
        if(type == 2) c = '#';

        for(int i = 0 ; i < len ; i++) screen[0][x + i] = '=';
    }

    public void doGravity() {
        for(int i = screen_height - 1 ; i > 0 ; i--) {
            for(int j = 0 ; j < screen_width ; j++) {
                if(screen[i - 1][j] == '=') screen[i][j] = '=';
                else if(screen[i - 1][j] == 'G') screen[i][j] = 'G';
                else if(screen[i - 1][j] == 'o') screen[i][j] = 'o';
                else if(screen[i - 1][j] == 'd') screen[i][j] = 'd';
                else screen[i][j] = ' ';
            }
        }
        for(int i = 0 ; i < screen_width ; i++) screen[0][i] = ' ';
    }

}
