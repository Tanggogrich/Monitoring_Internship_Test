package monitoring;

import com.grafana.foundation.common.VizOrientation;
import com.grafana.foundation.dashboard.ThresholdsConfigBuilder;
import com.grafana.foundation.dashboard.ThresholdsMode;
import com.grafana.foundation.gauge.PanelBuilder;

import static monitoring.Common.datasourceRef;
import static monitoring.Common.prometheusQuery;

public class Gauge {
    static PanelBuilder gauge() {
        return new PanelBuilder()
                .height(7)
                .span(12)
                .orientation(VizOrientation.AUTO)
                .showThresholdMarkers(true)
                .thresholds(new ThresholdsConfigBuilder()
                        .mode(ThresholdsMode.PERCENTAGE))
                .title("Gauge average cpu usage")
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
