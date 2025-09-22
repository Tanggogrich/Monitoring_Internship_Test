package monitoring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grafana.foundation.dashboard.*;
import static monitoring.Common.datasourceRef;
import static com.grafana.foundation.common.Constants.TimeZoneBrowser;
import static monitoring.Table.avgOverTimeCPUUsageAsTable;
import static monitoring.TimeSeries.avgOverTimeCPUUsageAsTimeSeries;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Dashboard dashboard = new DashboardBuilder("Grafana monitoring dashboard")
                .uid("monitoring-agent")
                .tags(List.of("generated", "grafana-foundation-sdk", "monitoring-agent"))
                .editable()
                .tooltip(DashboardCursorSync.OFF)
                .refresh("5s")
                .time(new DashboardDashboardTimeBuilder()
                        .from("now-10m")
                        .to("now"))
                .timezone(TimeZoneBrowser)
                .timepicker(new TimePickerBuilder()
                        .refreshIntervals(List.of("1s", "2s", "5s", "10s", "30s", "1m", "5m", "15m", "30m", "1h", "2h", "1d")))
                .withVariable(new DatasourceVariableBuilder("prometheus_datasource")
                        .label("Data source")
                        .type("prometheus")
                        .regex("(?!grafanacloud-usage|grafanacloud-ml-metrics).+")
                        .multi(false))
                .withVariable(jobQueryBuilder())
                .withVariable(instanceQueryBuilder())
                .withRow(new RowBuilder("CPU Usage as TimeSeries"))
                .withPanel(avgOverTimeCPUUsageAsTimeSeries())
                .withRow(new  RowBuilder("CPU Usage as Table"))
                .withPanel(avgOverTimeCPUUsageAsTable())
                .build();


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dashboard);
            try (FileWriter file = new FileWriter("src/main/resources/dashboard.json")) {
                file.write(prettyJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Formatted JSON successfully written to dashboard.json");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static QueryVariableBuilder jobQueryBuilder() {
        VariableOption option = new VariableOption();
        option.selected = true;
        option.text = StringOrArrayOfString.createArrayOfString(List.of("All"));
        option.value = StringOrArrayOfString.createArrayOfString(List.of("$__all"));

        return new QueryVariableBuilder("job")
                .label("job")
                .query(StringOrMap.createString("label_values(cpu_usage, job)"))
                .datasource(datasourceRef())
                .current(option)
                .refresh(VariableRefresh.ON_TIME_RANGE_CHANGED)
                .sort(VariableSort.ALPHABETICAL_ASC)
                .multi(true)
                .includeAll(true);
    }

    private static QueryVariableBuilder instanceQueryBuilder() {
        VariableOption option = new VariableOption();
        option.selected = true;
        option.text = StringOrArrayOfString.createArrayOfString(List.of("All"));
        option.value = StringOrArrayOfString.createArrayOfString(List.of("$__all"));

        return new QueryVariableBuilder("instance")
                .label("instance")
                .query(StringOrMap.createString("label_values(cpu_usage{job=~\"$job\"}, instance)"))
                .datasource(datasourceRef())
                .current(option)
                .refresh(VariableRefresh.ON_TIME_RANGE_CHANGED)
                .sort(VariableSort.ALPHABETICAL_ASC)
                .multi(true)
                .includeAll(true);
    }
}
