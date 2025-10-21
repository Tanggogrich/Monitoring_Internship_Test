package monitoring;

import com.grafana.foundation.common.VizOrientation;
import com.grafana.foundation.dashboard.Threshold;
import com.grafana.foundation.dashboard.ThresholdsConfigBuilder;
import com.grafana.foundation.dashboard.ThresholdsMode;
import com.grafana.foundation.gauge.PanelBuilder;

import java.util.List;

import static monitoring.Common.datasourceRef;
import static monitoring.Common.prometheusQuery;

public class Gauge {
    static String apiSuccessRateQuery =
            "(sum(rate(remote_write_status_total{job=~\"$job\", status=\"200\"}[5m])) / " +
                    "sum(rate(remote_write_status_total{job=~\"$job\"}[5m]))) * 100";

    static PanelBuilder gauge() {
        return new PanelBuilder()
                .height(7)
                .span(12)
                .orientation(VizOrientation.AUTO)
                .showThresholdMarkers(true)
                .thresholds(new ThresholdsConfigBuilder()
                        .mode(ThresholdsMode.ABSOLUTE)
                        .steps(List.of(
                                new Threshold(70.0, "red"),
                                new Threshold(80.0, "yellow"),
                                new Threshold(90.0, "green"),
                                new Threshold(100.0, "yellow")
                        )))
                .title("Gauge average cpu usage")
                .description("Average Over Time CPU Usage")
                .datasource(datasourceRef())
                .unit("short")
                .withTarget(
                        prometheusQuery(
                                apiSuccessRateQuery,
                                "{{job}} configured"
                        )
                                .instant()
                                .legendFormat("{{instance}} {{job}}")
                );
    }

}
