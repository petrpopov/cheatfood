package org.springframework.social.foursquare.api.impl.json;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.springframework.social.foursquare.api.Photos;

import java.io.IOException;

public class VenuePhotosContainerDeserializer extends AbstractFoursquareDeserializer<VenuePhotosContainer> {
	@Override
	public VenuePhotosContainer deserialize(JsonParser jp, DeserializationContext ctxt) 
			throws IOException, JsonProcessingException {
		return new VenuePhotosContainer(deserializeNestedResponseObject(jp, "photos", Photos.class));
	}
}
