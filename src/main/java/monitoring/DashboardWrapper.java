package monitoring;

import com.grafana.foundation.dashboard.Dashboard;

public record DashboardWrapper(String APIVersion, String kind, Metadata metadata, Dashboard spec) {}
