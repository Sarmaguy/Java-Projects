package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public interface ILocalizationProvider {
	
	public void addLocalizationListener(ILocalizationListener listener);
	
	public void removeLocalizationListener(ILocalizationListener listener);
	
	public String getString(String key);
}
