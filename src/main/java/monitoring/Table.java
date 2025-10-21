package monitoring;

import com.grafana.foundation.table.PanelBuilder;

import static monitoring.Common.*;

public class Table {
    static PanelBuilder avgOverTimeCPUUsageAsTable() {
        return new PanelBuilder()
                .title("Average Over Time CPU Usage")
                .description("Average Over Time CPU Usage")
                .datasource(datasourceRef())
                .unit("short")
                .withTarget(
                        tablePrometheusQuery(
                                "rate(remote_write_status_total{job=~\"$job\"}[5m])",
                                "{{job}} configured"
                        )
                                .legendFormat("{{instance}} {{status}}")
                );
    }
}
