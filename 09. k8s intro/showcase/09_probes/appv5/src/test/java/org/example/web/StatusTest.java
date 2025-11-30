package org.example.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.SimpleStatusAggregator;
import org.springframework.boot.actuate.health.Status;

class StatusTest {
	final SimpleStatusAggregator aggregator = new SimpleStatusAggregator();

	@Test
	void allUp() {
		Status aggregateStatus = aggregator.getAggregateStatus(Status.UP, Status.UP, Status.UP, Status.UP);
		Assertions.assertEquals(Status.UP, aggregateStatus);
	}

	@Test
	void oneDown() {
		Status aggregateStatus = aggregator.getAggregateStatus(Status.UP, Status.DOWN, Status.UP, Status.UP);
		Assertions.assertEquals(Status.DOWN, aggregateStatus);
	}

	@Test
	void oneUnknown() {
		Status aggregateStatus = aggregator.getAggregateStatus(Status.UP, Status.UNKNOWN, Status.UP, Status.UP);
		Assertions.assertEquals(Status.UP, aggregateStatus);
	}

	@Test
	void allUnknown() {
		Status aggregateStatus = aggregator.getAggregateStatus(Status.UNKNOWN, Status.UNKNOWN, Status.UNKNOWN, Status.UNKNOWN);
		Assertions.assertEquals(Status.UNKNOWN, aggregateStatus);
	}

	@Test
	void oneOutOfService() {
		Status aggregateStatus = aggregator.getAggregateStatus(Status.UP, Status.OUT_OF_SERVICE, Status.UP, Status.UP);
		Assertions.assertEquals(Status.OUT_OF_SERVICE, aggregateStatus);
	}

	@Test
	void oneOutOfService_and_oneDown() {
		Status aggregateStatus = aggregator.getAggregateStatus(Status.UP, Status.OUT_OF_SERVICE, Status.DOWN, Status.UP);
		Assertions.assertEquals(Status.DOWN, aggregateStatus);
	}

}
