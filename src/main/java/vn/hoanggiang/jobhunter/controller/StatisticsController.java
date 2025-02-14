package vn.hoanggiang.jobhunter.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.hoanggiang.jobhunter.service.StatisticsService;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @GetMapping("/daily")
    @ApiMessage("statistics by day")
    //Convert date value from string (String) in request to LocalDate
    public ResponseEntity<Map<String, Long>> getDailyStats(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(statisticsService.getDailyStats(date));
    }

    @GetMapping("/monthly")
    @ApiMessage("statistics by month")
    public ResponseEntity<Map<String, Long>> getMonthlyStats(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(statisticsService.getMonthlyStats(year, month));
    }

    @GetMapping("/yearly")
    @ApiMessage("statistics by year")
    public ResponseEntity<Map<String, Long>> getYearlyStats(@RequestParam int year) {
        return ResponseEntity.ok(statisticsService.getYearlyStats(year));
    }

    @GetMapping("/subscribers-skills")
    @ApiMessage("subscriber statistics by skill")
    public ResponseEntity<Map<String, Long>> getSubscribersBySkill() {
        return ResponseEntity.ok(statisticsService.getSubscribersBySkill());
    }

    @GetMapping("/overall")
    @ApiMessage("general statistics")
    public ResponseEntity<Map<String, Long>> getOverview() {
        return ResponseEntity.ok(statisticsService.getOverallStatistics());
    }
}
