package org.springframework.social.foursquare.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.foursquare.api.PhotoGroup;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
abstract class PhotosMixin {
	@JsonCreator
	PhotosMixin(
			@JsonProperty("count") int count, 
			@JsonProperty("groups") List<PhotoGroup> groups) {}
}
