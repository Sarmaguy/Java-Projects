package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private String language;
	private ResourceBundle bundle;
	private static final LocalizationProvider provider = new LocalizationProvider();
	
	private LocalizationProvider() {
		language = "en";
		setLanguage(language);
	}
	
	public static LocalizationProvider getInstance() {
		return provider;
	}
	
	public void setLanguage(String language) {
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi", Locale.forLanguageTag(language));
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
