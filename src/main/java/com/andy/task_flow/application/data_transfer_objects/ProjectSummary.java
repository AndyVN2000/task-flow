package com.andy.task_flow.application.data_transfer_objects;

import java.time.Instant;
import java.util.UUID;

public record ProjectSummary(UUID id, String title, String description, Instant createdAt, boolean isArchived) {

}
