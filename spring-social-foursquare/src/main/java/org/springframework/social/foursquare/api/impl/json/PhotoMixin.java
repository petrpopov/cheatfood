package org.springframework.social.foursquare.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.foursquare.api.*;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
abstract class PhotoMixin {
	@JsonCreator
	PhotoMixin(
			@JsonProperty("id") String id,
			@JsonProperty("createdAt") @JsonDeserialize(using=FoursquareDateDeserializer.class) Date createdAt,
			@JsonProperty("url") String url,
			@JsonProperty("sizes") PhotoSizes sizes,
			@JsonProperty("user") FoursquareUser user){}
	

	@JsonProperty("source") 
	PhotoSource source;
	
	@JsonProperty("venue") 
	Venue venue;
	
	@JsonProperty("tip") 
	Tip tip;
}
