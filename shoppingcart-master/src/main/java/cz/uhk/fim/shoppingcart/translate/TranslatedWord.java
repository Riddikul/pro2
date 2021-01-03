package cz.uhk.fim.shoppingcart.translate;

import com.google.gson.annotations.SerializedName;

public class TranslatedWord {
    @SerializedName("source")
    public Source source;
    @SerializedName("target")
    public Target target;
}
