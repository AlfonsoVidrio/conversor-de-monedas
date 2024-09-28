package models;

import com.google.gson.annotations.SerializedName;

public record CodesCurrenciesOmdb(
        @SerializedName("supported_codes") String[][] supportedCodes
) {
}