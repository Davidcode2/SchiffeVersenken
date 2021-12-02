package network;

import gui.Spielgui;

import javax.swing.*;
import java.awt.*;

public class Multiplayer {

    String msg = Connection.getMessage();
        if (msg.contains("shot ")) {
        String[] temp = msg.split(" ");
        int shotx = Integer.valueOf(temp[1]);
        int shoty = Integer.valueOf(temp[2]);
        // check own ship array
        if (ships[shotx][shoty] == false) {
            ((JButton)e.getSource()).setBackground(new Color(0,0,255));
            Connection.sendMessage("answer 0");
        } else {
            ((JButton)e.getSource()).setBackground(new Color(255,0,0));
            // what does 1 2 etc mean?
            Connection.sendMessage("answer 1");
            hitCounter--;
            if(hitCounter==0) {
                frame.dispose();
                new Spielgui(8);
                return;
            }
        }
    }
}
