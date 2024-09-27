package models;

public class ExchangeRateInfo {
    private String result;
    private String baseCode;
    private String targetCode;
    private String conversionRate;
    private String conversionResult;

    public ExchangeRateInfo(ExchangeRateInfoOmdb exchangeRateInfoOmdb) {
        this.result = exchangeRateInfoOmdb.result();
        this.baseCode = exchangeRateInfoOmdb.baseCode();
        this.targetCode = exchangeRateInfoOmdb.targetCode();
        this.conversionRate = exchangeRateInfoOmdb.conversionRate();
        this.conversionResult = exchangeRateInfoOmdb.conversionResult();
    }

    @Override
    public String toString() {
        return "( result='" + result +
                ", baseCode='" + baseCode +
                ", targetCode='" + targetCode +
                ", conversionRate='" + conversionRate +
                ", conversionResult='" + conversionResult + " )";
    }
}
