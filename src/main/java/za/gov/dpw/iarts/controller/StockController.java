package za.gov.dpw.iarts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.StockSummaryDto;
import za.gov.dpw.iarts.service.AssetService;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
    private final AssetService assetService;

    @GetMapping("/summary")
    public List<StockSummaryDto> summary() {
        return assetService.stockSummary();
    }
}
