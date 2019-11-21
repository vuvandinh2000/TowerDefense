package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        Frame frame = new Frame("Game Tower Defense by Vũ Văn Định");
        Button button = new Button("START!");
        button.setBounds(400, 200, 100, 40);
        frame.add(button);
        frame.setSize(900, 405);
        frame.setLayout(null);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.play("C://Users/HAC/Desktop/TowerDefense/src/resources/backgroundMusic.wav");
                new GameStage();
            }
        });

    }
}