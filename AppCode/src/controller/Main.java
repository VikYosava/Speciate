package controller;
import view.MainFrame;

public class Main {
	public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        MainController mainController = new MainController(mainFrame);
        mainFrame.setVisible(true);
    }
}
