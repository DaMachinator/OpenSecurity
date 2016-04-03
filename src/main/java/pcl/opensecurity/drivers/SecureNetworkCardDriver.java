package pcl.opensecurity.drivers;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.EnvironmentHost;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Component;
import li.cil.oc.api.network.ComponentConnector;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.server.component.NetworkCard;

public class SecureNetworkCardDriver extends NetworkCard {

	public static EnvironmentHost container;
	private ComponentConnector node;

	public SecureNetworkCardDriver(EnvironmentHost container) {
		super(container);
		this.container = container;
		this.setNode(Network.newNode(this, Visibility.Network)
				.withComponent("modem", Visibility.Neighbors)
				.withConnector(1)
				.create());
	}

	@Callback(doc = "generateUUID() -- Randomises the UUID")
	public Object[] generateUUID(Context context, Arguments args) {
		if(node.tryChangeBuffer(1)) {
			this.node = null;
			this.setNode(Network.newNode(this, Visibility.Network)
					.withComponent("modem", Visibility.Neighbors)
					.withConnector(1)
					.create());
			return new Object[] { true };
		} else {
			return new Object[] { false };
		}
	}

	@Override
	public EnvironmentHost host() {
		return this.container;
	}

	@Override
	public Component node() {
		return this.node != null ? this.node : super.node();
	}

	@Override
	protected void setNode(Node value) {
		if(value instanceof ComponentConnector) {
			this.node = (ComponentConnector) value;
		}
		super.setNode(value);
	}
}
