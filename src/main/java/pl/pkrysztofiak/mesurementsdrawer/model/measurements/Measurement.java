package pl.pkrysztofiak.mesurementsdrawer.model.measurements;

import java.util.UUID;

public abstract class Measurement {

	protected final String id = UUID.randomUUID().toString();

	public String getId() {
		return id;
	}

	public abstract MeasurementType getType();
}
