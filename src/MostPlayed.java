package src;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import database.GamesImpl;

public class MostPlayed {

	//Instance variable
	public static ArrayList<Game> games = new ArrayList<>();

	public MostPlayed() {
		try {
			//Calling API
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.steampowered.com/ISteamChartsService/GetGamesByConcurrentPlayers/v1/?"))
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();

			//Grabbing JSON response
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();

			//Getting array from JSON
			JsonNode jsonArray = GamesImpl.map.readTree(responseBody);
			jsonArray = jsonArray.get("response").get("ranks");
			
			for(JsonNode node : jsonArray) 
			{
				int id = node.get("appid").asInt();
				games.add(GamesImpl.getGame(id));
				
			}
			
		} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
		} 
	}

	public ArrayList<Game> getGames() {
		return games;
	}
	
	public Game getGame(int i) {
		return games.get(i);
	}

	
	public int getSize() {
		return games.size();
	}

}
