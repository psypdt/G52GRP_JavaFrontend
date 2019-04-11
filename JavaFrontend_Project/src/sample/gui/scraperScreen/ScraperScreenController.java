package sample.gui.scraperScreen;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sample.functionality.parsing.parser.JSONParser;
import java.util.ArrayList;



public class ScraperScreenController
{
    private String m_Id;
    private ArrayList<String> m_LoginTags = new ArrayList<>();
    @FXML private Pane background_pane;
    @FXML private VBox vertical_grid;
    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Button login_button;

    /**
     * Initializer for {@code ScraperscreenView.fxml}.
     * @implSpec This method initialises {@link #m_LoginTags} and then calls
     * {@link JSONParser#login(String, String, String, String, ArrayList, boolean)} from {@link JSONParser}.
     */
    @FXML private void initialize()
    {
        login_button.setOnAction((e) ->
        {
            JSONArray output;
            JSONParser parser = new JSONParser();

            switch (m_Id)
            {
                //Set a m_Id for the URL, so when the URL needs to be used it can only input the m_Id, set form tags.
                case "Moodle (courses)":
                {
                    m_LoginTags.add("#login");
                    m_LoginTags.add("#username");
                    m_LoginTags.add("#password");

                    //Tags to be parsed: div.m-l-1, a.list-group-item
                    output = parser.login("https://moodle.nottingham.ac.uk/login/index.php",
                            "div.m-l-1",
                            username_field.getText(),
                            password_field.getText(),
                            m_LoginTags, true);

                    //DEBUG: Ensure correct tag was parsed.
                    //System.out.println(output.toString());

                    displayElements(output);
                    break;
                }
                case "Blue Castle (Grades)":
                {
                    m_LoginTags.add("form");
                    m_LoginTags.add("#UserName");
                    m_LoginTags.add("#Password");
                    output = parser.login("https://bluecastle-results.nottingham.ac.uk/Account/Login?ReturnUrl=%2f",
                            "h4",
                            username_field.getText(),
                            password_field.getText(),
                            m_LoginTags, true);

                    //DEBUG: Ensure correct tag was parsed.
                    //System.out.println(output.toString());

                    displayElements(output);
                    break;
                }
            }
        });
    }

    /**
     * Setter for the {@link #m_Id} of the {@link ScraperScreenController}.
     * @param id The {@link #m_Id} that describes what type of site this controller is handling.
     */
    public void setId(String id) { this.m_Id = id; }


    /**
     * Creates a JavaFX representation of a {@link JSONArray} representing HTML elements.
     * @param elements {@link JSONArray} of HTML elements to convert to JavaFX objects.
     */
    @FXML private void displayElements(JSONArray elements)
    {
        // Set-up the primary container to hold the JavaFX objects.
        vertical_grid = new VBox();
        vertical_grid.setLayoutX(100);
        vertical_grid.setLayoutY(100);

        // Remove the login form and put the primary container into the view.
        background_pane.getChildren().clear();
        background_pane.getChildren().add(vertical_grid);

        // Iterate through the JSON array and display each element.
        for (int i = 0; i < elements.length(); i++)
        {
            try {
                displayElement(elements.getJSONObject(i), vertical_grid);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                vertical_grid.getChildren().add(new Text("Could not load :("));
            }
        }
    }


    /**
     * Create an equivalent JavaFX object for a HTML element and displays it within a given JavaFX container element.
     * @param element JSON representation of a HTML element.
     * @param container JavaFX container element to display within.
     */
    @FXML private void displayElement(JSONObject element, Pane container)
    {
        Node node = null;
        String type; //The type of element inside the JSONObject, child, button etc.

        // Check the HTML tag name.
        try {
            type = element.getString("type");
        }
        catch (JSONException e) {
            e.printStackTrace();
            type = "p";
        }

        try
        {
            switch (type.toLowerCase())
            {
                case "button":
                {
                    // HTML button -> FXML button.

                    // Check for element text.
                    String buttonText = "";
                    if (element.has("text")) buttonText = element.getString("text");
                    if (buttonText.equals("")) buttonText = "Click me!";  // Default text if none found.

                    node = new Button(buttonText);
                    break;
                }
                case "a":
                {
                    // HTML a -> FXML hyperlink.

                    // Check for element text.
                    String linkText = "";
                    if (element.has("text")) linkText = element.getString("text");
                    if (linkText.equals("")) linkText = "Click me!";  // Default text if none found.

                    node = new Hyperlink(linkText);
                    break;
                }
                default:
                {
                    // Check for element text.
                    String text = "";
                    if (element.has("text")) text = element.getString("text");
                    if (text.equals("")) text = "This is text";  // Default text if none found.

                    node = new Text(text);
                    break;
                }
            }

            // Append the JavaFX object to the displayable container.
            container.getChildren().add(node);

            // Recurse through any and all children of the element.
            if (element.has("children"))
            {
                JSONArray children = element.getJSONArray("children");

                // New container, one level deeper.
                Pane childContainer = new VBox();

                // If the element has children, create a "sub container" and place the children inside it.
                for (int i = 0; i < children.length(); i++)
                {
                    displayElement(children.getJSONObject(i), childContainer);
                }
                container.getChildren().add(childContainer);
            }

        } catch (JSONException e) { e.printStackTrace(); }
    }
}
