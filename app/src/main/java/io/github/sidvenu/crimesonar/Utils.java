package io.github.sidvenu.crimesonar;

import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;

public class Utils {
    public static Spanned getHtmlString(String text) {
        if (TextUtils.isEmpty(text)) {
            return new SpannedString("");
        }
        return Html.fromHtml(text);
    }
}
