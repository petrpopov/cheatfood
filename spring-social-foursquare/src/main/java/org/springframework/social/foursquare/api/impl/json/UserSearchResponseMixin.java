package org.springframework.social.foursquare.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.foursquare.api.FoursquareUser;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
abstract class UserSearchResponseMixin {
    @JsonCreator
    UserSearchResponseMixin(
            @JsonProperty("results") List<FoursquareUser> results,
            @JsonProperty("unmatched") Map<String, Object> unmatched){}
    
}
