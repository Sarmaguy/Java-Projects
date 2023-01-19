package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	private boolean connected;
	private ILocalizationListener listener;
	private ILocalizationProvider provider;

	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		this.connected = false;
		this.listener = () -> fire();

	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	public void connect() {
		if (!connected) {
			connected = true;
			provider.addLocalizationListener(listener);
		}
	}

	public void disconnect() {
		if (connected) {
			connected = false;
			provider.removeLocalizationListener(listener);
		}
	}

	public void setListener(ILocalizationListener listener) {
		this.listener = listener;
	}



}
