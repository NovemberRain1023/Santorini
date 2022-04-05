package edu.cmu.cs214.hw3;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import fi.iki.elonen.NanoHTTPD;

/**
 * Hello world!
 *
 */
public class App extends NanoHTTPD
{
    public static void main( String[] args ) {
        try {
            new App();
        } catch (IOException ioe) {
            System.out.println("Could not start the server:\n" + ioe);
        }

//        System.out.println( "Please choose your worker's initial position: " );
//        Scanner sc = new Scanner(System.in);
//        String pos = sc.nextLine();
//        String[] position = pos.split(",");
//        List<Worker> workers = new LinkedList<>();
//        for (int i = 0; i < position.length - 1; i += 2) {
//            int x = Integer.parseInt(position[i]);
//            int y = Integer.parseInt(position[i + 1]);
//            workers.add(new Worker(x, y));
//        }
//        for (Worker worker : workers) {
//            System.out.println(worker.getPosition()[0]);
//            System.out.println(worker.getPosition()[1]);
//        }
//        new UI().Santorini(workers);
//        for (String s : position) {
//            System.out.println(s);
//        }
    }

    private UI ui;

    public App() throws IOException {
        super(8080);
        this.ui = new UI();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }
    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame")) {
            this.ui = new UI();
        } else if (uri.equals("/play")) {
            this.ui = this.ui.playTheGame(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
        } else if (uri.equals("/god")) {
            this.ui = this.ui.setGod(params.get("god"));
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.ui);
        return newFixedLengthResponse(gameplay.toString());
    }
}
