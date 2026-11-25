import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeApp {
    public static void main(String[] args) throws Exception {
        organizingRecipes(recipeSearch(userInput()));
    }

    public static String userInput(){
        //Asks for the user to input a ingredient 
        System.out.println("Please enter an ingredient to search for recipes: ");
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            String ingredient = scanner.nextLine();
            //removing the extra spaces
            ingredient = ingredient.trim();
            //turing it into lower case
            ingredient = ingredient.toLowerCase();
            //Returning the ingredient inputted by the user
            return ingredient;
        }
    }

    public static String recipeSearch(String ingredient) throws IOException, InterruptedException {
        //Importing the API 
        //Fix this where it's not hardcoded if possible.
        String apiKey = "b411b9d9ed3c41dc83046d3f4cc0db24";
        
        String apiUrl = "https://api.spoonacular.com/recipes/findByIngredients?" + "ingredients=" + ingredient + "&apiKey=" + apiKey;
        HttpClient client  = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response testing: " + response.body());

        //Checking if the response is successful
        if (response.statusCode() == 200) {
            System.out.println("API request successful!");
            return response.body();
            // Process the response data here
        } else {
            System.out.println("API request failed with status code: " + response.statusCode());
            return "";
        }
    }

    public static void organizingRecipes(String recipeResponse) throws Exception {
        //Check if the reponse from recipeSearch method is empty
        if (recipeResponse == null){
            System.out.println("No recipe data availabe.");
            return;
        }

        //Converting the reponse from all together into a list
        JSONArray recipesList = new JSONArray(recipeResponse);

        //Creating a new Array list to add the names of the recipes into one list:
        ArrayList<String> recipeNames = new ArrayList<>();
        //Adding the names of the recipe into the list
        for (int i = 0; i < recipesList.length(); i++){
            JSONObject recipe = (JSONObject) recipesList.get(i);
            String title = recipe.getString("title");
            recipeNames.add(title);
        }

        //Displaying the number of recipes found
        System.out.println("Number of recipes found: " + recipeNames.size());
        //Printing out each recipe
        for (String name : recipeNames){
            System.out.println(name);
        }

    }

    //Possible more ideas to add:
    //A method where the user can pick the title and see how the recipe looks
    //HUGE MAYBE: showing the images of how to recipe looks.
}
