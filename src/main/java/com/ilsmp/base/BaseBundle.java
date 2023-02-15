package com.ilsmp.base;

import java.util.ResourceBundle;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

/**
 * {@link ResourceBundle}/localization utils for the Base plugin.
 */
public class BaseBundle {
	/**
	 * The {@link ResourceBundle} path.
	 */
	@NonNls
	private static final String BUNDLE_NAME = "messages.BaseBundle";
	/**
	 * The {@link ResourceBundle} instance.
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private BaseBundle() {
	}

	public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
		return AbstractBundle.message(BUNDLE, key, params);
/*        if (!StringUtil.isEmptyOrSpaces(message)) {
            try {
                message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        return message;*/
	}

}
