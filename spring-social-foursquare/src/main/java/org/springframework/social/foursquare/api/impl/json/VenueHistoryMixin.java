package org.springframework.social.foursquare.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.foursquare.api.VenueHistoryItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
abstract class VenueHistoryMixin {
	@JsonCreator
	VenueHistoryMixin(
			@JsonProperty("count") int count, 
			@JsonProperty("items") List<VenueHistoryItem> items){}
}
