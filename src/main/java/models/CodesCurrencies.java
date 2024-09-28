package models;

public class CodesCurrencies {
    private String[][] supportedCodes;
    private String[] codes;

    public CodesCurrencies(CodesCurrenciesOmdb codesCurrenciesOmdb) {
        this.supportedCodes = codesCurrenciesOmdb.supportedCodes();
        this.codes = new String[supportedCodes.length];
    }

    public String[] getSupportedCode() {
        for (int i = 0; i < supportedCodes.length; i++) {
            codes[i] = supportedCodes[i][0] + "  " + supportedCodes[i][1];
            //codes[i] = supportedCodes[i][0];
            if (codes[i].length() > 25) {
                codes[i] = codes[i].substring(0, 26) + "...";
            }
        }
        return codes;
    }

    // toString method
    @Override
    public String toString() {
        for (String[] supportedCode : supportedCodes) {
            System.out.println(supportedCode[0] + " - " + supportedCode[1]);
        }
        return "";
    }
}