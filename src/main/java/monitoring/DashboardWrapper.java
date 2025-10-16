package monitoring;

import com.grafana.foundation.dashboard.Dashboard;

public record DashboardWrapper(String apiVersion, String kind, Metadata metadata, Dashboard spec) {}
