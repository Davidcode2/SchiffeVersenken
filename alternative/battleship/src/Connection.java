import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Stack;
import java.util.TimerTask;

public class Connection {

    private static BufferedReader in;
    private static BufferedWriter usr;
    private static Writer out;

    private static boolean isServer;
    private static String message;
    private static boolean turn = true;
    private static int readyCounter = 0;
    private static Socket so;

    private static boolean Multiplayer = false;

    public Connection(BufferedReader in, Writer out, BufferedWriter usr) {
        this.in = in;
        this.out = out;
        this.usr = usr;
    }

    public static void setServer(boolean bool) {
        isServer = bool;
    }
    public static boolean isServer() {
        return isServer;
    }

    public static boolean Multiplayer() {
        return Multiplayer;
    }
    public static void setMultiplayer(boolean b) {
        Multiplayer = b;
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static Writer getOut() {
        return out;
    }

    public static BufferedWriter getUsr() {
        return usr;
    }
    private static Stack shotLog = new Stack();
    public static void pushShot(int x, int y) {
        int[] xy = new int[2];
        xy[0] = x;
        xy[1] = y;
        shotLog.push(xy);
    }
    public static int[] popShot() {
        if (shotLog.empty()) {
            return null;
        }
        int[] shot = (int[]) shotLog.pop();
        System.out.println(shot);
        System.out.println(shot.toString());
        return shot;
    }
    public static int[] peekShot() {
        if (shotLog.empty()) {
            return null;
        }
        int[] shot = (int[]) shotLog.peek();
        return shot;
    }

    public static String getMessage() {
        return message;
    }

    public static void setS(Socket s) {
       so = s;
    }
    public static Socket getS() {
        return so;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTurn(boolean b) {
        turn = b;
    }

    public boolean getTurn() {
        return turn;
    }

    public static void sendMessage(int x, int y) {
        if (isServer()) {
            try {
                if (Server.getConnection().getTurn()) {
                    Server.getConnection().getOut().write(String.format("shot %s %s%n", x,y));
                    Server.getConnection().getOut().flush();
                    pushShot(x,y);
                    Server.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        } else {
            try {
                if (Client.getConnection().getTurn()) {
                    Client.getConnection().getOut().write(String.format("shot %s %s%n", x,y));
                    Client.getConnection().getOut().flush();
                    pushShot(x,y);
                    Client.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        }
    }
    public static void sendMessage(String message) {
        // if turn == true -> Server
        if (isServer()) {
            try {
                if (message.contains("save") || Server.getConnection().getTurn() || (message.equals("ready") && readyCounter == 0)) {
                    if (message.equals("ready")) {
                        readyCounter = 1;
                    }
                    Server.getConnection().getOut().write(String.format("%s%n", message));
                    Server.getConnection().getOut().flush();
                    System.out.println("outgoing>> " + message);
                    Server.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        } else {
            try {
                if (message.contains("save") || Client.getConnection().getTurn() || (message.equals("ready") && readyCounter == 0)) {
                    if (message.equals("ready")) {
                        readyCounter = 1;
                    }
                    Client.getConnection().getOut().write(String.format("%s%n", message));
                    Client.getConnection().getOut().flush();
                    System.out.println("outgoing>> " + message);
                    Client.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        }

    }

    static class inboundMessageLoop extends SwingWorker<Object, Object> {
        String previousMessage = "";
        @Override
        protected Object doInBackground() throws Exception {
            new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    String message = Connection.getMessage();
                    if (message.contains("answer")) {
                        previousMessage = message;
                        int[] shot = Connection.peekShot();
                        int shipState = Integer.parseInt(message.split(" ")[1]);
                        if (shipState == 1 || shipState == 2) {
                            GUI.colorButtons("client", shot[0],shot[1], "Grey");
                        } else {
                            GUI.colorButtons("client", shot[0],shot[1], "Red");
                            Connection.sendMessage("pass");
                        }
                    }
                    if (message.contains("shot")) {
                        previousMessage = message;
                        int x = Integer.parseInt(message.split(" ")[1]);
                        int y = Integer.parseInt(message.split(" ")[2]);
                        GUI.userBoard.shot(x, y);
                        if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                            if (GUI.userBoard.getFieldArray()[x][y].isSunk()) {
                                Connection.sendMessage(String.format("answer %s", 2));
                            } else {
                                Connection.sendMessage(String.format("answer %s", 1));
                            }
                        } else {
                            Connection.sendMessage(String.format("answer %s", 0));
                        }
                    }
                    if (message.contains("save") && !previousMessage.contains("save")) {
                        previousMessage = message;
                        long id = Long.valueOf(message.split(" ")[1]);
                        try {
                            Controller.saveSession(GUI.userBoard, GUI.enemyBoard, id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 0, 500);
            return null;
        }
    }
}
