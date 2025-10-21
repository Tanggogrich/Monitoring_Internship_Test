package monitoring;

import com.grafana.foundation.common.*;
import com.grafana.foundation.dashboard.DataSourceRef;
import com.grafana.foundation.prometheus.DataqueryBuilder;
import com.grafana.foundation.prometheus.PromQueryFormat;
import com.grafana.foundation.timeseries.PanelBuilder;

import java.util.List;

public class Common {
    static DataSourceRef datasourceRef() {
        return new DataSourceRef("prometheus", "$prometheus_datasource");
    }

    static DataqueryBuilder prometheusQuery(String query, String refId) {
        return new DataqueryBuilder().expr(query).refId(refId);
    }

    static PanelBuilder defaultTimeSeries() {
        return new PanelBuilder()
                .height(7)
                .span(12)
                .lineWidth(1.0)
                .fillOpacity(0.0)
                .pointSize(5.0)
                .showPoints(VisibilityMode.AUTO)
                .drawStyle(GraphDrawStyle.LINE)
                .gradientMode(GraphGradientMode.NONE)
                .legend(new VizLegendOptionsBuilder()
                        .displayMode(LegendDisplayMode.LIST)
                        .placement(LegendPlacement.BOTTOM)
                        .showLegend(true)
                )
                .fillOpacity(15.0)
                .tooltip(new VizTooltipOptionsBuilder()
                        .mode(TooltipDisplayMode.SINGLE)
                        .sort(SortOrder.NONE)
                )
                .thresholdsStyle(new GraphThresholdsStyleConfigBuilder()
                        .mode(GraphThresholdsStyleMode.OFF)
                )
                .spanNulls(BoolOrFloat64.createBool(true))
                .axisBorderShow(false);
    }

    static PanelBuilder timeSeriesWithTable() {
        return new PanelBuilder()
                .height(7)
                .span(12)
                .lineWidth(1.0)
                .fillOpacity(0.0)
                .pointSize(5.0)
                .showPoints(VisibilityMode.NEVER)
                .drawStyle(GraphDrawStyle.LINE)
                .gradientMode(GraphGradientMode.NONE)
                .legend(new VizLegendOptionsBuilder()
                        .displayMode(LegendDisplayMode.TABLE)
                        .placement(LegendPlacement.BOTTOM)
                        .showLegend(true)
                        .calcs(List.of("min", "max", "mean", "last"))
                )
                .tooltip(new VizTooltipOptionsBuilder()
                        .mode(TooltipDisplayMode.SINGLE)
                        .sort(SortOrder.NONE)
                )
                .fillOpacity(15.0)
                .thresholdsStyle(new GraphThresholdsStyleConfigBuilder()
                        .mode(GraphThresholdsStyleMode.OFF)
                )
                .spanNulls(BoolOrFloat64.createBool(true))
                .axisBorderShow(false)
                .axisGridShow(true);
    }

    static DataqueryBuilder tablePrometheusQuery(String query, String refId) {
        return new DataqueryBuilder()
                .expr(query)
                .format(PromQueryFormat.TABLE)
                .range()
                .intervalFactor(2.0)
                .refId(refId);
    }
}
