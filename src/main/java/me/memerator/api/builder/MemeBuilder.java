package me.memerator.api.builder;

import me.memerator.api.entity.MaxAge;
import org.json.JSONObject;

public class MemeBuilder {
    private String url, base64data, caption = null;
    private MaxAge age = MaxAge.TEEN;
    private int rating = 0;
    private boolean upload, completed;

    /**
     * Constructor
     */
    public MemeBuilder() {
        completed = false;
    }

    /**
     * Sets the URL for the meme if uploading via URL. This or setImage() are required.
     * @param newUrl the URL of the meme
     */
    public void setUrl(String newUrl) {
        completed = true;
        upload = false;
        url = newUrl;
    }

    /**
     * Sets the image of the meme to be uploaded, must be a base64 string. This or setUrl() are required.
     * @param base64string the image in base64
     */
    public void setImage(String base64string) {
        base64data = base64string;
        completed = true;
        upload = true;
    }

    /**
     * Set the rating of the meme, when you upload it your rating will be automatically applied. Optional!
     * @param newRating the rating to set
     * @throws IllegalArgumentException if the rating is not valid (between 1 and 5)
     */
    public void setRating(int newRating) throws IllegalArgumentException {
        if(newRating < 1 || newRating > 5)
            throw new IllegalArgumentException("Please supply a rating between 1 and 5");
        rating = newRating;
    }

    /**
     * Sets the max age of the meme. By default is TEEN.
     * @param newAge the MaxAge to set.
     */
    public void setAge(MaxAge newAge) {
        age = newAge;
    }

    /**
     * Sets the caption of the meme. Optional!
     * @param newCaption the caption to set
     */
    public void setCaption(String newCaption) {
        caption = newCaption;
    }

    /**
     * Build the JSON body. Used for the submitMeme() function.
     * @return a Meme JSON object to be sent
     * @throws IllegalStateException if a URL or Image has yet to be provided
     */
    public JSONObject build() throws IllegalStateException {
        if(!completed)
            throw new IllegalStateException("Please supply a URL or Image");
        JSONObject newMeme = new JSONObject();
        if(caption != null)
            newMeme.put("caption", caption);
        if(rating > 0)
            newMeme.put("rating", rating);
        if(age != null)
            newMeme.put("age", age.getAgeInt());
        else
            newMeme.put("age", MaxAge.TEEN.getAgeInt());
        if(upload)
            newMeme.put("image", base64data);
        else
            newMeme.put("url", url);

        return newMeme;
    }
}
