package ch.oblivion.confluence.plugins.aging;

import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.oblivion.confluence.plugins.aging.admin.AgingConfiguration;
import ch.oblivion.confluence.plugins.aging.admin.Config;
import ch.oblivion.confluence.plugins.aging.utilities.DateUtilities;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.labels.Label;
import com.atlassian.confluence.pages.AbstractPage;
import com.atlassian.confluence.plugin.descriptor.web.WebInterfaceContext;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.plugin.web.model.WebPanel;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;

public class AgingWebPanel implements WebPanel {

	// private final LabelManager labelManager;
	// private final PageManager pageManager;
	// private final SpaceManager spaceManager;
	private final AgingConfiguration agingConfiguration;

	private final Logger logger = LoggerFactory.getLogger(AgingWebPanel.class);

	public AgingWebPanel(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
		// this.labelManager = labelManager;
		// this.pageManager = pageManager;
		// this.spaceManager = spaceManager;
		agingConfiguration = new AgingConfiguration(pluginSettingsFactory, transactionTemplate);
	}

	// private String html =
	// "<div style='position:absolute; text-align: center;vertical-align: middle;"
	// +
	// "border-color: #000000;border-width: 1px;border-style: solid;margin-top: 25%;margin-bottom: 25%;'>"
	// + "<b>Hi There ... this is my html</b></div>";

	@Override
	public String getHtml(Map<String, Object> context) {
		String message = "";
		AbstractPage page = getPage(context);
		if (page != null) {
			Space space = page.getSpace();
			Config config = agingConfiguration.getConfig(space.getKey());

			List<Label> labels = page.getLabels();
			for (Label label : labels) {
				if (label.getDisplayTitle().equalsIgnoreCase(config.getIgnoreLabel())) {
					logger.debug("Page {} contains ignore label {}", page.getTitle(), label.getDisplayTitle());
					return message;
				}
			}

			Date lastModificationDate = page.getLastModificationDate();
			ConfluenceUser lastModifier = page.getLastModifier();

			Calendar modifyCal = Calendar.getInstance();
			modifyCal.setTime(lastModificationDate);
			int periodAmount = config.getPeriodAmount();
			int periodType = config.getPeriodType();
			modifyCal.add(periodType, periodAmount);

			String lastModificationPeriod = DateUtilities.getElapsedTime(lastModificationDate);

			if (modifyCal.before(Calendar.getInstance())) {
				message = MessageFormat.format("<div id='page-metadata-agingtext' class='agingplugin-text'>" 
						+ "<b>This page may contain outdated content.</b> It was last edited {0} ago by {1}" 
						+ "</div>"
						, lastModificationPeriod
						, lastModifier == null ? "Anonymous" : lastModifier.getFullName()
						, periodType
						, periodAmount);
				
				/**
				 * page-metadata-start
				 * $("#page-metadata-agingtext").after($('#page-metadata'));
				 */
			}
		}
		return message;
	}

	// private static String formatInterval(final long l)
	// {
	// final long days = TimeUnit.MILLISECONDS.toDays(l);
	// final long hr = TimeUnit.MILLISECONDS.toHours(l -
	// TimeUnit.DAYS.toMillis(days));
	// final long min = TimeUnit.MILLISECONDS.toMinutes(l -
	// TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hr));
	// final long sec = TimeUnit.MILLISECONDS.toSeconds(l -
	// TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hr) -
	// TimeUnit.MINUTES.toMillis(min));
	// final long ms = TimeUnit.MILLISECONDS.toMillis(l -
	// TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hr) -
	// TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
	// return String.format("%0d days, %02d hours %02d minutes", days, hr, min);
	// }

	private AbstractPage getPage(Map<String, Object> context) {
		try {
			ConfluenceActionSupport action = (ConfluenceActionSupport) context.get("action");
			WebInterfaceContext webInterfaceContext = action.getWebInterfaceContext();
			return webInterfaceContext.getPage();
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void writeHtml(Writer writer, Map<String, Object> context) throws IOException {
		// writer.write(html);
	}
}
