package org.springframework.social.foursquare.api.impl.json;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.springframework.social.foursquare.api.CheckinInfo;

import java.io.IOException;

public class HereNowContainerDeserializer extends AbstractFoursquareDeserializer<HereNowContainer> {
	@Override
	public HereNowContainer deserialize(JsonParser jp, DeserializationContext ctxt) 
			throws IOException, JsonProcessingException {
		return new HereNowContainer(deserializeNestedResponseObject(jp, "hereNow", CheckinInfo.class));
	}
}
