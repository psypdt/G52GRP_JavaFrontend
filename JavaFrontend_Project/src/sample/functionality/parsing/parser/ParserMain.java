package sample.functionality.parsing.parser;


import javafx.application.Application;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sample.functionality.forms.formSending.FormSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParserMain extends Application {
    JSONParser parser;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);
        primaryStage.show();

        parser = new JSONParser();
        String s = parser.login("https://moodle.nottingham.ac.uk/login/index.php", "a.list-group-item", "psyjm9", "");
        //String s = parser.getJSON("a.list-group-item");
        System.out.println(s);


        /*
        Parser parse = new Parser();
        String output = parse.parsedHTML("https://mynottingham.nottingham.ac.uk/psp/psprd/EMPLOYEE/EMPL/h/?tab=PAPP_GUEST", "div.nav_icons");
        System.out.println(output);*/
    }

    public static void main(String[] args) { launch(args); }

}
