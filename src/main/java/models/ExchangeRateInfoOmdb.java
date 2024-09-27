package models;

import com.google.gson.annotations.SerializedName;

public record ExchangeRateInfo(
        @SerializedName("result") String result,
        @SerializedName("base_code") String baseCode,
        @SerializedName("target_code") String targetCode,
        @SerializedName("conversion_rate") String conversionRate,
        @SerializedName("conversion_result") String conversionResult
) {
}
