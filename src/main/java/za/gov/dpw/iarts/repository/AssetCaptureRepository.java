package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.constants.StockStatuses;
import za.gov.dpw.iarts.entity.AssetCapture;

import java.util.Optional;

@Repository
public interface AssetCaptureRepository extends JpaRepository<AssetCapture, Long> {
    long countByStockStatus(String stockStatus);

    Optional<AssetCapture> findFirstByAssetTypeIgnoreCaseAndStorageRankIgnoreCaseAndStockStatusOrderByCreatedAtDesc(
            String assetType,
            String storageRank,
            String stockStatus
    );
}
