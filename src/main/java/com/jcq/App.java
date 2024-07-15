package com.jcq;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

public class App 
{
    public static void main(String[] args) {
        
        try {
            GameScreen.init();

            //Cuadros de novela
            GameScreen.drawSeparatingRectangle(new TerminalPosition(1, 27), new TerminalSize(32, 17));
            GameScreen.drawSeparatingRectangle(new TerminalPosition(33, 27), new TerminalSize(68, 17));
            GameScreen.drawSeparatingRectangle(new TerminalPosition(101, 27), new TerminalSize(32, 17));
            GameScreen.drawSeparatingRectangle(new TerminalPosition(1, 1), new TerminalSize(132, 26));

            GameScreen.refresh();

            readFile("");
            GameScreen.nextPage();
            GameScreen.kill();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void readFile(String fileName) throws InterruptedException{
        //Todo tuyo
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Aliquet porttitor lacus luctus accumsan tortor posuere ac ut. Elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue. Elit eget gravida cum sociis natoque penatibus. Lectus urna duis convallis convallis tellus id interdum velit. Faucibus vitae aliquet nec ullamcorper sit amet risus. Molestie ac feugiat sed lectus vestibulum mattis. Sed elementum tempus egestas sed. Ac tortor vitae purus faucibus ornare suspendisse. Purus faucibus ornare suspendisse sed nisi lacus sed viverra. Semper feugiat nibh sed pulvinar proin gravida hendrerit. Lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit. Lectus urna duis convallis convallis tellus id interdum velit. Nec ullamcorper sit amet risus nullam eget. Venenatis urna cursus eget nunc scelerisque. Lectus arcu bibendum at varius vel pharetra vel turpis. In iaculis nunc sed augue.";

        GameScreen.printPortrait(0, "jean/src/main/java/com/jcq/Jean.txt");
        GameScreen.printPortrait(1, "jean/src/main/java/com/jcq/Moscardina.txt");

        String nameJ = "Jean";
        String nameM = "Moscardina";

        GameScreen.drawSeparatingRectangle(new TerminalPosition(35, 28), new TerminalSize(nameJ.length()+2, 3));
        GameScreen.drawSeparatingRectangle(new TerminalPosition(100-nameM.length()-3, 28), new TerminalSize(nameM.length()+2, 3));
        GameScreen.print(nameJ, 36, 29);
        GameScreen.print(nameM, 100-nameM.length()-2, 29);

        int line = 32;
        GameScreen.setCursor(35, line);
        int screenLimit = 99;

        String[] words = lorem.split(" ");
        for (String word : words) {
            
            if (word.length() > screenLimit - GameScreen.getCursorX()) {
                if (line == 41) {
                    GameScreen.nextPage();
                    line = 32;
                    GameScreen.setCursor(35, line);
                }
                else {
                    line++;
                    GameScreen.setCursor(35, line);
                }
            }

            for (int i = 0; i < word.length(); i++) {
                GameScreen.print(word.charAt(i));
                GameScreen.refresh();
                Thread.sleep(20);
            }
            GameScreen.nextXpos();
        }

    }
}

