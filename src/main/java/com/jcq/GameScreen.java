package com.jcq;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
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

            textGraphics =terminal.newTextGraphics();
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

    //Para luego hacerlo
    public static boolean isConsoleSizeSufficient() {return true;}

    public static void kill() {
        try {
            terminal.close();
        }
        catch (IOException e) {e.printStackTrace();}
    }
}

