package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TileGsonMarshal implements JsonSerializer<Tile>, JsonDeserializer<Tile>{

	@Override
	public JsonElement serialize(Tile tile, Type type,
			JsonSerializationContext context) {
		return context.serialize(tile, tile.getClass());
	}

	@Override
	public Tile deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		
		String tileType = jsonObject.getAsJsonPrimitive("type").getAsString();
		
		if (tileType.equals("text")) {
			String text = jsonObject.getAsJsonPrimitive("text").getAsString();
			return new TextTile(text);
		}
		
		throw new JsonParseException("Tile missing type");
	}
}
