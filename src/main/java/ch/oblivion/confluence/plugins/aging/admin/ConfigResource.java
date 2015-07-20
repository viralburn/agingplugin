package ch.oblivion.confluence.plugins.aging.admin;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;

@Path("/")
//@Path("/")
public class ConfigResource {
	private final UserManager userManager;
	private final AgingConfiguration agingConfiguration;

	private final Logger logger = LoggerFactory.getLogger(ConfigResource.class);

	public ConfigResource(UserManager userManager, PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
		this.userManager = userManager;
		agingConfiguration = new AgingConfiguration(pluginSettingsFactory, transactionTemplate);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/{spaceKey : (/spaceKey)?}")
	public Response get(@Context HttpServletRequest request) {
		UserProfile remoteUser = userManager.getRemoteUser(request);
		if (remoteUser == null || !userManager.isSystemAdmin(remoteUser.getUserKey())) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		logger.debug("Received simple GET request.");
		return Response.ok(agingConfiguration.getConfig(null)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{spaceKey}")
	public Response get(@PathParam("spaceKey") String spaceKey, @Context HttpServletRequest request) {
		UserProfile remoteUser = userManager.getRemoteUser(request);
		if (remoteUser == null || !userManager.isSystemAdmin(remoteUser.getUserKey())) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		logger.debug("Received GET request for {}", spaceKey);
		return Response.ok(agingConfiguration.getConfig(spaceKey)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(final Config config, @Context HttpServletRequest request) {
		UserProfile remoteUser = userManager.getRemoteUser(request);
		if (remoteUser == null || !userManager.isSystemAdmin(remoteUser.getUserKey())) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		logger.debug("Received PUT request for {}", config);
		agingConfiguration.updateConfig(config);
		return Response.noContent().build();
	}

}