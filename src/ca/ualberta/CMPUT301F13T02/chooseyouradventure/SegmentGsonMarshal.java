package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SegmentGsonMarshal implements JsonSerializer<Segment>, JsonDeserializer<Segment>{

	@Override
	public JsonElement serialize(Segment segment, Type type,
			JsonSerializationContext context) {
		return context.serialize(segment, segment.getClass());
	}

	@Override
	public Segment deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		
		String segmentType = jsonObject.getAsJsonPrimitive("type").getAsString();
		
		if (segmentType.equals("text")) {
			String text = jsonObject.getAsJsonPrimitive("text").getAsString();
			return new TextSegment(text);
		}
		
		throw new JsonParseException("Segment missing type");
	}
}
