package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

public enum ToolType {

	POLYGON(new PolygonTool()), LINE(new LineTool());

	private final Tool tool;

	private ToolType(Tool tool) {
		this.tool = tool;
	}

	public Tool getTool() {
		return tool;
	}
}