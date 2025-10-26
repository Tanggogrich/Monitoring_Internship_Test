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
            "(sum(rate(http_requests_total{job=~\"$job\", status=\"200\"}[1m]))/"+
                    "sum(rate(http_requests_total{job=~\"$job\"}[1m])) * 100";

    static PanelBuilder gauge() {
        return new PanelBuilder()
                .height(7)
                .span(12)
                .orientation(VizOrientation.AUTO)
                .showThresholdMarkers(true)
                .unit("percent") // Use standard percentage unit
                .thresholds(new ThresholdsConfigBuilder()
                        .mode(ThresholdsMode.ABSOLUTE)
                        .steps(List.of(
                                // 0% - 99.0% is RED (Bad)
                                new Threshold(99.0, "red"),
                                // 99.0% - 99.9% is YELLOW (Warning)
                                new Threshold(99.9, "yellow"),
                                // 99.9% - 100% is GREEN (Good)
                                new Threshold(100.0, "green")
                        )))
                .title("Remote Write API Success Rate (%)")
                .description("Average Over Time CPU Usage")
                .datasource(datasourceRef())
                .withTarget(
                        prometheusQuery(
                                apiSuccessRateQuery,
                                "{{job}} configured"
                        )
                                .range()
                );
    }

}
