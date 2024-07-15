package com.jcq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public final class GameScreen {

    private static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    private static Terminal terminal = null;
    private static TextGraphics textGraphics = null;

    private GameScreen() {
        //Previene la instanciación
    }

    public static void init() {

        try {
            terminal = defaultTerminalFactory.createTerminal();
    
            terminal.enterPrivateMode();
            terminal.clearScreen();
            terminal.setCursorVisible(false);

            textGraphics = terminal.newTextGraphics();
            textGraphics.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(134, 45), '#');
            terminal.flush();
        } 
        catch (IOException e) {
            if(terminal != null)
                try {
                    terminal.close();
                }
                catch(IOException E) {
                    E.printStackTrace();
                }
                e.printStackTrace();
        }
    }

    // Puede que lanzar las excepciones sea mejor que tratarlas
    public static void setCursor (int x, int y) {
        // if (x < 0 || y < 0 || x > 134 || y > 43) return;
        // Tengo que comprobar antes como se tratan las coordenadas en Lanterna
        try {terminal.setCursorPosition(x, y);}
        catch (IOException e) {e.printStackTrace();}
    }

    public static void nextXpos() {
        // Tengo que comprobar antes como se tratan las coordenadas en Lanterna
        try {terminal.setCursorPosition(terminal.getCursorPosition().withRelativeColumn(1));}
        catch (IOException e) {e.printStackTrace();}
    }

    public static void nextYpos() {
        // Tengo que comprobar antes como se tratan las coordenadas en Lanterna
        try {terminal.setCursorPosition(terminal.getCursorPosition().withRelativeRow(1));}
        catch (IOException e) {e.printStackTrace();}
    }

    public static void print(char c){
        try {
            terminal.putCharacter(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(String s){
        try {
            textGraphics.putString(terminal.getCursorPosition(), s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(String s, int x, int y){
        textGraphics.putString(new TerminalPosition(x, y), s);
    }

    public static void drawSeparatingRectangle(TerminalPosition topLeft, TerminalSize size) {
        String s = "╔";
        for (int i = 0; i < size.getColumns()-2; i++) s+="═";
        s += "╗";
        textGraphics.putString(topLeft, s);

        s = "╚";
        for (int i = 0; i < size.getColumns()-2; i++) s+="═";
        s += "╝";

        textGraphics.putString(topLeft.withRelativeRow(size.getRows()-1), s);

        TerminalPosition topRight = new TerminalPosition(topLeft.getColumn()+size.getColumns()-1, topLeft.getRow());
        textGraphics.drawLine(topLeft.withRelativeRow(1), topLeft.withRelativeRow(size.getRows()-2), '║');
        textGraphics.drawLine(topRight.withRelativeRow(1), topRight.withRelativeRow(size.getRows()-2), '║');
    }
    
    public static void printPortrait(int direction, String filePath) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            int col = 0;
            int row = 28;

            if (direction == 0) col = 2;
            if (direction == 1) col = 103;

            while ((line = br.readLine()) != null) {

                textGraphics.putString(new TerminalPosition(col, row), line);
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void nextPage() {
        try {
            KeyStroke keyStroke = terminal.readInput();
            while(keyStroke.getKeyType() != KeyType.Enter) {keyStroke = terminal.readInput();}

            textGraphics.fillRectangle(new TerminalPosition(35, 32), new TerminalSize(64, 10), ' ');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Para luego hacerlo
    public static boolean isConsoleSizeSufficient() {return true;}

    public static void refresh() {
        try {
            if (isConsoleSizeSufficient()) terminal.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCursorX (){
        try {
            return terminal.getCursorPosition().getColumn();
        }
        catch (IOException e) {return -1;}
    }

    public static void kill() {
        try {
            terminal.close();
        }
        catch (IOException e) {e.printStackTrace();}
    }
}

                             