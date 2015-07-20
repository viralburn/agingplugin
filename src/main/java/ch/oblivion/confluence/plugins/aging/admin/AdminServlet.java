package ch.oblivion.confluence.plugins.aging.admin;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;
import com.atlassian.templaterenderer.TemplateRenderer;

public class AdminServlet extends HttpServlet {
	private final UserManager userManager;
	private final LoginUriProvider loginUriProvider;
	private final TemplateRenderer renderer;

	public AdminServlet(UserManager userManager,
			LoginUriProvider loginUriProvider, TemplateRenderer renderer) {
		this.userManager = userManager;
		this.loginUriProvider = loginUriProvider;
		this.renderer = renderer;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserProfile remoteUser = userManager.getRemoteUser(request);
		if (remoteUser == null || !userManager.isSystemAdmin(remoteUser.getUserKey())) {
			redirectToLogin(request, response);
			return;
		}

		response.setContentType("text/html;charset=utf-8");
		renderer.render("templates/aging-admin.vm", response.getWriter());
	}

	private void redirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(loginUriProvider.getLoginUri(getUri(request))
				.toASCIIString());
	}

	private URI getUri(HttpServletRequest request) {
		StringBuffer builder = request.getRequestURL();
		if (request.getQueryString() != null) {
			builder.append("?");
			builder.append(request.getQueryString());
		}
		return URI.create(builder.toString());
	}
}
