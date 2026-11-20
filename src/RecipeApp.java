import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RecipeApp {
    public static void main(String[] args) throws Exception {
        //Change this later to userInput Method
        recipeSearch("chicken");
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

    public static void recipeSearch(String ingredient) throws IOException, InterruptedException {
        //Importing the API 
        String apiKey = "b411b9d9ed3c41dc83046d3f4cc0db24";
        
        String apiUrl = "https://api.spoonacular.com/recipes/findByIngredients?" + "ingredients=" + ingredient + "&apiKey=" + apiKey;
        HttpClient client  = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response testing: " + response.body());

        //Checking if the response is successful
        if (response.statusCode() == 200) {
            System.out.println("API request successful!");
            // Process the response data here
        } else {
            System.out.println("API request failed with status code: " + response.statusCode());
        }
    }
}
