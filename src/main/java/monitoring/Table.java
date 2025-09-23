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
                                "avg_over_time(cpu_usage{job=~\"$job\"}[$__interval])",
                                "{{job}} configured"
                        )
                                .intervalFactor(2.0)
                                .legendFormat("{{instance}} {{job}}")
                );
    }
}
