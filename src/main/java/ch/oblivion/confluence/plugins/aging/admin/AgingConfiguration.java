package ch.oblivion.confluence.plugins.aging.admin;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;

public class AgingConfiguration {

	protected static final int DEFAULT_PERIOD_AMOUNT = 1;
	protected static final int DEFAULT_PERIOD_TYPE = Calendar.MINUTE;
	protected static final String DEFAULT_IGNORE_LABEL = "no-aging";
	protected static final int DEFAULT_USE_MASTER = 1;

	private final Logger logger = LoggerFactory.getLogger(AgingConfiguration.class);

	private final PluginSettingsFactory pluginSettingsFactory;
	private final TransactionTemplate transactionTemplate;

	public AgingConfiguration(final PluginSettingsFactory pluginSettingsFactory, final TransactionTemplate transactionTemplate) {
		this.pluginSettingsFactory = pluginSettingsFactory;
		this.transactionTemplate = transactionTemplate;
		logger.debug("Setup aging configuration with {}, {}", pluginSettingsFactory, transactionTemplate);
	}

	public Config getConfig(final String spaceKey) {
		return transactionTemplate.execute(new TransactionCallback<Config>() {
			public Config doInTransaction() {
				if (!StringUtils.isBlank(spaceKey)) {
					return getSpaceConfig(spaceKey);
				} else {
					return getMasterConfig();
				}
			}
		});
	}

	private Config getMasterConfig() {
		PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
		Config config = new Config();
		setupStandard(settings, config);
		return config;
	}
	
	private Config getSpaceConfig(final String spaceKey) {
		PluginSettings settings = pluginSettingsFactory.createSettingsForKey(spaceKey);
		Config config = new Config();
		setupStandard(settings, config);
		String useMaster = (String) settings.get(Config.class.getName() + ".useMaster");
		if (useMaster != null) {
			config.setUseMaster(Integer.parseInt(useMaster));
			if (config.getUseMaster() == 1) {
				Config masterConfig = getMasterConfig();
				config.setPeriodAmount(masterConfig.getPeriodAmount());
				config.setPeriodType(masterConfig.getPeriodType());
				config.setIgnoreLabel(masterConfig.getIgnoreLabel());
			}
		} else {
			config.setUseMaster(DEFAULT_USE_MASTER);
		}
		return config;
	}

	private void setupStandard(PluginSettings settings, Config config) {
		String periodAmount = (String) settings.get(Config.class.getName() + ".periodAmount");
		if (periodAmount != null) {
			config.setPeriodAmount(Integer.parseInt(periodAmount));
		} else {
			config.setPeriodAmount(DEFAULT_PERIOD_AMOUNT);
		}

		String periodType = (String) settings.get(Config.class.getName() + ".periodType");
		if (periodType != null) {
			config.setPeriodType(Integer.parseInt(periodType));
		} else {
			config.setPeriodType(DEFAULT_PERIOD_TYPE);
		}

		String ignoreLabel = (String) settings.get(Config.class.getName() + ".periodType");
		if (ignoreLabel != null) {
			config.setIgnoreLabel((String) settings.get(Config.class.getName() + ".ignoreLabel"));
		} else {
			config.setIgnoreLabel(DEFAULT_IGNORE_LABEL);
		}		
	}

	public void updateConfig(final Config config) {
		if (StringUtils.isBlank(config.getSpaceKey())) {
			updateMasterConfig(config);
		} else {
			updateSpaceConfig(config);
		}
	}
	
	private void updateMasterConfig(final Config config) {
		transactionTemplate.execute(new TransactionCallback<Config>() {
			public Config doInTransaction() {
				PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
				updateSettings(pluginSettings, config);
				logger.debug("Update settings with {}", config);
				return null;
			}

		});
	}
	
	private void updateSpaceConfig(final Config config) {
		transactionTemplate.execute(new TransactionCallback<Config>() {
			public Config doInTransaction() {
				PluginSettings pluginSettings = pluginSettingsFactory.createSettingsForKey(config.getSpaceKey());
				updateSettings(pluginSettings, config);
				logger.debug("Update space settings for {} with {}", config.getSpaceKey(), config);
				return null;
			}

		});
	}
	
	private void updateSettings(PluginSettings pluginSettings, Config config) {
		pluginSettings.put(Config.class.getName() + ".periodType", Integer.toString(config.getPeriodType()));
		pluginSettings.put(Config.class.getName() + ".periodAmount", Integer.toString(config.getPeriodAmount()));
		pluginSettings.put(Config.class.getName() + ".ignoreLabel", config.getIgnoreLabel());
		pluginSettings.put(Config.class.getName() + ".useMaster", Integer.toString(config.getUseMaster()));
	}
}
