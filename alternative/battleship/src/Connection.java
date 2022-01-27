import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Stack;
import java.util.TimerTask;

public class Connection {

    private static final Stack shotLog = new Stack();
    private static BufferedReader in;
    private static BufferedWriter usr;
    private static Writer out;
    private static boolean isServer;
    private static String message;
    private static boolean turn = true;
    private static int readyCounter = 0;
    private static Socket so;
    private static java.util.Timer timer;
    private static boolean Multiplayer = false;

    /**
     * Constructor for Connection
     *
     * @param in  Buffered Reader which carries the input
     * @param out Writer which carries the output
     * @param usr Buffered Writer which
     */
    public Connection(BufferedReader in, Writer out, BufferedWriter usr) {
        Connection.in = in;
        Connection.out = out;
        Connection.usr = usr;
    }

    public static boolean isServer() {
        return isServer;
    }

    public static void setServer(boolean bool) {
        isServer = bool;
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

    public static java.util.Timer getTimer() {
        return timer;
    }

    public static BufferedWriter getUsr() {
        return usr;
    }

    /**
     * @param x x-coordinate
     * @param y y-coordinate
     *          push x and y Coordinates onto stack
     */
    public static void pushShot(int x, int y) {
        int[] xy = new int[2];
        xy[0] = x;
        xy[1] = y;
        shotLog.push(xy);
    }

    /**
     * Get current shot coordinates
     *
     * @return int array containing x and y coordinates
     */
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

    public void setMessage(String message) {
        Connection.message = message;
    }

    public static Socket getS() {
        return so;
    }

    public static void setS(Socket s) {
        so = s;
    }

    public static boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean b) {
        turn = b;
    }

    public static void sendMessage(int x, int y) {
        if (isServer()) {
            try {
                if (getTurn()) {
                    getOut().write(String.format("shot %s %s%n", x, y));
                    getOut().flush();
                    pushShot(x, y);
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
                if (getTurn()) {
                    getOut().write(String.format("shot %s %s%n", x, y));
                    getOut().flush();
                    pushShot(x, y);
                    Client.getConnection().setTurn(false);
                    turn = false;
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
                if (message.contains("save") || message.contains("load") || getTurn() || (message.equals("ready") && readyCounter == 0)) {
                    if (message.equals("ready")) {
                        readyCounter = 1;
                    }
                    getOut().write(String.format("%s%n", message));
                    getOut().flush();
                    System.out.println("outgoing>> " + message);
                    Server.getConnection().setTurn(false);
                    turn = false;
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        } else {
            try {
                if (message.contains("save") || getTurn() || (message.equals("ready") && readyCounter == 0)) {
                    if (message.equals("ready")) {
                        readyCounter = 1;
                    }
                    getOut().write(String.format("%s%n", message));
                    getOut().flush();
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

    public void setSocket(Socket s) {
        so = s;
    }

    static class inboundMessageLoop extends SwingWorker<Object, Object> {
        String previousMessage = "";

        @Override
        protected java.util.Timer doInBackground() throws Exception {
            timer = new java.util.Timer();
            TimerTask timerTask = new TimerTask() {
                final boolean newTurn = true;

                @Override
                public void run() {
                    String message = Connection.getMessage();
                    if (message.contains("answer")) {
                        previousMessage = message;
                        int[] shot = Connection.peekShot();
                        int shipState = Integer.parseInt(message.split(" ")[1]);
                        int x = shot[0];
                        int y = shot[1];
                        if (shipState == 1 || shipState == 2) {
                            GUI.colorButtons("client", shot[0], shot[1], "Grey");
                            GUI.enemyBoard.getFieldArray()[x][y].isHit();
                            if (previousMessage != message) {
                                GUI.hitCounter--;
                            }
                        } else {
                            GUI.colorButtons("client", shot[0], shot[1], "Red");
                            GUI.enemyBoard.getFieldArray()[x][y].isMiss();
                            Connection.sendMessage("pass");
                        }
                    }
                    if (message.contains("shot")) {
                        previousMessage = message;
                        int x = Integer.parseInt(message.split(" ")[1]);
                        int y = Integer.parseInt(message.split(" ")[2]);
                        GUI.userBoard.shot(x, y);
                        if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                            if (previousMessage != message) {
                                GUI.enemyHitCounter--;
                            }
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
                    if (message.contains("load")) {
                        long id = Long.valueOf(message.split(" ")[1]);
//                            Controller.clientLoad(id);
                    }
                }

            };

            timer.scheduleAtFixedRate(timerTask, 0, 500);
            return timer;
        }
    }
}