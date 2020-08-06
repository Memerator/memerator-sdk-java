package me.memerator.api.builder;

import me.memerator.api.entity.Age;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class MemeBuilder {
    private String base64data, caption = null;
    private Age age = Age.TEEN;
    private int rating = 0;
    private boolean completed = false;

    /**
     * Sets the image of the meme to be uploaded, must be a base64 string. This or setUrl() are required.
     * @param base64string the image in base64
     */
    public void setImage(@NotNull String base64string) {
        base64data = base64string;
        completed = true;
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
     * Sets the age of the meme. By default is TEEN.
     * @param newAge the Age to set.
     */
    public void setAge(@NotNull Age newAge) {
        age = newAge;
    }

    /**
     * Sets the caption of the meme. Optional!
     * @param newCaption the caption to set
     */
    public void setCaption(@NotNull String newCaption) {
        caption = newCaption;
    }

    /**
     * Build the JSON body. Used for the MemeratorAPI#submitMeme() function.
     * @return a Meme JSON object to be sent
     * @throws IllegalStateException if an Image has yet to be provided
     */
    public JSONObject build() throws IllegalStateException {
        if(!completed)
            throw new IllegalStateException("Please supply an image.");

        JSONObject newMeme = new JSONObject();
        if(caption != null)
            newMeme.put("caption", caption);
        if(rating > 0)
            newMeme.put("rating", rating);

        newMeme.put("age", age.getAgeInt());
        newMeme.put("image", base64data);

        return newMeme;
    }
}
