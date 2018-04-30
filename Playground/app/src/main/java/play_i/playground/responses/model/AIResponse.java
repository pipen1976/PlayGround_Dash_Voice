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

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * {
	"id": "1d98c587-9310-477e-b2d0-9f90bb5c4e8b",
	"timestamp": "2018-04-01T12:13:51.108Z",
	"lang": "es",
	"result": {
		"source": "agent",
		"resolvedQuery": "I need apples",
		"contexts": [{
			"name": "shop",
			"lifespan": 4
		}],
		"metadata": {},
		"fulfillment": {
			"speech": ""
		},
		"score": 0.0
	},
	"status": {
		"code": 200,
		"errorType": "success",
		"webhookTimedOut": false
	},
	"sessionId": "12345"
}
 * @author admin
 *
 */
public class AIResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * Unique identifier of the result.
     */
	 @SerializedName("speech")
	private String speech;
	 @SerializedName("displayText")
	private String displayText;
	
    @SerializedName("id")
    private String id;

    @SerializedName("timestamp")
    private Date timestamp;
    
    @SerializedName("lang")
    private String lang;

    /**
     * Result object
     */
    @SerializedName("result")
    private Result result;

    @SerializedName("status")
    private Status status;
    
    @SerializedName("sessionId")
    private String sessionId;

    /**
     * Unique identifier of the result.
     */
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	/**
     * Date and time of the request in UTC timezone using ISO-8601 format.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Agent's language.
     * @return Language tag. See <a href="https://docs.api.ai/docs/languages">languages</a> 
     * for details
     */
    public String getLang() {
      return lang;
    }
    
    public void setLang(String lang) {
      this.lang = lang;
    }

    /**
     * Contains the results of the natual language processing.
     */
    public Result getResult() {
        return result;
    }

    public void setResult(final Result result) {
        this.result = result;
    }

    /**
     * Contains data on how the request succeeded or failed.
     */
    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }
    
    /**
     * Session ID
     */
    public String getSessionId() {
    	return sessionId;
    }
    
    public void setSessionId(String sessionId) {
    	this.sessionId = sessionId;
    }

    public boolean isError() {
        if (status != null && status.getCode() != null && status.getCode() >= 400) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("AIResponse{id='%s', timestamp=%s, result=%s, status=%s, sessionId=%s}",
                id,
                timestamp,
                result,
                status,
        		sessionId);
    }

   
}
