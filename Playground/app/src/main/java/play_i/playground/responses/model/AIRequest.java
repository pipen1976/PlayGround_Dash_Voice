/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package play_i.playground.responses.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties
public class AIRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @SerializedName("timestamp")
    private String timestamp;
    
    @SerializedName("sessionId")
    private String sessionId;
    
    @SerializedName("result")
	private Result result;
	
	@SerializedName("query")
    private String[] query;

    @SerializedName("lang")
    private String lang;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("confidence")
    private float[] confidence;

    @SerializedName("contexts")
    private List<AIContext> contexts;

    @SerializedName("resetContexts")
    private Boolean resetContexts;

    @SerializedName("event")
    private AIEvent event;

    @SerializedName("lang")
    private String language;

    @SerializedName("entities")
    private List<Entity> entities;

    @SerializedName("location")
    private Location location;

    @SerializedName("originalRequest")
    private AIOriginalRequest originalRequest;

    public AIRequest() {
    }

    public AIRequest(final String query) {
        setQuery(query);
    }

    public void setQuery(final String query) {
        this.query = query.isEmpty() ? null : new String[]{query};
        confidence = null;
    }

    public void setQuery(final String[] query, final float[] confidence) {
        if (query == null) {
            throw new IllegalStateException("Query array must not be null");
        }

        if (confidence == null && query.length > 1) {
            throw new IllegalStateException("Then confidences array is null, query must be one or zero item length");
        }

        if (confidence != null && query.length != confidence.length) {
            throw new IllegalStateException("Query and confidence arrays must be equals size");
        }

        this.query = query;
        this.confidence = confidence;
    }

    public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
    public float[] getConfidence() {
        return confidence;
    }

    public void setConfidence(final float[] confidence) {
        this.confidence = confidence;
    }

    public void setResetContexts(final Boolean resetContexts) {
        this.resetContexts = resetContexts;
    }

    public Boolean getResetContexts() {
        return resetContexts;
    }

    public void setContexts(final List<AIContext> contexts) {
        this.contexts = contexts;
    }

    public void addContext(final AIContext aiContext) {
        if (contexts == null) {
            contexts = new ArrayList<>(1);
        }

        contexts.add(aiContext);
    }


    public void setEvent(AIEvent event) {
        this.event = event;
    }

    /**
     * Full request coming from the integrated platform (Facebook Messenger, Slack, etc.) 
     * @return <code>null</code> if request is not defined
     */
    public AIOriginalRequest getOriginalRequest() {
      return originalRequest;
    }

    /**
     * Set full request coming from the integrated platform (Facebook Messenger, Slack, etc.)
     * @param originalRequest <code>null</code> if request is not defined
     */
    public void setOriginalRequest(AIOriginalRequest originalRequest) {
      this.originalRequest = originalRequest;
    }

    public String[] getQuery() {
		return query;
	}

	public void setQuery(String[] query) {
		this.query = query;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public List<AIContext> getContexts() {
		return contexts;
	}

	public AIEvent getEvent() {
		return event;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	
	
}
