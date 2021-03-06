package org.springframework.social.foursquare.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.foursquare.api.Badge;
import org.springframework.social.foursquare.api.BadgeSets;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
abstract class BadgesResponseMixin {
    @JsonCreator
    BadgesResponseMixin(
            @JsonProperty("sets") BadgeSets sets,
            @JsonProperty("badges") Map<String,Badge> badges){}
}
