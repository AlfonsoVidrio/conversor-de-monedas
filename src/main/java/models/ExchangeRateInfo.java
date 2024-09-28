package models;

public class ExchangeRateInfo {
    private String amount;
    private String baseCode;
    private String targetCode;
    private String conversionRate;
    private String conversionResult;

    public ExchangeRateInfo(ExchangeRateInfoOmdb exchangeRateInfoOmdb) {
        this.amount = "0";
        this.baseCode = exchangeRateInfoOmdb.baseCode();
        this.targetCode = exchangeRateInfoOmdb.targetCode();
        this.conversionRate = exchangeRateInfoOmdb.conversionRate();
        this.conversionResult = exchangeRateInfoOmdb.conversionResult();
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public double getRate() {
        return Double.parseDouble(conversionRate);
    }

    @Override
    public String toString() {
        return amount + " " + baseCode + " = " + conversionResult + " " + targetCode;
    }



}
