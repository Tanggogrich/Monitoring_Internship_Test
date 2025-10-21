package monitoring;

import com.grafana.foundation.timeseries.PanelBuilder;

import static monitoring.Common.*;

public class TimeSeries {
    static PanelBuilder avgOverTimeCPUUsageAsTimeSeries() {
        return defaultTimeSeries()
                .title("Average Over Time CPU Usage")
                .description("Average Over Time CPU Usage")
                .datasource(datasourceRef())
                .unit("short")
                .withTarget(
                        prometheusQuery(
                                "avg_over_time(cpu_usage{job=~\"$job\"}[5m])",
                                "{{job}} configured"
                        )
                                .legendFormat("{{instance}} {{job}}")
                );
    }

    static PanelBuilder avgOverTimeCPUUsageAsModifiedTimeSeries() {
        return timeSeriesWithTable()
                .title("Average Over Time CPU Usage")
                .description("Average Over Time CPU Usage")
                .datasource(datasourceRef())
                .unit("short")
                .withTarget(
                        prometheusQuery(
                                "avg_over_time(cpu_usage{job=~\"$job\"}[$__interval])",
                                "{{job}} configured"
                        )
                                .intervalFactor(2.0)
                                .legendFormat("{{instance}} {{job}}")
                );
    }
}
