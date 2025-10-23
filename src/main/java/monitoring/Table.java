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
                                "sum by (status)(rate(remote_write_status_total{job=~\"$job\"}[1m]))",
                                "{{job}} configured"
                        )
                                .legendFormat("{{instance}} {{status}}")
                );
    }
}
