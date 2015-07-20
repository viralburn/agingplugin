package ch.oblivion.confluence.plugins.aging.admin.space;

import com.atlassian.confluence.spaces.actions.SpaceAdminAction;

public class AgingSpaceAdminAction extends SpaceAdminAction {

	@Override
	public String doDefault() throws Exception {
		return INPUT;
	}
	
}
