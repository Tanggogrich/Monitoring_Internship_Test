package monitoring;

import com.grafana.foundation.timeseries.PanelBuilder;

import static monitoring.Common.datasourceRef;
import static monitoring.Common.prometheusQuery;
import static monitoring.Common.defaultTimeSeries;

public class TimeSeries {
    static PanelBuilder avgOverTimeCPUUsageAsTimeSeries() {
        return defaultTimeSeries()
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
